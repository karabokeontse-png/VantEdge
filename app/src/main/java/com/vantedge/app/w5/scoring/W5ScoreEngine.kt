package com.vantedge.app.w5.scoring

import kotlin.math.min

class W5ScoreEngine(private val assets: ValidationAssets) {
    fun evaluate(profile: ValidatedProfile, job: ValidatedJob, trace: W5TraceContext): ScoreResult {
        val inputHash = computeInputHash(profile, job)
        val outputTrace = W5TraceContext(trace.correlationId, inputHash, trace.timestamp)

        val skillMatch = AxisEvaluators.skillMatch(profile, job, assets)
        val experienceAlignment = AxisEvaluators.experienceAlignment(profile, job)
        val roleRelevance = AxisEvaluators.roleRelevance(profile, job, assets)
        val keywordCoverage = AxisEvaluators.keywordCoverage(profile, job, assets)
        val seniorityFit = AxisEvaluators.seniorityFit(profile, job)
        val structuralCompleteness = AxisEvaluators.structuralCompleteness(profile, job)
        val constraintCompliance = AxisEvaluators.constraintCompliance(profile, job)

        val axisScores = listOf(
            AxisScore("SkillMatch", skillMatch),
            AxisScore("ExperienceAlignment", experienceAlignment),
            AxisScore("RoleRelevance", roleRelevance),
            AxisScore("KeywordCoverage", keywordCoverage),
            AxisScore("SeniorityFit", seniorityFit),
            AxisScore("StructuralCompleteness", structuralCompleteness),
            AxisScore("ConstraintCompliance", constraintCompliance)
        )

        val totalScore = axisScores.sumOf { axis ->
            axis.score * AxisWeights.get(axis.axisName)
        }

        val profileCompleteness = if (RequiredProfileFields.fields.isEmpty()) {
            1.0
        } else {
            profile.completedFields.size.toDouble() / RequiredProfileFields.fields.size
        }
        val jobCompleteness = if (RequiredJobFields.fields.isEmpty()) {
            1.0
        } else {
            job.completedFields.size.toDouble() / RequiredJobFields.fields.size
        }
        val completenessRatio = (profileCompleteness + jobCompleteness) / 2.0

        val profileMultiplier = if (profile.isDegraded) 0.85 else 1.0
        val jobMultiplier = if (job.isDegraded) 0.85 else 1.0
        val confidenceMultiplier = min(profileMultiplier, jobMultiplier)

        val p2ValidationStatus = if (profile.isAccepted && job.isAccepted) 1.0 else 0.0
        val confidence = completenessRatio * confidenceMultiplier * p2ValidationStatus

        val breakdown = ScoreBreakdown(
            axisScores.map { axis ->
                AxisBreakdown(
                    axisName = axis.axisName,
                    score = axis.score,
                    weight = AxisWeights.get(axis.axisName),
                    contributingFactors = listOf(
                        Factor(
                            field = "score",
                            value = String.format("%.4f", axis.score),
                            contribution = 1.0
                        )
                    )
                )
            }
        )

        val gapAnalysis = GapAnalyzer.analyze(profile, job, assets)
        val hints = HintBuilder.build(gapAnalysis, axisScores)

        return ScoreResult(
            correlationId = trace.correlationId,
            totalScore = totalScore,
            confidence = confidence,
            axisScores = axisScores,
            breakdown = breakdown,
            gapAnalysis = gapAnalysis,
            hints = hints,
            trace = outputTrace
        )
    }
}
