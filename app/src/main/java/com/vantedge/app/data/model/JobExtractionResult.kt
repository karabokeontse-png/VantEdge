package com.vantedge.app.data.model


data class JobExtractionResult(
    val extractionId: String,
    val extractedAt: Long,
    val jobTitle: String?,
    val company: String?,
    val description: String,
    val requiredSkills: List<String> = emptyList(),
    val preferredSkills: List<String> = emptyList(),
    val confidenceScore: Float,
    val confidenceBreakdown: ConfidenceBreakdown,
    val gate0Result: Gate0JobResult,
    val metrics: JobExtractionMetrics,
    val educationRequired: String? = null
)
