package com.vantedge.app.data.model

data class JobExtractionMetrics(
    val durationMs: Long,
    val sourceType: JobSourceType,
    val rawTextLength: Int,
    val gate0DurationMs: Long,
    val gate1DurationMs: Long,
    val gate2DurationMs: Long,
    val gate3DurationMs: Long,
    val gate4DurationMs: Long,
    val qualificationPassed: Boolean,
    val narrativeDensity: Float
)
