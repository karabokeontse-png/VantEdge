package com.vantedge.app.util

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

object LogDumper {

    fun dumpAndShare(context: Context) {
        val logFile = File(context.cacheDir, "vantedge_debug.log")

        try {
            val process = Runtime.getRuntime().exec(
                arrayOf(
                    "logcat",
                    "-d",
                    "-v",
                    "threadtime",
                    "-t",
                    "1000",
                    "*:V"
                )
            )

            val logs = process.inputStream.bufferedReader().use { it.readText() }
            val errors = process.errorStream.bufferedReader().use { it.readText() }

            process.inputStream.close()
            process.errorStream.close()

            process.waitFor()

            val finalText = buildString {
                appendLine("=== VANTEDGE LOG DUMP ===")
                appendLine("Device: ${Build.MANUFACTURER} ${Build.MODEL}")
                appendLine("Android: ${Build.VERSION.SDK_INT}")
                appendLine("Exit code: ${process.exitValue()}")
                appendLine("====================================")
                appendLine()

                appendLine("=== LOGS ===")
                appendLine(logs)

                if (errors.isNotBlank()) {
                    appendLine()
                    appendLine("=== ERRORS ===")
                    appendLine(errors)
                }
            }

            logFile.writeText(finalText)

            shareFile(context, logFile)

        } catch (e: Exception) {
            handleFallback(context, e)
        }
    }

    private fun shareFile(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooserIntent = Intent.createChooser(intent, "Share VantEdge Logs").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(chooserIntent)
    }

    private fun handleFallback(context: Context, e: Exception) {
        try {
            val fallback = File(context.cacheDir, "vantedge_log_error.txt")

            fallback.writeText(
                """
                Log dump failed
                
                Error: ${e.message}
                
                Stack:
                ${e.stackTraceToString()}
                """.trimIndent()
            )

            shareFile(context, fallback)

        } catch (_: Exception) {
        }
    }
}