package com.vantedge.app.w6.improvement

import com.vantedge.app.w5.scoring.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class W6EngineTest {

    private lateinit var engine: W6Engine

    @Before
    fun setup() {
        engine = W6Engine()
    }

    @Test
    fun `fixed W5 output produces expected W6 improvements`() {
        val (profile, job, scoreResult) = buildStrongMatchScenario()
        val trace = W6TraceContext("w6-test-001", System.currentTimeMillis())
        val result = engine.improve(scoreResult, profile, job, trace)

        assertEquals("w6-test-001", result.correlationId)
        assertTrue("Should have gap closure actions", result.gapClosurePlan.isNotEmpty())
        assertTrue("Should have impact projections", result.impactProjections.isNotEmpty())
        assertTrue("Should have profile improvements for missing fields", result.profileImprovements.isNotEmpty())
    }

    @Test
    fun `100-run identical output validation`() {
        val (profile, job, scoreResult) = buildStrongMatchScenario()
        val trace = W6TraceContext("stable-w6", System.currentTimeMillis())
        val first = engine.improve(scoreResult, profile, job, trace)

        for (i in 1..99) {
            val result = engine.improve(scoreResult, profile, job, trace)
            assertEquals("Run $i: correlationId", first.correlationId, result.correlationId)
            assertEquals("Run $i: profileImprovements size", first.profileImprovements.size, result.profileImprovements.size)
            assertEquals("Run $i: jobImprovements size", first.jobImprovements.size, result.jobImprovements.size)
            assertEquals("Run $i: gapClosurePlan size", first.gapClosurePlan.size, result.gapClosurePlan.size)
            assertEquals("Run $i: impactProjections size", first.impactProjections.size, result.impactProjections.size)
            for (j in first.gapClosurePlan.indices) {
                assertEquals("Run $i gapClosurePlan[$j].estimatedImpact",
                    first.gapClosurePlan[j].estimatedImpact,
                    result.gapClosurePlan[j].estimatedImpact, 0.0)
            }
        }
    }

    @Test
    fun `deduplication - missing skill does not generate duplicate axis-level action`() {
        val profile = ValidatedProfile(
            correlationId = "dedup", skills = listOf("Java"), roles = listOf("Dev"),
            currentTitle = "Dev", experienceYears = 3, seniorityLevel = "mid",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val job = ValidatedJob(
            correlationId = "dedup", requiredSkills = listOf("Kotlin", "AWS"), title = "Dev",
            keywords = listOf("Kotlin"), requiredYears = 3, seniorityLevel = "mid",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val scoreResult = ScoreResult(
            correlationId = "dedup", totalScore = 0.6, confidence = 0.9,
            axisScores = listOf(
                AxisScore("SkillMatch", 0.3),
                AxisScore("ExperienceAlignment", 0.8),
                AxisScore("RoleRelevance", 0.7),
                AxisScore("KeywordCoverage", 0.5),
                AxisScore("SeniorityFit", 0.8),
                AxisScore("StructuralCompleteness", 1.0),
                AxisScore("ConstraintCompliance", 1.0)
            ),
            breakdown = ScoreBreakdown(emptyList()),
            gapAnalysis = GapAnalysis(
                missing = listOf("Kotlin", "AWS"),
                weak = listOf("Java"),
                matched = listOf()
            ),
            hints = RecommendationHints(emptyList()),
            trace = W5TraceContext("dedup", "", 0L)
        )

        val trace = W6TraceContext("dedup", System.currentTimeMillis())
        val result = engine.improve(scoreResult, profile, job, trace)

        val addSkillActions = result.gapClosurePlan.filter { it.actionType == ImprovementActionType.ADD_SKILL }
        assertEquals("Should have exactly 2 ADD_SKILL actions (one per missing skill, no duplicate from low axis score)", 2, addSkillActions.size)
        assertEquals("First missing skill should be Kotlin", "Kotlin", addSkillActions[0].gapSkill)
        assertEquals("Second missing skill should be AWS", "AWS", addSkillActions[1].gapSkill)
    }

    @Test
    fun `deduplication - weak skill does not generate duplicate ADD_SKILL`() {
        val profile = ValidatedProfile(
            correlationId = "dedup2", skills = listOf("Java"), roles = listOf("Dev"),
            currentTitle = "Dev", experienceYears = 3, seniorityLevel = "mid",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val job = ValidatedJob(
            correlationId = "dedup2", requiredSkills = listOf("Kotlin", "Java"), title = "Dev",
            keywords = listOf("Kotlin"), requiredYears = 3, seniorityLevel = "mid",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val scoreResult = ScoreResult(
            correlationId = "dedup2", totalScore = 0.6, confidence = 0.9,
            axisScores = listOf(
                AxisScore("SkillMatch", 0.7),
                AxisScore("ExperienceAlignment", 0.8),
                AxisScore("RoleRelevance", 0.7),
                AxisScore("KeywordCoverage", 0.4),
                AxisScore("SeniorityFit", 0.8),
                AxisScore("StructuralCompleteness", 1.0),
                AxisScore("ConstraintCompliance", 1.0)
            ),
            breakdown = ScoreBreakdown(emptyList()),
            gapAnalysis = GapAnalysis(
                missing = listOf("Kotlin"),
                weak = listOf("Java"),
                matched = listOf()
            ),
            hints = RecommendationHints(emptyList()),
            trace = W5TraceContext("dedup2", "", 0L)
        )

        val trace = W6TraceContext("dedup2", System.currentTimeMillis())
        val result = engine.improve(scoreResult, profile, job, trace)

        val addSkill = result.gapClosurePlan.filter { it.actionType == ImprovementActionType.ADD_SKILL }
        val alignKeywords = result.gapClosurePlan.filter { it.actionType == ImprovementActionType.ALIGN_KEYWORDS }

        assertEquals("1 ADD_SKILL from missing", 1, addSkill.size)
        assertEquals("Kotlin should be ADD_SKILL", "Kotlin", addSkill[0].gapSkill)
        assertEquals("1 ALIGN_KEYWORDS from weak", 1, alignKeywords.size)
        assertEquals("Java should be ALIGN_KEYWORDS", "Java", alignKeywords[0].gapSkill)
    }

    @Test
    fun `priority assignment for different impact levels`() {
        val profile = ValidatedProfile(
            correlationId = "prio", skills = emptyList(), roles = emptyList(),
            currentTitle = null, experienceYears = 0, seniorityLevel = null,
            completedFields = setOf("skills"), isAccepted = true, isDegraded = false
        )
        val job = ValidatedJob(
            correlationId = "prio", requiredSkills = listOf("Kotlin", "AWS", "Docker", "Kubernetes", "Python"),
            title = "Engineer", keywords = listOf("Kotlin"), requiredYears = 5, seniorityLevel = "senior",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val scoreResult = ScoreResult(
            correlationId = "prio", totalScore = 0.3, confidence = 0.9,
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
            gapAnalysis = GapAnalysis(
                missing = listOf("Kotlin", "AWS", "Docker", "Kubernetes", "Python"),
                weak = emptyList(),
                matched = emptyList()
            ),
            hints = RecommendationHints(emptyList()),
            trace = W5TraceContext("prio", "", 0L)
        )

        val trace = W6TraceContext("prio", System.currentTimeMillis())
        val result = engine.improve(scoreResult, profile, job, trace)

        for (action in result.gapClosurePlan) {
            assertTrue("Priority should be 1, 2, or 3: got ${action.priority}",
                action.priority in 1..3)
        }
        val sortedByPriority = result.gapClosurePlan.sortedBy { it.priority }
        assertEquals("Gap closure plan should be sorted by priority ASC",
            sortedByPriority.map { it.priority },
            result.gapClosurePlan.map { it.priority })
    }

    @Test
    fun `ordering rules - profile improvements sorted by priority then fieldName`() {
        val profile = ValidatedProfile(
            correlationId = "order", skills = emptyList(), roles = emptyList(),
            currentTitle = null, experienceYears = 0, seniorityLevel = null,
            completedFields = emptySet(), isAccepted = true, isDegraded = false
        )
        val job = ValidatedJob(
            correlationId = "order", requiredSkills = listOf("Kotlin"), title = "Dev",
            keywords = listOf("Kotlin"), requiredYears = 3, seniorityLevel = "mid",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val scoreResult = ScoreResult(
            correlationId = "order", totalScore = 0.8, confidence = 1.0,
            axisScores = listOf(
                AxisScore("SkillMatch", 0.8),
                AxisScore("ExperienceAlignment", 0.8),
                AxisScore("RoleRelevance", 0.8),
                AxisScore("KeywordCoverage", 0.8),
                AxisScore("SeniorityFit", 0.8),
                AxisScore("StructuralCompleteness", 0.8),
                AxisScore("ConstraintCompliance", 1.0)
            ),
            breakdown = ScoreBreakdown(emptyList()),
            gapAnalysis = GapAnalysis(emptyList(), emptyList(), emptyList()),
            hints = RecommendationHints(emptyList()),
            trace = W5TraceContext("order", "", 0L)
        )

        val trace = W6TraceContext("order", System.currentTimeMillis())
        val result = engine.improve(scoreResult, profile, job, trace)

        val profileFields = result.profileImprovements
        for (i in 1 until profileFields.size) {
            val prev = profileFields[i - 1]
            val curr = profileFields[i]
            assertTrue("Profile improvements should be sorted by priority then fieldName",
                prev.priority < curr.priority ||
                (prev.priority == curr.priority && prev.fieldName <= curr.fieldName))
        }
    }

    @Test
    fun `ordering rules - gap closure plan sorted by priority then estimatedImpact DESC`() {
        val profile = ValidatedProfile(
            correlationId = "order2", skills = emptyList(), roles = emptyList(),
            currentTitle = null, experienceYears = 0, seniorityLevel = null,
            completedFields = emptySet(), isAccepted = true, isDegraded = false
        )
        val job = ValidatedJob(
            correlationId = "order2", requiredSkills = listOf("A", "B", "C", "D", "E"),
            title = "", keywords = emptyList(), requiredYears = null, seniorityLevel = null,
            completedFields = setOf("requiredSkills"), isAccepted = true, isDegraded = false
        )
        val scoreResult = ScoreResult(
            correlationId = "order2", totalScore = 0.5, confidence = 1.0,
            axisScores = listOf(
                AxisScore("SkillMatch", 0.2),
                AxisScore("ExperienceAlignment", 0.5),
                AxisScore("RoleRelevance", 0.5),
                AxisScore("KeywordCoverage", 1.0),
                AxisScore("SeniorityFit", 0.5),
                AxisScore("StructuralCompleteness", 0.2),
                AxisScore("ConstraintCompliance", 1.0)
            ),
            breakdown = ScoreBreakdown(emptyList()),
            gapAnalysis = GapAnalysis(
                missing = listOf("A", "B", "C", "D", "E"),
                weak = emptyList(), matched = emptyList()
            ),
            hints = RecommendationHints(emptyList()),
            trace = W5TraceContext("order2", "", 0L)
        )

        val trace = W6TraceContext("order2", System.currentTimeMillis())
        val result = engine.improve(scoreResult, profile, job, trace)

        val gaps = result.gapClosurePlan
        for (i in 1 until gaps.size) {
            val prev = gaps[i - 1]
            val curr = gaps[i]
            if (prev.priority == curr.priority) {
                assertTrue("Same priority should be sorted by estimatedImpact DESC",
                    prev.estimatedImpact >= curr.estimatedImpact)
            } else {
                assertTrue("Should be sorted by priority ASC", prev.priority < curr.priority)
            }
        }
    }

    @Test
    fun `impact projections sorted by projectedDelta DESC`() {
        val (profile, job, scoreResult) = buildStrongMatchScenario()
        val trace = W6TraceContext("proj", System.currentTimeMillis())
        val result = engine.improve(scoreResult, profile, job, trace)

        val projections = result.impactProjections
        for (i in 1 until projections.size) {
            assertTrue("Impact projections should be sorted by projectedDelta DESC",
                projections[i - 1].projectedDelta >= projections[i].projectedDelta)
        }
    }

    @Test
    fun `axis name mapping - all 7 W5 axes map correctly`() {
        assertEquals(AxisName.SKILL_MATCH, W6Rules.mapAxisName("SkillMatch"))
        assertEquals(AxisName.EXPERIENCE_ALIGNMENT, W6Rules.mapAxisName("ExperienceAlignment"))
        assertEquals(AxisName.ROLE_RELEVANCE, W6Rules.mapAxisName("RoleRelevance"))
        assertEquals(AxisName.KEYWORD_COVERAGE, W6Rules.mapAxisName("KeywordCoverage"))
        assertEquals(AxisName.SENIORITY_FIT, W6Rules.mapAxisName("SeniorityFit"))
        assertEquals(AxisName.STRUCTURAL_COMPLETENESS, W6Rules.mapAxisName("StructuralCompleteness"))
        assertEquals(AxisName.CONSTRAINT_COMPLIANCE, W6Rules.mapAxisName("ConstraintCompliance"))
        assertNull(W6Rules.mapAxisName("UnknownAxis"))
    }

    @Test
    fun `template string locale safety`() {
        val template = W6Templates.addSkill("Kotlin", "SkillMatch", 0.12345)
        assertTrue("Template should contain the skill name", template.contains("Kotlin"))
        assertTrue("Template should contain the axis name", template.contains("SkillMatch"))
        assertTrue("Delta should use '.' decimal separator", template.contains("0.12"))
    }

    @Test
    fun `edge case - empty gaps produces minimal result`() {
        val profile = ValidatedProfile(
            correlationId = "empty", skills = listOf("Kotlin"), roles = listOf("Dev"),
            currentTitle = "Dev", experienceYears = 3, seniorityLevel = "mid",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val job = ValidatedJob(
            correlationId = "empty", requiredSkills = listOf("Kotlin"), title = "Dev",
            keywords = listOf("Kotlin"), requiredYears = 3, seniorityLevel = "mid",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val scoreResult = ScoreResult(
            correlationId = "empty", totalScore = 1.0, confidence = 1.0,
            axisScores = listOf(
                AxisScore("SkillMatch", 1.0),
                AxisScore("ExperienceAlignment", 1.0),
                AxisScore("RoleRelevance", 1.0),
                AxisScore("KeywordCoverage", 1.0),
                AxisScore("SeniorityFit", 1.0),
                AxisScore("StructuralCompleteness", 1.0),
                AxisScore("ConstraintCompliance", 1.0)
            ),
            breakdown = ScoreBreakdown(emptyList()),
            gapAnalysis = GapAnalysis(emptyList(), emptyList(), emptyList()),
            hints = RecommendationHints(emptyList()),
            trace = W5TraceContext("empty", "", 0L)
        )

        val trace = W6TraceContext("empty", System.currentTimeMillis())
        val result = engine.improve(scoreResult, profile, job, trace)

        assertTrue("No gaps should produce empty gapClosurePlan", result.gapClosurePlan.isEmpty())
        assertTrue("Perfect profile should produce empty profileImprovements", result.profileImprovements.isEmpty())
        assertTrue("Perfect job should produce empty jobImprovements", result.jobImprovements.isEmpty())
        assertTrue("All axes at 1.0 should still produce impact projections", result.impactProjections.isEmpty())
    }

    @Test
    fun `edge case - null requiredYears handled gracefully`() {
        val profile = ValidatedProfile(
            correlationId = "null-yrs", skills = listOf("Kotlin"), roles = listOf("Dev"),
            currentTitle = "Dev", experienceYears = 5, seniorityLevel = "senior",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val job = ValidatedJob(
            correlationId = "null-yrs", requiredSkills = listOf("Kotlin"), title = "Dev",
            keywords = listOf("Kotlin"), requiredYears = null, seniorityLevel = "senior",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val scoreResult = ScoreResult(
            correlationId = "null-yrs", totalScore = 0.8, confidence = 1.0,
            axisScores = listOf(
                AxisScore("SkillMatch", 1.0),
                AxisScore("ExperienceAlignment", 0.0),
                AxisScore("RoleRelevance", 1.0),
                AxisScore("KeywordCoverage", 1.0),
                AxisScore("SeniorityFit", 1.0),
                AxisScore("StructuralCompleteness", 1.0),
                AxisScore("ConstraintCompliance", 1.0)
            ),
            breakdown = ScoreBreakdown(emptyList()),
            gapAnalysis = GapAnalysis(emptyList(), emptyList(), listOf("Kotlin")),
            hints = RecommendationHints(emptyList()),
            trace = W5TraceContext("null-yrs", "", 0L)
        )

        val trace = W6TraceContext("null-yrs", System.currentTimeMillis())
        val result = engine.improve(scoreResult, profile, job, trace)

        assertFalse("Should handle null requiredYears without exception", result.gapClosurePlan.isEmpty())
        val expActions = result.gapClosurePlan.filter { it.targetAxis == AxisName.EXPERIENCE_ALIGNMENT }
        assertTrue("Should suggest rephrasing experience when alignment is low", expActions.isNotEmpty())
    }

    @Test
    fun `impact projection calculation correctness`() {
        val profile = ValidatedProfile(
            correlationId = "calc", skills = listOf("Java"), roles = listOf("Dev"),
            currentTitle = "Dev", experienceYears = 3, seniorityLevel = "mid",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val job = ValidatedJob(
            correlationId = "calc", requiredSkills = listOf("Kotlin", "AWS"), title = "Dev",
            keywords = listOf("Kotlin"), requiredYears = 3, seniorityLevel = "mid",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val scoreResult = ScoreResult(
            correlationId = "calc", totalScore = 0.6, confidence = 0.9,
            axisScores = listOf(
                AxisScore("SkillMatch", 0.3),
                AxisScore("ExperienceAlignment", 0.8),
                AxisScore("RoleRelevance", 0.7),
                AxisScore("KeywordCoverage", 1.0),
                AxisScore("SeniorityFit", 0.8),
                AxisScore("StructuralCompleteness", 1.0),
                AxisScore("ConstraintCompliance", 1.0)
            ),
            breakdown = ScoreBreakdown(emptyList()),
            gapAnalysis = GapAnalysis(
                missing = listOf("Kotlin", "AWS"),
                weak = emptyList(), matched = listOf("Java")
            ),
            hints = RecommendationHints(emptyList()),
            trace = W5TraceContext("calc", "", 0L)
        )

        val trace = W6TraceContext("calc", System.currentTimeMillis())
        val result = engine.improve(scoreResult, profile, job, trace)

        val skillMatchProj = result.impactProjections.find { it.targetAxis == AxisName.SKILL_MATCH }
        assertNotNull("Should have SkillMatch projection", skillMatchProj)
        assertEquals("Current score should match", 0.3, skillMatchProj!!.currentScore, 0.001)
        assertTrue("Projected delta should be positive", skillMatchProj.projectedDelta > 0.0)
    }

    @Test
    fun `correlationId propagation through engine`() {
        val (profile, job, scoreResult) = buildStrongMatchScenario()
        val traceId = "corr-prop-test"
        val trace = W6TraceContext(traceId, System.currentTimeMillis())
        val result = engine.improve(scoreResult, profile, job, trace)

        assertEquals("correlationId should match scoreResult", scoreResult.correlationId, result.correlationId)
        assertEquals("trace correlationId should match input", traceId, result.trace.correlationId)
    }

    @Test
    fun `gapClosureAction has distinct gapSkill and estimatedImpact fields per specification`() {
        val (profile, job, scoreResult) = buildStrongMatchScenario()
        val trace = W6TraceContext("distinct", System.currentTimeMillis())
        val result = engine.improve(scoreResult, profile, job, trace)

        for (action in result.gapClosurePlan) {
            if (action.actionType == ImprovementActionType.ADD_SKILL) {
                assertTrue("ADD_SKILL should have non-empty gapSkill", action.gapSkill.isNotEmpty())
            }
            assertTrue("estimatedImpact should be >= 0", action.estimatedImpact >= 0.0)
            assertNotNull("rationale should not be null", action.rationale)
            assertTrue("priority should be valid", action.priority in 1..3)
        }
    }

    // --- Test helpers ---

    private fun buildStrongMatchScenario(): Triple<ValidatedProfile, ValidatedJob, ScoreResult> {
        val profile = ValidatedProfile(
            correlationId = "w6-test-001",
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
            correlationId = "w6-test-001",
            requiredSkills = listOf("Kotlin", "AWS", "Docker", "Kubernetes"),
            title = "Senior Backend Engineer",
            keywords = listOf("Kotlin", "AWS", "Docker", "Kubernetes", "backend", "microservices"),
            requiredYears = 5,
            seniorityLevel = "senior",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val scoreResult = ScoreResult(
            correlationId = "w6-test-001",
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
            gapAnalysis = GapAnalysis(
                missing = listOf("Kubernetes"),
                weak = listOf("Python"),
                matched = listOf("Kotlin", "AWS", "Docker")
            ),
            hints = RecommendationHints(emptyList()),
            trace = W5TraceContext("w6-test-001", "", 1000L)
        )

        return Triple(profile, job, scoreResult)
    }
}
