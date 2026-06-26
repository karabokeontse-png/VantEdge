package com.vantedge.app.data.storage

import android.content.Context
import android.util.Log
import com.vantedge.app.data.model.TelemetryRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class TelemetryStorage(context: Context) {

    companion object {
        private const val TAG = "TelemetryStorage"
        private const val LOG_FILE_NAME = "telemetry.log"
        private const val GATE0_PREFIX = "GATE0"
        private const val GATE0_FIELD_COUNT = 9
        const val MAX_DISPLAY_RECORDS = 20
        const val MAX_EXPORT_RECORDS = 500
    }

    private val liveFile = File(context.filesDir, LOG_FILE_NAME)

    suspend fun getRecentRecords(limit: Int = MAX_DISPLAY_RECORDS): List<TelemetryRecord> {
        return withContext(Dispatchers.IO) {
            readGate0Records(limit)
        }
    }

    suspend fun getExportRecords(): List<TelemetryRecord> {
        return withContext(Dispatchers.IO) {
            readGate0Records(MAX_EXPORT_RECORDS)
        }
    }

    fun getLiveFileReference(): File = liveFile

    private fun readGate0Records(limit: Int): List<TelemetryRecord> {
        if (!liveFile.exists()) return emptyList()

        val records = mutableListOf<TelemetryRecord>()

        liveFile.useLines { lines ->
            for (line in lines) {
                if (line.startsWith(GATE0_PREFIX)) {
                    parseLine(line)?.let { records.add(it) }
                }
            }
        }

        return records.takeLast(limit)
    }

    private fun parseLine(line: String): TelemetryRecord? {
        try {
            val parts = line.split("\t")
            if (parts.size != GATE0_FIELD_COUNT) {
                Log.d(
                    TAG,
                    "[TelemetryStorage] Skipping malformed line " +
                            "(expected=$GATE0_FIELD_COUNT fields, got=${parts.size})"
                )
                return null
            }
            return TelemetryRecord(
                documentHash = parts[1],
                sessionId = parts[2],
                timestampMs = parts[3].toLong(),
                gate0Score = parts[4].toInt(),
                gate0Threshold = parts[5].toInt(),
                gate0Accepted = parts[6].toBoolean(),
                gate0Reason = parts[7],
                extractionMode = parts[8]
            )
        } catch (e: Exception) {
            Log.d(TAG, "[TelemetryStorage] Parse exception on line: ${e.message}")
            return null
        }
    }
}
