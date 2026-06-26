package com.vantedge.app.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.vantedge.app.data.storage.TelemetryStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.security.MessageDigest

class TelemetryExportService(
    private val context: Context,
    private val telemetryStorage: TelemetryStorage
) {

    companion object {
        private const val TAG = "TelemetryExportService"
        private const val EXPORT_FILE_PREFIX = "vantedge_telemetry_"
        private const val EXPORT_FILE_SUFFIX = ".log"
        private const val FILE_PROVIDER_AUTHORITY_SUFFIX = ".fileprovider"
        private const val GATE0_PREFIX = "GATE0"
        private const val SCHEMA_VERSION = 1
    }

    suspend fun createExportFile(): Pair<File, String>? {
        return withContext(Dispatchers.IO) {
            try {
                val records = telemetryStorage.getExportRecords()
                val file = File(
                    context.cacheDir,
                    "${EXPORT_FILE_PREFIX}${System.currentTimeMillis()}$EXPORT_FILE_SUFFIX"
                )

                file.bufferedWriter(Charsets.UTF_8).use { writer ->
                    writer.write("SCHEMA_VERSION\t$SCHEMA_VERSION\n")
                    for (record in records) {
                        writer.write(
                            "$GATE0_PREFIX\t${record.documentHash}\t${record.sessionId}\t" +
                                    "${record.timestampMs}\t${record.gate0Score}\t" +
                                    "${record.gate0Threshold}\t${record.gate0Accepted}\t" +
                                    "${record.gate0Reason}\t${record.extractionMode}\n"
                        )
                    }
                }

                val digest = MessageDigest.getInstance("SHA-256")
                val checksum = digest.digest(file.readBytes())
                    .joinToString("") { "%02x".format(it) }

                Pair(file, checksum)
            } catch (e: Exception) {
                Log.e(TAG, "[TelemetryExportService] Export failed: ${e.message}")
                null
            }
        }
    }

    fun getShareUri(exportFile: File): Uri? {
        return try {
            FileProvider.getUriForFile(
                context,
                "${context.packageName}$FILE_PROVIDER_AUTHORITY_SUFFIX",
                exportFile
            )
        } catch (e: Exception) {
            Log.e(TAG, "[TelemetryExportService] FileProvider URI failed: ${e.message}")
            null
        }
    }

    fun getShareIntent(exportFile: File): Intent? {
        val uri = getShareUri(exportFile) ?: return null
        return Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "VantEdge Telemetry Export")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
}
