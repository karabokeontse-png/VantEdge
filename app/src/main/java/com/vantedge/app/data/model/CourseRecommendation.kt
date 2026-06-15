package com.vantedge.app.data.model

data class CourseRecommendation(
    val title: String,
    val provider: String,
    val url: String,
    val category: String,
    val hasCertificate: Boolean,
    val estimatedDuration: String,
    val relevancyPercent: Int = 0,
    val priority: Int = 1
)
