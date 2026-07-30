package com.vantedge.app.w5.scoring

import com.vantedge.app.domain.PipelineTrace
import java.util.Locale
import kotlin.math.min

class W5ScoreEngine(private val assets: ValidationAssets) {
    fun evaluate(profile: ValidatedProfile, job: ValidatedJob, trace: W5TraceContext): ScoreResult {
        val startMs = System.currentTimeMillis()
        val correlationId = trace.correlationId
        PipelineTrace.entry("W5ScoreEngine", mapOf(
            "correlationId" to correlationId,
            "profileSkills" to profile.skills.size,
            "jobRequiredSkills" to job.requiredSkills.size
        ))

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

        val durationMs = System.currentTimeMillis() - startMs
        PipelineTrace.dataQuality(
            stage = "W5ScoreEngine",
            issue = "AXIS_SCORES",
            details = mapOf(
                "correlationId" to correlationId,
                "totalScore" to totalScore,
                "confidence" to confidence,
                "skillMatch" to skillMatch,
                "experienceAlignment" to experienceAlignment,
                "constraintCompliance" to constraintCompliance,
                "missingCount" to gapAnalysis.missing.size,
                "weakCount" to gapAnalysis.weak.size,
                "matchedCount" to gapAnalysis.matched.size
            ),
            correlationId = correlationId
        )
        PipelineTrace.exit("W5ScoreEngine", durationMs, mapOf(
            "correlationId" to correlationId,
            "totalScore" to totalScore,
            "confidence" to confidence,
            "missingGaps" to gapAnalysis.missing.size
        ))

        return ScoreResult(
            correlationId = trace.correlationId,
            totalScore = totalScore,
            confidence = confidence,
            axisScores = axisScores,
            breakdown = breakdown,
            gapAnalysis = gapAnalysis,
            hints = hints,
            trace = trace
        )
    }
}
