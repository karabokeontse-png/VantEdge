package com.vantedge.app.data.model

data class GapAnalysisResult(
    val matchedSkills: List<String>,
    val missingSkills: List<String>,
    val skillGaps: List<SkillGap>,
    val transferableSkills: List<String>,
    val learningRecommendations: List<LearningRecommendation>,
    val gapScore: Int
)