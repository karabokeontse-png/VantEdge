package com.vantedge.app.w6.improvement

import com.vantedge.app.w5.scoring.AxisScore
import com.vantedge.app.w5.scoring.GapAnalysis
import com.vantedge.app.w5.scoring.ScoreResult
import com.vantedge.app.w5.scoring.ValidatedJob
import com.vantedge.app.w5.scoring.ValidatedProfile

object W6Rules {

    fun generateGapClosurePlan(
        gaps: GapAnalysis,
        score: ScoreResult
    ): List<GapClosureAction> {
        val actions = mutableListOf<GapClosureAction>()

        val skillMatchScore = score.axisScores.find { it.axisName == "SkillMatch" }?.score ?: 0.0

        for (skill in gaps.missing) {
            val impact = computeEstimatedImpact(skillMatchScore)
            actions.add(
                GapClosureAction(
                    gapSkill = skill,
                    actionType = ImprovementActionType.ADD_SKILL,
                    rationale = W6Templates.addSkill(skill, AxisName.SKILL_MATCH, impact),
                    priority = computePriority(impact),
                    estimatedImpact = impact
                )
            )
        }

        for (skill in gaps.weak) {
            val impact = computeEstimatedImpact(skillMatchScore)
            actions.add(
                GapClosureAction(
                    gapSkill = skill,
                    actionType = ImprovementActionType.ALIGN_KEYWORDS,
                    rationale = W6Templates.alignKeywords(skill, AxisName.KEYWORD_COVERAGE, impact),
                    priority = computePriority(impact),
                    estimatedImpact = impact
                )
            )
        }

        return actions.sortedWith(
            compareBy<GapClosureAction> { it.priority }
                .thenByDescending { it.estimatedImpact }
        )
    }

    fun generateProfileImprovements(
        profile: ValidatedProfile,
        score: ScoreResult
    ): List<ProfileImprovement> {
        val improvements = mutableListOf<ProfileImprovement>()

        val experienceScore = score.axisScores.find { it.axisName == "ExperienceAlignment" }?.score ?: 0.0
        if (experienceScore < 1.0) {
            val impact = computeEstimatedImpact(experienceScore)
            improvements.add(
                ProfileImprovement(
                    fieldName = "workHistory",
                    actionType = ImprovementActionType.REPHRASE_EXPERIENCE,
                    rationale = W6Templates.rephraseExperience("relevant duration", AxisName.EXPERIENCE_ALIGNMENT),
                    priority = computePriority(impact)
                )
            )
        }

        val seniorityScore = score.axisScores.find { it.axisName == "SeniorityFit" }?.score ?: 0.0
        if (seniorityScore < 1.0) {
            val impact = computeEstimatedImpact(seniorityScore)
            val targetLevel = profile.seniorityLevel ?: "appropriate"
            improvements.add(
                ProfileImprovement(
                    fieldName = "seniorityLevel",
                    actionType = ImprovementActionType.ADJUST_SENIORITY_TRAJECTORY,
                    rationale = W6Templates.adjustSeniorityTrajectory(targetLevel, AxisName.SENIORITY_FIT),
                    priority = computePriority(impact)
                )
            )
        }

        val structuralScore = score.axisScores.find { it.axisName == "StructuralCompleteness" }?.score ?: 0.0
        if (structuralScore < 1.0) {
            val missingFields = computeMissingProfileFields(profile)
            for (field in missingFields) {
                val impact = computeEstimatedImpact(structuralScore)
                improvements.add(
                    ProfileImprovement(
                        fieldName = field,
                        actionType = ImprovementActionType.COMPLETE_PROFILE_FIELDS,
                        rationale = W6Templates.completeProfileFields(field, AxisName.STRUCTURAL_COMPLETENESS),
                        priority = computePriority(impact)
                    )
                )
            }
        }

        return improvements.sortedWith(
            compareBy<ProfileImprovement> { it.priority }
                .thenBy { it.fieldName }
        )
    }

    fun generateJobImprovements(
        job: ValidatedJob,
        score: ScoreResult
    ): List<JobImprovement> {
        val improvements = mutableListOf<JobImprovement>()

        val roleScore = score.axisScores.find { it.axisName == "RoleRelevance" }?.score ?: 0.0
        if (roleScore < 1.0) {
            val impact = computeEstimatedImpact(roleScore)
            improvements.add(
                JobImprovement(
                    fieldName = "title",
                    actionType = ImprovementActionType.ALIGN_KEYWORDS,
                    rationale = W6Templates.alignKeywords(job.title, AxisName.ROLE_RELEVANCE, impact),
                    priority = computePriority(impact)
                )
            )
        }

        return improvements.sortedWith(
            compareBy<JobImprovement> { it.priority }
                .thenBy { it.fieldName }
        )
    }

    fun generateImpactProjections(
        score: ScoreResult
    ): List<ImpactProjection> {
        val projections = mutableListOf<ImpactProjection>()

        for (axisScore in score.axisScores) {
            if (axisScore.score < 1.0) {
                val axisName = AxisNameMapping.fromW5AxisName(axisScore.axisName)
                val delta = computeProjectedDelta(axisScore.score)
                val actionType = when (axisName) {
                    AxisName.SKILL_MATCH -> ImprovementActionType.ADD_SKILL
                    AxisName.EXPERIENCE_ALIGNMENT -> ImprovementActionType.REPHRASE_EXPERIENCE
                    AxisName.ROLE_RELEVANCE -> ImprovementActionType.ALIGN_KEYWORDS
                    AxisName.KEYWORD_COVERAGE -> ImprovementActionType.ALIGN_KEYWORDS
                    AxisName.SENIORITY_FIT -> ImprovementActionType.ADJUST_SENIORITY_TRAJECTORY
                    AxisName.STRUCTURAL_COMPLETENESS -> ImprovementActionType.COMPLETE_PROFILE_FIELDS
                    AxisName.CONSTRAINT_COMPLIANCE -> ImprovementActionType.COMPLETE_PROFILE_FIELDS
                }

                projections.add(
                    ImpactProjection(
                        targetAxis = axisName,
                        currentScore = axisScore.score,
                        projectedDelta = delta,
                        actionType = actionType
                    )
                )
            }
        }

        return projections.sortedWith(
            compareByDescending<ImpactProjection> { it.projectedDelta }
        )
    }

    private fun computeEstimatedImpact(currentScore: Double): Double {
        return (1.0 - currentScore) * 0.5
    }

    private fun computeProjectedDelta(currentScore: Double): Double {
        return (1.0 - currentScore) * 0.5
    }

    private fun computePriority(impact: Double): Int {
        return when {
            impact > W6Constants.CRITICAL_IMPACT_THRESHOLD -> W6Constants.PRIORITY_CRITICAL
            impact > W6Constants.IMPORTANT_IMPACT_THRESHOLD -> W6Constants.PRIORITY_IMPORTANT
            else -> W6Constants.PRIORITY_NICE_TO_HAVE
        }
    }

    private fun computeMissingProfileFields(profile: ValidatedProfile): List<String> {
        val required = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel")
        return required.filter { it !in profile.completedFields }
    }
}
