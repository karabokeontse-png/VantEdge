package com.vantedge.app.data.model

data class GapItem(
    val skill: String,
    val importance: String,
    val description: String,
    val experienceGap: Boolean = false,
    val platformGap: Boolean = false,
    val courses: List<CourseRecommendation>
)
