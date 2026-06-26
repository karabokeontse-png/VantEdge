package com.vantedge.app.data.network

data class ModelAttemptResult(
    val requestId: String,
    val model: String,
    val attempt: Int,
    val failureType: String?,
    val failureStage: String? = null,
    val httpCode: Int?,
    val durationMs: Long,
    val latencyMs: Long?,
    val bodyPreview: String?,
    val detail: String?,
    val cancelReason: String?
)
