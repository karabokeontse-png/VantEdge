package com.vantedge.app.w6.improvement

import java.util.Locale

object W6Priority {
    const val CRITICAL = 1
    const val IMPORTANT = 2
    const val NICE_TO_HAVE = 3

    const val CRITICAL_THRESHOLD = 0.15
    const val IMPORTANT_THRESHOLD = 0.08
}

object W6Templates {
    fun addSkill(skill: String, axis: String, delta: Double): String {
        val formatted = String.format(Locale.US, "%.2f", delta)
        return "Add '$skill' to your profile to improve $axis score by $formatted."
    }

    fun rephraseExperience(aspect: String, axis: String): String =
        "Highlight $aspect in your work history to improve $axis score."

    fun alignKeywords(skill: String): String =
        "Emphasize '$skill' in your profile descriptions to improve keyword coverage."

    fun adjustSeniorityTrajectory(level: String): String =
        "Target $level roles to improve seniority fit."

    fun completeProfileFields(field: String): String =
        "Complete '$field' in your profile to improve structural completeness."
}

object W6GapWeights {
    const val SKILL_MATCH = 0.25
    const val EXPERIENCE_ALIGNMENT = 0.20
    const val ROLE_RELEVANCE = 0.15
    const val KEYWORD_COVERAGE = 0.10
    const val SENIORITY_FIT = 0.10
    const val STRUCTURAL_COMPLETENESS = 0.10
    const val CONSTRAINT_COMPLIANCE = 0.10
}
