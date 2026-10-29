package com.vantedge.app.w6.improvement

import com.vantedge.app.w5.scoring.AxisScore
import com.vantedge.app.w5.scoring.GapAnalysis
import com.vantedge.app.w5.scoring.RecommendationHints
import com.vantedge.app.w5.scoring.ScoreBreakdown
import com.vantedge.app.w5.scoring.ScoreResult
import com.vantedge.app.w5.scoring.ValidatedJob
import com.vantedge.app.w5.scoring.ValidatedProfile
import com.vantedge.app.w5.scoring.W5TraceContext
import org.junit.Assert.assertTrue
import org.junit.Test

class W6UnknownAxisTest {

    private fun profile() = ValidatedProfile(
        correlationId = "w6",
        skills = listOf("Kotlin"),
        roles = listOf("Developer"),
        currentTitle = "Developer",
        experienceYears = 5,
        seniorityLevel = "senior",
        completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
        isAccepted = true,
        isDegraded = false
    )

    private fun job() = ValidatedJob(
        correlationId = "w6",
        requiredSkills = listOf("Kotlin"),
        title = "Developer",
        keywords = listOf("Kotlin"),
        requiredYears = 5,
        seniorityLevel = "senior",
        completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
        isAccepted = true,
        isDegraded = false
    )

    private fun score(axisScores: List<AxisScore>, gaps: GapAnalysis) = ScoreResult(
        correlationId = "w6",
        totalScore = 0.5,
        confidence = 0.9,
        axisScores = axisScores,
        breakdown = ScoreBreakdown(emptyList()),
        gapAnalysis = gaps,
        hints = RecommendationHints(emptyList()),
        trace = W5TraceContext("w6", "", 0L),
        unknownAxes = axisScores.filterNot { it.isMeasured }.map { it.axisName }
    )

    private fun evaluate(score: ScoreResult, gaps: GapAnalysis) = W6Engine.evaluate(
        W6Input(
            profile = profile(),
            job = job(),
            score = score,
            gaps = gaps,
            hints = RecommendationHints(emptyList()),
            correlationId = "w6"
        )
    )

    @Test
    fun `unmeasured axes do not produce weakness recommendations`() {
        val gaps = GapAnalysis(missing = listOf("Kotlin"), weak = emptyList(), matched = emptyList())
        val result = evaluate(
            score(
                listOf(
                    AxisScore("SkillMatch", 0.0, false),
                    AxisScore("ExperienceAlignment", 0.0, false),
                    AxisScore("RoleRelevance", 0.0, false),
                    AxisScore("KeywordCoverage", 0.0, false),
                    AxisScore("SeniorityFit", 0.0, false),
                    AxisScore("StructuralCompleteness", 1.0, true),
                    AxisScore("ConstraintCompliance", 1.0, true)
                ),
                gaps
            ),
            gaps
        )

        assertTrue(result.gapClosurePlan.isEmpty())
        assertTrue(result.profileImprovements.isEmpty())
        assertTrue(result.jobImprovements.isEmpty())
        assertTrue(result.impactProjections.isEmpty())
    }

    @Test
    fun `measured weaknesses still produce recommendations`() {
        val gaps = GapAnalysis(missing = listOf("Kotlin"), weak = emptyList(), matched = emptyList())
        val result = evaluate(
            score(
                listOf(
                    AxisScore("SkillMatch", 0.3, true),
                    AxisScore("ExperienceAlignment", 0.5, true),
                    AxisScore("RoleRelevance", 0.4, true),
                    AxisScore("KeywordCoverage", 0.6, true),
                    AxisScore("SeniorityFit", 0.5, true),
                    AxisScore("StructuralCompleteness", 0.8, true),
                    AxisScore("ConstraintCompliance", 1.0, true)
                ),
                gaps
            ),
            gaps
        )

        assertTrue(result.gapClosurePlan.isNotEmpty())
        assertTrue(result.profileImprovements.isNotEmpty())
        assertTrue(result.jobImprovements.isNotEmpty())
        assertTrue(result.impactProjections.isNotEmpty())
    }
}
