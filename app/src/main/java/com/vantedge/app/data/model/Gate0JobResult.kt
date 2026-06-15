package com.vantedge.app.data.model

data class Gate0JobResult(
    val score: Int,
    val threshold: Int,
    val accepted: Boolean,
    val reason: Gate0JobReason,
    val detectedSignals: List<String>,
    val appliedPenalties: List<String>,
    val rejectionCauses: List<String>,
    val narrativeDensity: Float
)
