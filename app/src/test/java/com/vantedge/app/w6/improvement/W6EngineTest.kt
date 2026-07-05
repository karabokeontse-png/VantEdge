package com.vantedge.app.w6.improvement

import com.vantedge.app.w5.scoring.AxisScore
import com.vantedge.app.w5.scoring.GapAnalysis
import com.vantedge.app.w5.scoring.RecommendationHints
import com.vantedge.app.w5.scoring.ScoreBreakdown
import com.vantedge.app.w5.scoring.ScoreResult
import com.vantedge.app.w5.scoring.ValidatedJob
import com.vantedge.app.w5.scoring.ValidatedProfile
import com.vantedge.app.w5.scoring.W5TraceContext
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class W6EngineTest {

    @Test
    fun `100-run determinism - same input produces identical result`() {
        val input = buildDefaultInput()
        val first = W6Engine.evaluate(input)

        for (i in 1..99) {
            val result = W6Engine.evaluate(input)
            assertEquals("Run $i: correlationId mismatch", first.correlationId, result.correlationId)
            assertEquals("Run $i: profileImprovements size", first.profileImprovements.size, result.profileImprovements.size)
            assertEquals("Run $i: jobImprovements size", first.jobImprovements.size, result.jobImprovements.size)
            assertEquals("Run $i: gapClosurePlan size", first.gapClosurePlan.size, result.gapClosurePlan.size)
            assertEquals("Run $i: impactProjections size", first.impactProjections.size, result.impactProjections.size)

            for (j in first.gapClosurePlan.indices) {
                val expected = first.gapClosurePlan[j]
                val actual = result.gapClosurePlan[j]
                assertEquals("Run $i gapClosurePlan[$j].gapSkill", expected.gapSkill, actual.gapSkill)
                assertEquals("Run $i gapClosurePlan[$j].actionType", expected.actionType, actual.actionType)
                assertEquals("Run $i gapClosurePlan[$j].estimatedImpact", expected.estimatedImpact, actual.estimatedImpact, 0.0)
                assertEquals("Run $i gapClosurePlan[$j].priority", expected.priority, actual.priority)
                assertEquals("Run $i gapClosurePlan[$j].rationale", expected.rationale, actual.rationale)
            }

            for (j in first.profileImprovements.indices) {
                assertEquals("Run $i profileImprovements[$j].fieldName", first.profileImprovements[j].fieldName, result.profileImprovements[j].fieldName)
                assertEquals("Run $i profileImprovements[$j].actionType", first.profileImprovements[j].actionType, result.profileImprovements[j].actionType)
                assertEquals("Run $i profileImprovements[$j].priority", first.profileImprovements[j].priority, result.profileImprovements[j].priority)
                assertEquals("Run $i profileImprovements[$j].rationale", first.profileImprovements[j].rationale, result.profileImprovements[j].rationale)
            }

            for (j in first.jobImprovements.indices) {
                assertEquals("Run $i jobImprovements[$j].fieldName", first.jobImprovements[j].fieldName, result.jobImprovements[j].fieldName)
                assertEquals("Run $i jobImprovements[$j].actionType", first.jobImprovements[j].actionType, result.jobImprovements[j].actionType)
                assertEquals("Run $i jobImprovements[$j].priority", first.jobImprovements[j].priority, result.jobImprovements[j].priority)
                assertEquals("Run $i jobImprovements[$j].rationale", first.jobImprovements[j].rationale, result.jobImprovements[j].rationale)
            }

            for (j in first.impactProjections.indices) {
                assertEquals("Run $i impactProjections[$j].targetAxis", first.impactProjections[j].targetAxis, result.impactProjections[j].targetAxis)
                assertEquals("Run $i impactProjections[$j].currentScore", first.impactProjections[j].currentScore, result.impactProjections[j].currentScore, 0.0)
                assertEquals("Run $i impactProjections[$j].projectedDelta", first.impactProjections[j].projectedDelta, result.impactProjections[j].projectedDelta, 0.0)
                assertEquals("Run $i impactProjections[$j].actionType", first.impactProjections[j].actionType, result.impactProjections[j].actionType)
            }
        }
    }

    @Test
    fun `deduplication - missing skill with low SkillMatch score produces exactly one GapClosureAction`() {
        val profile = ValidatedProfile(
            correlationId = "dedup",
            skills = listOf("Java"),
            roles = listOf("Developer"),
            currentTitle = "Developer",
            experienceYears = 3,
            seniorityLevel = "mid",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )
        val job = ValidatedJob(
            correlationId = "dedup",
            requiredSkills = listOf("Kotlin", "AWS"),
            title = "Developer",
            keywords = listOf("Kotlin"),
            requiredYears = 3,
            seniorityLevel = "mid",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )
        val gaps = GapAnalysis(
            missing = listOf("Kotlin", "AWS"),
            weak = emptyList(),
            matched = listOf("Java")
        )
        val score = ScoreResult(
            correlationId = "dedup",
            totalScore = 0.5,
            confidence = 0.9,
            axisScores = listOf(
                AxisScore("SkillMatch", 0.3),
                AxisScore("ExperienceAlignment", 0.8),
                AxisScore("RoleRelevance", 0.7),
                AxisScore("KeywordCoverage", 1.0),
                AxisScore("SeniorityFit", 1.0),
                AxisScore("StructuralCompleteness", 1.0),
                AxisScore("ConstraintCompliance", 1.0)
            ),
            breakdown = ScoreBreakdown(emptyList()),
            gapAnalysis = gaps,
            hints = RecommendationHints(emptyList()),
            trace = W5TraceContext("dedup", "", 0L)
        )

        val input = W6Input(
            profile = profile,
            job = job,
            score = score,
            gaps = gaps,
            hints = RecommendationHints(emptyList()),
            correlationId = "dedup"
        )

        val result = W6Engine.evaluate(input)

        val addSkillActions = result.gapClosurePlan.filter { it.actionType == ImprovementActionType.ADD_SKILL }
        assertEquals("Should have exactly 2 ADD_SKILL actions (one per missing skill)", 2, addSkillActions.size)
        assertEquals("First missing skill", "Kotlin", addSkillActions[0].gapSkill)
        assertEquals("Second missing skill", "AWS", addSkillActions[1].gapSkill)

        val totalAddSkillForGaps = result.gapClosurePlan.count { it.actionType == ImprovementActionType.ADD_SKILL && it.gapSkill in gaps.missing }
        assertEquals("No duplicate ADD_SKILL beyond gaps.missing", gaps.missing.size, totalAddSkillForGaps)
    }

    @Test
    fun `priority ordering - gapClosurePlan sorted by priority ASC then estimatedImpact DESC`() {
        val profile = ValidatedProfile(
            correlationId = "prio",
            skills = emptyList(),
            roles = emptyList(),
            currentTitle = null,
            experienceYears = 0,
            seniorityLevel = null,
            completedFields = setOf("skills"),
            isAccepted = true,
            isDegraded = false
        )
        val job = ValidatedJob(
            correlationId = "prio",
            requiredSkills = listOf("Kotlin", "AWS", "Docker", "Kubernetes", "Python"),
            title = "Engineer",
            keywords = listOf("Kotlin"),
            requiredYears = 5,
            seniorityLevel = "senior",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )
        val gaps = GapAnalysis(
            missing = listOf("Kotlin", "AWS", "Docker", "Kubernetes", "Python"),
            weak = emptyList(),
            matched = emptyList()
        )
        val score = ScoreResult(
            correlationId = "prio",
            totalScore = 0.3,
            confidence = 0.9,
            axisScores = listOf(
                AxisScore("SkillMatch", 0.0),
                AxisScore("ExperienceAlignment", 0.8),
                AxisScore("RoleRelevance", 0.7),
                AxisScore("KeywordCoverage", 0.5),
                AxisScore("SeniorityFit", 0.8),
                AxisScore("StructuralCompleteness", 0.2),
                AxisScore("ConstraintCompliance", 1.0)
            ),
            breakdown = ScoreBreakdown(emptyList()),
            gapAnalysis = gaps,
            hints = RecommendationHints(emptyList()),
            trace = W5TraceContext("prio", "", 0L)
        )

        val input = W6Input(
            profile = profile,
            job = job,
            score = score,
            gaps = gaps,
            hints = RecommendationHints(emptyList()),
            correlationId = "prio"
        )

        val result = W6Engine.evaluate(input)

        for (i in 1 until result.gapClosurePlan.size) {
            val prev = result.gapClosurePlan[i - 1]
            val curr = result.gapClosurePlan[i]
            if (prev.priority == curr.priority) {
                assertTrue(
                    "Same priority ($prev.priority) should sort by estimatedImpact DESC",
                    prev.estimatedImpact >= curr.estimatedImpact
                )
            } else {
                assertTrue(
                    "Priority should be ASC: ${prev.priority} <= ${curr.priority}",
                    prev.priority < curr.priority
                )
            }
        }
    }

    @Test
    fun `impact projection ordering - sorted by projectedDelta DESC`() {
        val input = buildDefaultInput()
        val result = W6Engine.evaluate(input)

        val projections = result.impactProjections
        for (i in 1 until projections.size) {
            assertTrue(
                "Index $i: projectedDelta should be DESC",
                projections[i - 1].projectedDelta >= projections[i].projectedDelta
            )
        }
    }

    @Test
    fun `correlationId pass-through - output correlationId equals input correlationId`() {
        val input = buildDefaultInput()
        val result = W6Engine.evaluate(input)
        assertEquals("correlationId should pass through", input.correlationId, result.correlationId)
    }

    @Test
    fun `template determinism - rationale strings are template-generated`() {
        val input = buildDefaultInput()
        val result = W6Engine.evaluate(input)

        for (action in result.gapClosurePlan) {
            when (action.actionType) {
                ImprovementActionType.ADD_SKILL -> {
                    assertTrue("ADD_SKILL rationale should mention 'Add '", action.rationale.startsWith("Add '"))
                    assertTrue("ADD_SKILL rationale should mention 'score by'", action.rationale.contains("score by"))
                }
                ImprovementActionType.ALIGN_KEYWORDS -> {
                    assertTrue("ALIGN_KEYWORDS rationale should mention 'Emphasize '", action.rationale.startsWith("Emphasize '"))
                    assertTrue("ALIGN_KEYWORDS rationale should mention 'keyword coverage'", action.rationale.contains("keyword coverage"))
                }
                else -> {}
            }
        }

        for (improvement in result.profileImprovements) {
            when (improvement.actionType) {
                ImprovementActionType.REPHRASE_EXPERIENCE -> {
                    assertTrue("REPHRASE_EXPERIENCE rationale should mention 'Highlight '", improvement.rationale.startsWith("Highlight "))
                }
                ImprovementActionType.ADJUST_SENIORITY_TRAJECTORY -> {
                    assertTrue("ADJUST_SENIORITY_TRAJECTORY rationale should mention 'Target '", improvement.rationale.startsWith("Target "))
                }
                ImprovementActionType.COMPLETE_PROFILE_FIELDS -> {
                    assertTrue("COMPLETE_PROFILE_FIELDS rationale should mention 'Complete '", improvement.rationale.startsWith("Complete '"))
                }
                else -> {}
            }
        }

        for (improvement in result.jobImprovements) {
            when (improvement.actionType) {
                ImprovementActionType.ALIGN_KEYWORDS -> {
                    assertTrue("Job ALIGN_KEYWORDS rationale should mention 'Emphasize '", improvement.rationale.startsWith("Emphasize '"))
                }
                else -> {}
            }
        }
    }

    @Test
    fun `axis name mapping - all 7 W5 axis names map correctly to AxisName`() {
        assertEquals(AxisName.SKILL_MATCH, AxisNameMapping.fromW5AxisName("SkillMatch"))
        assertEquals(AxisName.EXPERIENCE_ALIGNMENT, AxisNameMapping.fromW5AxisName("ExperienceAlignment"))
        assertEquals(AxisName.ROLE_RELEVANCE, AxisNameMapping.fromW5AxisName("RoleRelevance"))
        assertEquals(AxisName.KEYWORD_COVERAGE, AxisNameMapping.fromW5AxisName("KeywordCoverage"))
        assertEquals(AxisName.SENIORITY_FIT, AxisNameMapping.fromW5AxisName("SeniorityFit"))
        assertEquals(AxisName.STRUCTURAL_COMPLETENESS, AxisNameMapping.fromW5AxisName("StructuralCompleteness"))
        assertEquals(AxisName.CONSTRAINT_COMPLIANCE, AxisNameMapping.fromW5AxisName("ConstraintCompliance"))
    }

    @Test
    fun `no mutation - W6Input objects are unchanged after evaluate call`() {
        val profile = ValidatedProfile(
            correlationId = "mutate-test",
            skills = listOf("Java"),
            roles = listOf("Developer"),
            currentTitle = "Developer",
            experienceYears = 3,
            seniorityLevel = "mid",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )
        val job = ValidatedJob(
            correlationId = "mutate-test",
            requiredSkills = listOf("Kotlin", "AWS"),
            title = "Developer",
            keywords = listOf("Kotlin"),
            requiredYears = 3,
            seniorityLevel = "mid",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )
        val gaps = GapAnalysis(
            missing = listOf("Kotlin", "AWS"),
            weak = emptyList(),
            matched = listOf("Java")
        )
        val hints = RecommendationHints(listOf("Acquire Kotlin"))
        val score = ScoreResult(
            correlationId = "mutate-test",
            totalScore = 0.5,
            confidence = 0.9,
            axisScores = listOf(
                AxisScore("SkillMatch", 0.3),
                AxisScore("ExperienceAlignment", 0.8),
                AxisScore("RoleRelevance", 0.7),
                AxisScore("KeywordCoverage", 1.0),
                AxisScore("SeniorityFit", 1.0),
                AxisScore("StructuralCompleteness", 1.0),
                AxisScore("ConstraintCompliance", 1.0)
            ),
            breakdown = ScoreBreakdown(emptyList()),
            gapAnalysis = gaps,
            hints = hints,
            trace = W5TraceContext("mutate-test", "", 0L)
        )

        val input = W6Input(
            profile = profile,
            job = job,
            score = score,
            gaps = gaps,
            hints = hints,
            correlationId = "mutate-test"
        )

        val profileBefore = profile.copy()
        val jobBefore = job.copy()
        val gapsBefore = gaps.copy()
        val hintsBefore = hints.copy()
        val scoreBefore = score.copy()

        W6Engine.evaluate(input)

        assertEquals("profile unchanged", profileBefore, input.profile)
        assertEquals("job unchanged", jobBefore, input.job)
        assertEquals("gaps unchanged", gapsBefore, input.gaps)
        assertEquals("hints unchanged", hintsBefore, input.hints)
        assertEquals("score unchanged", scoreBefore, input.score)
        assertEquals("correlationId unchanged", "mutate-test", input.correlationId)
    }

    private fun buildDefaultInput(): W6Input {
        val profile = ValidatedProfile(
            correlationId = "default-test",
            skills = listOf("Kotlin", "Java", "AWS", "Docker"),
            roles = listOf("Senior Backend Engineer"),
            currentTitle = "Senior Backend Engineer",
            experienceYears = 8,
            seniorityLevel = "senior",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "default-test",
            requiredSkills = listOf("Kotlin", "AWS", "Docker", "Kubernetes"),
            title = "Senior Backend Engineer",
            keywords = listOf("Kotlin", "AWS", "Docker", "Kubernetes", "backend", "microservices"),
            requiredYears = 5,
            seniorityLevel = "senior",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val gaps = GapAnalysis(
            missing = listOf("Kubernetes"),
            weak = listOf("Python"),
            matched = listOf("Kotlin", "AWS", "Docker")
        )

        val hints = RecommendationHints(emptyList())

        val score = ScoreResult(
            correlationId = "default-test",
            totalScore = 0.78,
            confidence = 0.95,
            axisScores = listOf(
                AxisScore("SkillMatch", 0.75),
                AxisScore("ExperienceAlignment", 0.85),
                AxisScore("RoleRelevance", 0.90),
                AxisScore("KeywordCoverage", 0.70),
                AxisScore("SeniorityFit", 1.0),
                AxisScore("StructuralCompleteness", 1.0),
                AxisScore("ConstraintCompliance", 1.0)
            ),
            breakdown = ScoreBreakdown(emptyList()),
            gapAnalysis = gaps,
            hints = hints,
            trace = W5TraceContext("default-test", "", 1000L)
        )

        return W6Input(
            profile = profile,
            job = job,
            score = score,
            gaps = gaps,
            hints = hints,
            correlationId = "default-test"
        )
    }
}
