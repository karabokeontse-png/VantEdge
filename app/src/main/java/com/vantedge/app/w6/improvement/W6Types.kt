package com.vantedge.app.w6.improvement

import com.vantedge.app.w5.scoring.AxisScore
import com.vantedge.app.w5.scoring.GapAnalysis
import com.vantedge.app.w5.scoring.RecommendationHints
import com.vantedge.app.w5.scoring.ScoreResult
import com.vantedge.app.w5.scoring.ValidatedJob
import com.vantedge.app.w5.scoring.ValidatedProfile

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

data class W6Input(
    val profile: ValidatedProfile,
    val job: ValidatedJob,
    val score: ScoreResult,
    val gaps: GapAnalysis,
    val hints: RecommendationHints,
    val correlationId: String
)

data class W6ImprovementResult(
    val profileImprovements: List<ProfileImprovement>,
    val jobImprovements: List<JobImprovement>,
    val gapClosurePlan: List<GapClosureAction>,
    val impactProjections: List<ImpactProjection>,
    val correlationId: String
)

data class ImpactProjection(
    val targetAxis: AxisName,
    val currentScore: Double,
    val projectedDelta: Double,
    val actionType: ImprovementActionType
)

data class ProfileImprovement(
    val fieldName: String,
    val actionType: ImprovementActionType,
    val rationale: String,
    val priority: Int
)

data class JobImprovement(
    val fieldName: String,
    val actionType: ImprovementActionType,
    val rationale: String,
    val priority: Int
)

data class GapClosureAction(
    val gapSkill: String,
    val actionType: ImprovementActionType,
    val rationale: String,
    val priority: Int,
    val estimatedImpact: Double
)
