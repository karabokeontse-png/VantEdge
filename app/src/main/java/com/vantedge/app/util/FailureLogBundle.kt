package com.vantedge.app.util

import android.content.Context
import java.io.File

object FailureLogBundle {

    fun attachCvFailure(context: Context, error: Throwable, contextData: String? = null) {
        try {
            val file = File(context.cacheDir, "cv_failure_bundle.log")

            val content = buildString {
                appendLine("=== VANTEDGE CV GENERATION FAILURE ===")
                appendLine("Time: ${System.currentTimeMillis()}")
                appendLine()
                appendLine("=== ERROR ===")
                appendLine(error.stackTraceToString())
            }

            file.writeText(content)

        } catch (_: Exception) {
            // no fallback log dump allowed by policy
        }
    }
}
