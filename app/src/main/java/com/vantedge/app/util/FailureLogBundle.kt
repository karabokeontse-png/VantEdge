package com.vantedge.app.util

import android.content.Context
import java.io.File

object FailureLogBundle {

    fun attachCvFailure(context: Context, error: Throwable, contextData: String? = null) {
        try {
            val file = File(context.cacheDir, "cv_failure_bundle.log")

            val logDump = Runtime.getRuntime().exec(
                arrayOf(
                    "logcat",
                    "-d",
                    "-v",
                    "threadtime",
                    "-t",
                    "800",
                    "*:D"
                )
            )

            val logs = logDump.inputStream.bufferedReader().use { it.readText() }
            logDump.destroy()

            val content = buildString {
                appendLine("=== VANTEDGE CV GENERATION FAILURE ===")
                appendLine("Time: ${System.currentTimeMillis()}")
                appendLine()
                appendLine("=== ERROR ===")
                appendLine(error.stackTraceToString())
                appendLine()

                if (!contextData.isNullOrBlank()) {
                    appendLine("=== CONTEXT DATA ===")
                    appendLine(contextData)
                    appendLine()
                }

                appendLine("=== LOGCAT SNAPSHOT ===")
                appendLine(logs)
            }

            file.writeText(content)

            LogDumper.dumpAndShare(context)

        } catch (e: Exception) {
            // last fallback: just raw logs
            LogDumper.dumpAndShare(context)
        }
    }
}