package com.vantedge.app.data.model

data class LearningRecommendation(
    val skill: String,
    val type: String,
    val priority: GapSeverity
)