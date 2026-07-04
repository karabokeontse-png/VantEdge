package com.vantedge.app.w5.scoring

object AxisWeights {
    private val weights = mapOf(
        "SkillMatch" to 0.25,
        "ExperienceAlignment" to 0.20,
        "RoleRelevance" to 0.15,
        "KeywordCoverage" to 0.10,
        "SeniorityFit" to 0.10,
        "StructuralCompleteness" to 0.10,
        "ConstraintCompliance" to 0.10
    )

    fun get(axisName: String): Double = weights[axisName] ?: 0.0
}

object RequiredProfileFields {
    val fields = setOf(
        "skills",
        "roles",
        "currentTitle",
        "experienceYears",
        "seniorityLevel"
    )
}

object RequiredJobFields {
    val fields = setOf(
        "requiredSkills",
        "title",
        "keywords",
        "requiredYears",
        "seniorityLevel"
    )
}
