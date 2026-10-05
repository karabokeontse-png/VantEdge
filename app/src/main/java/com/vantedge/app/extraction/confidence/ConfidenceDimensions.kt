package com.vantedge.app.extraction.confidence

data class ConfidenceDimensions(
    val structural: Double,
    val content: Double,
    val semantic: Double,
    val sourceQuality: Double,
    val overall: Double
)

data class ConfidenceBreakdown(
    val dimensions: ConfidenceDimensions,
    val warnings: List<String>,
    val fieldDetails: Map<String, FieldConfidence>
)

data class FieldConfidence(
    val structural: Double,
    val content: Double,
    val semantic: Double,
    val sourceQuality: Double
)
