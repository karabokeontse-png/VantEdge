package com.vantedge.app.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

object LogSink {
    private const val TAG = "LogSink"
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)
    private val sessionId: String = UUID.randomUUID().toString().take(8)

    fun write(
        level: String,
        stage: String,
        correlationId: String?,
        message: String
    ) {
        val file = try {
            LogFileProvider.file()
        } catch (e: Exception) {
            Log.e(TAG, "LogFileProvider not bound: ${e.message}")
            return
        }
        val timestamp = dateFormat.format(Date())
        val cid = correlationId ?: sessionId
        val line = "$timestamp\t$level\t$stage\t$cid\t$message\n"
        try {
            file.appendText(line, Charsets.UTF_8)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to write trace: ${e.message}")
        }
    }
}
