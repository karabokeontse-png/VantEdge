package com.vantedge.app.data.model

data class ConfidenceBreakdown(
    val baseScore: Float,
    val titleContribution: Float,
    val companyContribution: Float,
    val qualificationContribution: Float,
    val penalties: List<String>,
    val finalScore: Float
)
