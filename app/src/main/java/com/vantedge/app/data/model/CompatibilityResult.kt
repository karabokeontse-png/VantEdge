package com.vantedge.app.data.model

data class CompatibilityResult(
    val score: Int,
    val matchedSkills: List<String>,
    val missingSkills: List<String>,
    val weakSections: List<String>,
    val suggestions: List<String>,
    val courses: List<CourseRecommendation>
)
