package com.vantedge.app.data.engine

import com.vantedge.app.data.model.UserProfile

data class AtsResult(
    val score: Int,
    val keywords: List<String>,
    val missingKeywords: List<String>
)

object ATSEngine {

    fun analyze(profile: UserProfile, jobDescription: String): AtsResult {
        val keywords = jobDescription.split(" ").distinct().take(10)
        val missing = keywords.filter { !profile.skills.contains(it) }

        return AtsResult(
            score = (100 - missing.size * 5).coerceAtLeast(0),
            keywords = keywords,
            missingKeywords = missing
        )
    }
}