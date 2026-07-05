package com.vantedge.app.w6.improvement

import com.vantedge.app.w5.scoring.ScoreResult
import com.vantedge.app.w5.scoring.ValidatedProfile
import com.vantedge.app.w5.scoring.ValidatedJob
import com.vantedge.app.w5.scoring.RequiredProfileFields
import com.vantedge.app.w5.scoring.RequiredJobFields

class W6Engine {
    fun improve(
        scoreResult: ScoreResult,
        profile: ValidatedProfile,
        job: ValidatedJob,
        trace: W6TraceContext
    ): W6ImprovementResult {
        val axisScores = scoreResult.axisScores.mapNotNull { axis ->
            val name = W6Rules.mapAxisName(axis.axisName)
            name?.let { it to axis.score }
        }.toMap()

        val gapClosureActions = buildGapClosureActions(scoreResult, axisScores, profile)
        val profileImprovements = buildProfileImprovements(profile)
        val jobImprovements = buildJobImprovements(job)
        val impactProjections = buildImpactProjections(axisScores, gapClosureActions)

        val sortedProfile = profileImprovements.sortedWith(compareBy({ it.priority }, { it.fieldName }))
        val sortedJob = jobImprovements.sortedWith(compareBy({ it.priority }, { it.fieldName }))
        val sortedGaps = gapClosureActions.sortedWith(compareBy({ it.priority }, { -it.estimatedImpact }))
        val sortedProjections = impactProjections.sortedByDescending { it.projectedDelta }

        return W6ImprovementResult(
            correlationId = scoreResult.correlationId,
            profileImprovements = sortedProfile,
            jobImprovements = sortedJob,
            gapClosurePlan = sortedGaps,
            impactProjections = sortedProjections,
            trace = trace
        )
    }

    private fun buildGapClosureActions(
        scoreResult: ScoreResult,
        axisScores: Map<AxisName, Double>,
        profile: ValidatedProfile
    ): List<GapClosureAction> {
        val actions = mutableListOf<GapClosureAction>()

        val missingSkills = scoreResult.gapAnalysis.missing
        val weakSkills = scoreResult.gapAnalysis.weak
        val missingCount = missingSkills.size.coerceAtLeast(1)
        val weakCount = weakSkills.size.coerceAtLeast(1)

        val currentSkillMatch = axisScores[AxisName.SKILL_MATCH] ?: 0.0
        val currentKeywordCoverage = axisScores[AxisName.KEYWORD_COVERAGE] ?: 0.0
        val currentExpAlignment = axisScores[AxisName.EXPERIENCE_ALIGNMENT] ?: 0.0
        val currentSeniorityFit = axisScores[AxisName.SENIORITY_FIT] ?: 0.0
        val currentStructural = axisScores[AxisName.STRUCTURAL_COMPLETENESS] ?: 0.0

        for (skill in missingSkills) {
            val gapWeight = 1.0 / missingCount
            val impact = W6Rules.calculateEstimatedImpact(currentSkillMatch, gapWeight)
            val priority = W6Rules.assignPriority(impact)
            actions.add(
                GapClosureAction(
                    gapSkill = skill,
                    actionType = ImprovementActionType.ADD_SKILL,
                    estimatedImpact = impact,
                    rationale = W6Templates.addSkill(skill, "SkillMatch", impact),
                    priority = priority,
                    targetAxis = AxisName.SKILL_MATCH
                )
            )
        }

        for (skill in weakSkills) {
            val gapWeight = 1.0 / weakCount
            val impact = W6Rules.calculateEstimatedImpact(currentKeywordCoverage, gapWeight)
            val priority = W6Rules.assignPriority(impact)
            actions.add(
                GapClosureAction(
                    gapSkill = skill,
                    actionType = ImprovementActionType.ALIGN_KEYWORDS,
                    estimatedImpact = impact,
                    rationale = W6Templates.alignKeywords(skill),
                    priority = priority,
                    targetAxis = AxisName.KEYWORD_COVERAGE
                )
            )
        }

        if (currentExpAlignment < 0.5) {
            val impact = (1.0 - currentExpAlignment) * W6GapWeights.EXPERIENCE_ALIGNMENT
            val priority = W6Rules.assignPriority(impact)
            actions.add(
                GapClosureAction(
                    gapSkill = "",
                    actionType = ImprovementActionType.REPHRASE_EXPERIENCE,
                    estimatedImpact = impact,
                    rationale = W6Templates.rephraseExperience("relevant experience", "ExperienceAlignment"),
                    priority = priority,
                    targetAxis = AxisName.EXPERIENCE_ALIGNMENT
                )
            )
        }

        if (currentSeniorityFit < 0.5) {
            val impact = (1.0 - currentSeniorityFit) * W6GapWeights.SENIORITY_FIT
            val priority = W6Rules.assignPriority(impact)
            actions.add(
                GapClosureAction(
                    gapSkill = "",
                    actionType = ImprovementActionType.ADJUST_SENIORITY_TRAJECTORY,
                    estimatedImpact = impact,
                    rationale = W6Templates.adjustSeniorityTrajectory("seniority-matched"),
                    priority = priority,
                    targetAxis = AxisName.SENIORITY_FIT
                )
            )
        }

        if (currentStructural < 1.0) {
            val missingFields = RequiredProfileFields.fields - profile.completedFields
            if (missingFields.isNotEmpty()) {
                val perFieldImpact = ((1.0 - currentStructural) * W6GapWeights.STRUCTURAL_COMPLETENESS) /
                    missingFields.size
                for (field in missingFields) {
                    val priority = W6Rules.assignPriority(perFieldImpact)
                    actions.add(
                        GapClosureAction(
                            gapSkill = "",
                            actionType = ImprovementActionType.COMPLETE_PROFILE_FIELDS,
                            estimatedImpact = perFieldImpact,
                            rationale = W6Templates.completeProfileFields(field),
                            priority = priority,
                            targetAxis = AxisName.STRUCTURAL_COMPLETENESS
                        )
                    )
                }
            }
        }

        return actions
    }

    private fun buildProfileImprovements(profile: ValidatedProfile): List<ProfileImprovement> {
        val missingFields = RequiredProfileFields.fields - profile.completedFields
        return missingFields.map { field ->
            val currentValue = when (field) {
                "skills" -> if (profile.skills.isNotEmpty()) profile.skills.joinToString(", ") else null
                "roles" -> if (profile.roles.isNotEmpty()) profile.roles.joinToString(", ") else null
                "currentTitle" -> profile.currentTitle
                "experienceYears" -> profile.experienceYears.toString()
                "seniorityLevel" -> profile.seniorityLevel
                else -> null
            }
            ProfileImprovement(
                fieldName = field,
                currentValue = currentValue,
                suggestedAction = "Add '$field' to improve profile completeness.",
                priority = W6Priority.IMPORTANT
            )
        }
    }

    private fun buildJobImprovements(job: ValidatedJob): List<JobImprovement> {
        val missingFields = RequiredJobFields.fields - job.completedFields
        return missingFields.map { field ->
            val currentValue = when (field) {
                "requiredSkills" -> if (job.requiredSkills.isNotEmpty()) job.requiredSkills.joinToString(", ") else null
                "title" -> job.title.ifEmpty { null }
                "keywords" -> if (job.keywords.isNotEmpty()) job.keywords.joinToString(", ") else null
                "requiredYears" -> job.requiredYears?.toString()
                "seniorityLevel" -> job.seniorityLevel
                else -> null
            }
            JobImprovement(
                fieldName = field,
                currentValue = currentValue,
                suggestedAction = "Provide '$field' in the job posting.",
                priority = W6Priority.IMPORTANT
            )
        }
    }

    private fun buildImpactProjections(
        axisScores: Map<AxisName, Double>,
        gapClosureActions: List<GapClosureAction>
    ): List<ImpactProjection> {
        return AxisName.values().mapNotNull { axisName ->
            val currentScore = axisScores[axisName] ?: return@mapNotNull null
            val actionsForAxis = gapClosureActions.filter { it.targetAxis == axisName }
            if (actionsForAxis.isEmpty()) return@mapNotNull null
            val projectedDelta = W6Rules.calculateProjectedDelta(
                actionsForAxis.map { it.estimatedImpact },
                W6Rules.axisWeight(axisName)
            )
            ImpactProjection(
                targetAxis = axisName,
                currentScore = currentScore,
                projectedDelta = projectedDelta
            )
        }
    }
}
