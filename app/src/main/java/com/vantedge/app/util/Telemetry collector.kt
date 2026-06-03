package com.vantedge.app.util

import android.content.Context
import android.util.Log
import com.vantedge.app.data.model.TelemetryRecord
import com.vantedge.app.data.model.UserDecisionEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Async-safe telemetry collector for Gate 0 calibration data.
 *
 * Architectural contracts:
 * - [record] and [recordDecision] are synchronous entry points (non-suspend, return Unit).
 *   Async dispatch is handled internally via [scope].
 * - Internal scope: SupervisorJob + [dispatcher]. Failures in one coroutine do NOT
 *   cancel others.
 * - [ReentrantLock] guards in-memory queue mutations AND triggers file append atomically.
 * - File persistence: records appended to [cacheDir/telemetry.log] in tab-separated format.
 *   Survives process death. Append-only; never truncated by this class.
 * - Bounded LRU queue: max [MAX_QUEUE_SIZE] TelemetryRecords in memory. When full, the
 *   oldest record is evicted (and its hash removed). Warning logged on eviction.
 *   File log is NOT pruned — it is the durable source of truth.
 * - Idempotent emission: duplicate [documentHash] calls are defensive no-ops for both
 *   the in-memory queue AND the file (no duplicate lines written).
 * - No reference to ViewModel, viewModelScope, or any UI lifecycle component.
 *
 * Log file format (tab-separated, one record per line):
 *   GATE0  {hash}  {sessionId}  {timestampMs}  {score}  {threshold}  {accepted}  {reason}  {mode}
 *   DECISION  {hash}  {sessionId}  {timestampMs}  {decisionType}
 *
 * Constructor parameter [dispatcher] defaults to [Dispatchers.IO]. Override in tests
 * (e.g. UnconfinedTestDispatcher) for deterministic execution.
 */
class TelemetryCollector(
    context: Context,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    companion object {
        private const val TAG = "TelemetryCollector"
        private const val MAX_QUEUE_SIZE = 100
        private const val LOG_FILE_NAME = "telemetry.log"
    }

    private val telemetryFile = File(context.cacheDir, LOG_FILE_NAME)
    private val lock = ReentrantLock()
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    // In-memory LRU structures — guarded by [lock].
    // LinkedHashSet gives O(1) contains() + remove() in insertion order.
    private val seenHashes = LinkedHashSet<String>(MAX_QUEUE_SIZE)
    private val queue = ArrayDeque<TelemetryRecord>(MAX_QUEUE_SIZE)
    private val decisionLog = ArrayDeque<UserDecisionEvent>()

    // ------------------------------------------
    // RECORD: Gate 0 telemetry
    // Synchronous entry point → async dispatch
    // ------------------------------------------

    /**
     * Records a [TelemetryRecord] emitted at Gate 0 completion.
     *
     * Returns immediately (Unit). Async work runs on [scope]:
     *   1. Idempotency check (by documentHash)
     *   2. LRU eviction if queue is full
     *   3. In-memory queue update
     *   4. File append to [telemetry.log]
     */
    fun record(telemetry: TelemetryRecord) {
        scope.launch {
            lock.withLock {
                if (seenHashes.contains(telemetry.documentHash)) {
                    Log.d(
                        TAG,
                        "[TelemetryCollector] Idempotent skip — " +
                                "hash=${telemetry.documentHash.take(8)}…"
                    )
                    return@withLock
                }

                if (queue.size >= MAX_QUEUE_SIZE) {
                    val evicted = queue.removeFirst()
                    seenHashes.remove(evicted.documentHash)
                    Log.w(TAG, "Queue overflow: dropped oldest record")
                }

                queue.addLast(telemetry)
                seenHashes.add(telemetry.documentHash)

                appendToFile(telemetry.toLogLine())

                Log.i(
                    TAG,
                    "[TelemetryCollector] Recorded — " +
                            "hash=${telemetry.documentHash.take(8)}… " +
                            "session=${telemetry.sessionId.take(8)}… " +
                            "accepted=${telemetry.gate0Accepted} " +
                            "score=${telemetry.gate0Score}/${telemetry.gate0Threshold} " +
                            "reason=${telemetry.gate0Reason} " +
                            "mode=${telemetry.extractionMode} " +
                            "queueSize=${queue.size}"
                )
            }
        }
    }

    // ------------------------------------------
    // RECORD: User decision post-review
    // Append-only — never mutates TelemetryRecord
    // ------------------------------------------

    /**
     * Appends a [UserDecisionEvent] after the user completes extraction review.
     *
     * Returns immediately (Unit). Async work runs on [scope].
     * Multiple events for the same ([documentHash], [sessionId]) are permitted.
     */
    fun recordDecision(event: UserDecisionEvent) {
        scope.launch {
            lock.withLock {
                decisionLog.addLast(event)
                appendToFile(event.toLogLine())

                Log.i(
                    TAG,
                    "[TelemetryCollector] Decision — " +
                            "hash=${event.documentHash.take(8)}… " +
                            "session=${event.sessionId.take(8)}… " +
                            "type=${event.decisionType}"
                )
            }
        }
    }

    // ------------------------------------------
    // RETRIEVAL: Calibration data access
    // ------------------------------------------

    /**
     * Returns the [limit] most-recently recorded [TelemetryRecord]s from the in-memory queue.
     * For full history, read [telemetry.log] directly via [getLogFile].
     *
     * Thread-safe. Returns a snapshot; caller cannot mutate queue state.
     */
    fun getRecordsForCalibration(limit: Int): List<TelemetryRecord> {
        return lock.withLock {
            val safeLimit = minOf(limit, queue.size)
            queue.takeLast(safeLimit)
        }
    }

    /**
     * Returns all recorded [UserDecisionEvent]s in insertion order (in-memory only).
     *
     * Thread-safe. Returns a snapshot.
     */
    fun getDecisionEvents(): List<UserDecisionEvent> {
        return lock.withLock {
            decisionLog.toList()
        }
    }

    /**
     * Returns the durable [telemetry.log] file reference for external reading or export.
     * Callers must not write to this file directly.
     */
    fun getLogFile(): File = telemetryFile

    // ------------------------------------------
    // FILE I/O
    // ------------------------------------------

    /**
     * Appends [line] + newline to [telemetryFile].
     * Errors are caught and logged — never propagated to callers.
     * Called only from within [lock]-guarded blocks on [Dispatchers.IO].
     */
    private fun appendToFile(line: String) {
        try {
            telemetryFile.appendText("$line\n", Charsets.UTF_8)
        } catch (e: Exception) {
            Log.e(TAG, "[TelemetryCollector] File write failed: ${e.message}")
        }
    }

    // ------------------------------------------
    // LOG LINE FORMATTERS
    // ------------------------------------------

    private fun TelemetryRecord.toLogLine(): String =
        "GATE0\t$documentHash\t$sessionId\t$timestampMs\t" +
                "$gate0Score\t$gate0Threshold\t$gate0Accepted\t$gate0Reason\t$extractionMode"

    private fun UserDecisionEvent.toLogLine(): String =
        "DECISION\t$documentHash\t$sessionId\t$timestampMs\t$decisionType"
}
