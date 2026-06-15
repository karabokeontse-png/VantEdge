package com.vantedge.app.domain

import android.util.Log

object PipelineTrace {
    private const val TAG = "PipelineTrace"

    fun entry(stage: String, context: Map<String, Any?>) {
        val ctx = context.entries.joinToString(" ") { "${it.key}=${it.value}" }
        Log.i(TAG, "[PIPELINE] ENTRY $stage $ctx")
    }

    fun exit(stage: String, durationMs: Long, summary: Map<String, Any?>) {
        val summ = summary.entries.joinToString(" ") { "${it.key}=${it.value}" }
        Log.i(TAG, "[PIPELINE] EXIT $stage duration=${durationMs}ms $summ")
    }

    fun error(stage: String, reason: String, throwable: Throwable? = null) {
        if (throwable != null) {
            Log.e(TAG, "[PIPELINE] ERROR $stage $reason", throwable)
        } else {
            Log.e(TAG, "[PIPELINE] ERROR $stage $reason")
        }
    }

    fun warn(stage: String, message: String) {
        Log.w(TAG, "[PIPELINE] WARN $stage $message")
    }

    fun dataQuality(stage: String, issue: String, details: Map<String, Any?>) {
        val det = details.entries.joinToString(" ") { "${it.key}=${it.value}" }
        Log.w(TAG, "[PIPELINE] DATA_QUALITY $stage $issue $det")
    }

    fun validateScore(
        stage: String,
        scoreName: String,
        value: Int,
        min: Int = 0,
        max: Int = 100
    ): Boolean {
        if (value in min..max) return true
        warn(stage, "$scoreName out of range [$min, $max]: got $value")
        return false
    }

    fun validateNonBlank(stage: String, fieldName: String, value: String?): Boolean {
        if (!value.isNullOrBlank()) return true
        warn(stage, "$fieldName is blank or null")
        return false
    }

    fun validateEnum(
        stage: String,
        fieldName: String,
        value: String?,
        allowed: Set<String>,
        fallback: String
    ): String {
        if (value != null && value in allowed) return value
        warn(stage, "$fieldName not in allowed set $allowed: got '$value', using fallback '$fallback'")
        return fallback
    }
}
