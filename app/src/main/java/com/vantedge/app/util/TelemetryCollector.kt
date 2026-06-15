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

    private val seenHashes = LinkedHashSet<String>(MAX_QUEUE_SIZE)
    private val queue = ArrayDeque<TelemetryRecord>(MAX_QUEUE_SIZE)
    private val decisionLog = ArrayDeque<UserDecisionEvent>()

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

    fun getRecordsForCalibration(limit: Int): List<TelemetryRecord> {
        return lock.withLock {
            val safeLimit = minOf(limit, queue.size)
            queue.takeLast(safeLimit)
        }
    }

    fun getDecisionEvents(): List<UserDecisionEvent> {
        return lock.withLock {
            decisionLog.toList()
        }
    }

    fun getLogFile(): File = telemetryFile

    private fun appendToFile(line: String) {
        try {
            telemetryFile.appendText("$line\n", Charsets.UTF_8)
        } catch (e: Exception) {
            Log.e(TAG, "[TelemetryCollector] File write failed: ${e.message}")
        }
    }

    private fun TelemetryRecord.toLogLine(): String =
        "GATE0\t$documentHash\t$sessionId\t$timestampMs\t" +
                "$gate0Score\t$gate0Threshold\t$gate0Accepted\t$gate0Reason\t$extractionMode"

    private fun UserDecisionEvent.toLogLine(): String =
        "DECISION\t$documentHash\t$sessionId\t$timestampMs\t$decisionType"
}
