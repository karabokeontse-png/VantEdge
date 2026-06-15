package com.vantedge.app.data.engine

import com.vantedge.app.data.model.UserProfile

data class VantEdgeScoreResult(
    val score: Int,
    val interpretation: String
)

object ScoreEngine {

    fun calculate(profile: UserProfile): VantEdgeScoreResult {

        var score = 0

        if (profile.name.isNotBlank()) score += 10
        if (profile.summary.isNotBlank()) score += 15
        if (profile.skills.isNotEmpty()) score += 20
        if (profile.workHistory.isNotEmpty()) score += 25
        if (profile.education.isNotEmpty()) score += 10
        if (profile.certifications.isNotEmpty()) score += 10
        if (profile.languages.isNotEmpty()) score += 10

        val interpretation = when {
            score >= 80 -> "Strong Profile"
            score >= 50 -> "Moderate Profile"
            else -> "Needs Improvement"
        }

        return VantEdgeScoreResult(score, interpretation)
    }
}