package com.vantedge.app.w6.improvement

import java.util.Locale

object W6Constants {
    const val CRITICAL_IMPACT_THRESHOLD = 0.15
    const val IMPORTANT_IMPACT_THRESHOLD = 0.08

    const val PRIORITY_CRITICAL = 1
    const val PRIORITY_IMPORTANT = 2
    const val PRIORITY_NICE_TO_HAVE = 3
}

object W6Templates {
    fun addSkill(skill: String, axisName: AxisName, delta: Double): String {
        val formattedDelta = String.format(Locale.US, "%.2f", delta)
        return "Add '$skill' to your profile to improve ${axisName.name} score by $formattedDelta."
    }

    fun rephraseExperience(aspect: String, axisName: AxisName): String {
        return "Highlight $aspect in your work history to improve ${axisName.name} score."
    }

    fun alignKeywords(skill: String, axisName: AxisName, delta: Double): String {
        val formattedDelta = String.format(Locale.US, "%.2f", delta)
        return "Emphasize '$skill' in your profile descriptions to improve keyword coverage by $formattedDelta."
    }

    fun adjustSeniorityTrajectory(targetLevel: String, axisName: AxisName): String {
        return "Target $targetLevel roles to improve ${axisName.name} score."
    }

    fun completeProfileFields(field: String, axisName: AxisName): String {
        return "Complete '$field' in your profile to improve ${axisName.name} score."
    }
}

object AxisNameMapping {
    private val w5ToW6 = mapOf(
        "SkillMatch" to AxisName.SKILL_MATCH,
        "ExperienceAlignment" to AxisName.EXPERIENCE_ALIGNMENT,
        "RoleRelevance" to AxisName.ROLE_RELEVANCE,
        "KeywordCoverage" to AxisName.KEYWORD_COVERAGE,
        "SeniorityFit" to AxisName.SENIORITY_FIT,
        "StructuralCompleteness" to AxisName.STRUCTURAL_COMPLETENESS,
        "ConstraintCompliance" to AxisName.CONSTRAINT_COMPLIANCE
    )

    fun fromW5AxisName(w5Name: String): AxisName {
        return w5ToW6[w5Name] ?: error("Unknown W5 axis name: $w5Name")
    }
}
