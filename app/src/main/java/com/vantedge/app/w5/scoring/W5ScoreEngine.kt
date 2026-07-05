package com.vantedge.app.w5.scoring

import java.util.Locale
import kotlin.math.min

class W5ScoreEngine(private val assets: ValidationAssets) {
    fun evaluate(profile: ValidatedProfile, job: ValidatedJob, trace: W5TraceContext): ScoreResult {
        val outputTrace = trace

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

        val profileCompleteness = profile.completedFields.intersect(RequiredProfileFields.fields).size.toDouble() / RequiredProfileFields.fields.size
        val jobCompleteness = job.completedFields.intersect(RequiredJobFields.fields).size.toDouble() / RequiredJobFields.fields.size
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
                            field = "rawScore",
                            value = String.format(Locale.US, "%.4f", axis.score),
                            contribution = axis.score
                        ),
                        Factor(
                            field = "weightedContribution",
                            value = String.format(Locale.US, "%.4f", axis.score * AxisWeights.get(axis.axisName)),
                            contribution = axis.score * AxisWeights.get(axis.axisName)
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
