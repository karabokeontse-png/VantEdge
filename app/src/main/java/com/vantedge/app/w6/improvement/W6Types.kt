package com.vantedge.app.w6.improvement

data class W6TraceContext(
    val correlationId: String,
    val timestamp: Long
)

enum class AxisName {
    SKILL_MATCH,
    EXPERIENCE_ALIGNMENT,
    ROLE_RELEVANCE,
    KEYWORD_COVERAGE,
    SENIORITY_FIT,
    STRUCTURAL_COMPLETENESS,
    CONSTRAINT_COMPLIANCE
}

enum class ImprovementActionType {
    ADD_SKILL,
    REPHRASE_EXPERIENCE,
    ALIGN_KEYWORDS,
    ADJUST_SENIORITY_TRAJECTORY,
    COMPLETE_PROFILE_FIELDS
}

data class ProfileImprovement(
    val fieldName: String,
    val currentValue: String?,
    val suggestedAction: String,
    val priority: Int
)

data class JobImprovement(
    val fieldName: String,
    val currentValue: String?,
    val suggestedAction: String,
    val priority: Int
)

data class GapClosureAction(
    val gapSkill: String,
    val actionType: ImprovementActionType,
    val estimatedImpact: Double,
    val rationale: String,
    val priority: Int,
    val targetAxis: AxisName
)

data class ImpactProjection(
    val targetAxis: AxisName,
    val currentScore: Double,
    val projectedDelta: Double
)

data class W6ImprovementResult(
    val correlationId: String,
    val profileImprovements: List<ProfileImprovement>,
    val jobImprovements: List<JobImprovement>,
    val gapClosurePlan: List<GapClosureAction>,
    val impactProjections: List<ImpactProjection>,
    val trace: W6TraceContext
)

data class ImprovementErrorState(
    val correlationId: String,
    val errorMessage: String,
    val trace: W6TraceContext
)
