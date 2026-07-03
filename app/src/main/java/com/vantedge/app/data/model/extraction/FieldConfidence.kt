package com.vantedge.app.data.model.extraction

data class FieldConfidence(
    val value: String?,
    val llmConfidence: Float,
    val validationConfidence: Float,
    val consistencyConfidence: Float,
    val overallConfidence: Float,
    val source: ExtractionMethod,
    val validationFailures: List<String>
)
