package com.vantedge.app.data.model

data class ExtractedField(
    val value: String,
    val confidence: Float,
    val sourceType: ExtractionSourceType
)
