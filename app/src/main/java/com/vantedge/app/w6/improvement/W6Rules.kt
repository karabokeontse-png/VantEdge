package com.vantedge.app.w6.improvement

object W6Rules {
    fun mapAxisName(w5AxisName: String): AxisName? = when (w5AxisName) {
        "SkillMatch" -> AxisName.SKILL_MATCH
        "ExperienceAlignment" -> AxisName.EXPERIENCE_ALIGNMENT
        "RoleRelevance" -> AxisName.ROLE_RELEVANCE
        "KeywordCoverage" -> AxisName.KEYWORD_COVERAGE
        "SeniorityFit" -> AxisName.SENIORITY_FIT
        "StructuralCompleteness" -> AxisName.STRUCTURAL_COMPLETENESS
        "ConstraintCompliance" -> AxisName.CONSTRAINT_COMPLIANCE
        else -> null
    }

    fun assignPriority(estimatedImpact: Double): Int = when {
        estimatedImpact > W6Priority.CRITICAL_THRESHOLD -> W6Priority.CRITICAL
        estimatedImpact >= W6Priority.IMPORTANT_THRESHOLD -> W6Priority.IMPORTANT
        else -> W6Priority.NICE_TO_HAVE
    }

    fun axisWeight(axisName: AxisName): Double = when (axisName) {
        AxisName.SKILL_MATCH -> W6GapWeights.SKILL_MATCH
        AxisName.EXPERIENCE_ALIGNMENT -> W6GapWeights.EXPERIENCE_ALIGNMENT
        AxisName.ROLE_RELEVANCE -> W6GapWeights.ROLE_RELEVANCE
        AxisName.KEYWORD_COVERAGE -> W6GapWeights.KEYWORD_COVERAGE
        AxisName.SENIORITY_FIT -> W6GapWeights.SENIORITY_FIT
        AxisName.STRUCTURAL_COMPLETENESS -> W6GapWeights.STRUCTURAL_COMPLETENESS
        AxisName.CONSTRAINT_COMPLIANCE -> W6GapWeights.CONSTRAINT_COMPLIANCE
    }

    fun calculateEstimatedImpact(currentScore: Double, gapWeight: Double): Double {
        return (1.0 - currentScore) * gapWeight
    }

    fun calculateProjectedDelta(impacts: List<Double>, axisWeight: Double): Double {
        return impacts.sum() * axisWeight
    }
}
