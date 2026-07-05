package com.vantedge.app.w5.scoring

import com.vantedge.app.data.model.*
import com.vantedge.pipeline.validation.ValidationDecision
import com.vantedge.pipeline.validation.ValidationTrace
import kotlinx.collections.immutable.toImmutableList
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class W5ScoreEngineTest {

    private lateinit var assets: ValidationAssets
    private lateinit var engine: W5ScoreEngine

    @Before
    fun setup() {
        assets = ValidationAssets(
            stopWords = setOf("the", "a", "an", "and", "or", "of", "to", "in", "for", "with"),
            keywordDictionary = mapOf(
                "kotlin" to listOf("kotlin", "kotlinlang"),
                "java" to listOf("java", "java8", "java11"),
                "python" to listOf("python", "py"),
                "javascript" to listOf("javascript", "js", "ecmascript"),
                "aws" to listOf("aws", "amazon-web-services", "amazonwebservices")
            )
        )
        engine = W5ScoreEngine(assets)
    }

    @Test
    fun `fixed dataset produces expected scores`() {
        val profile = ValidatedProfile(
            correlationId = "test-001",
            skills = listOf("Kotlin", "Java", "AWS", "Python", "Docker"),
            roles = listOf("Senior Backend Engineer"),
            currentTitle = "Senior Backend Engineer",
            experienceYears = 8,
            seniorityLevel = "senior",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "test-001",
            requiredSkills = listOf("Kotlin", "AWS", "Docker", "Kubernetes"),
            title = "Senior Backend Engineer",
            keywords = listOf("Kotlin", "AWS", "Docker", "Kubernetes", "backend", "microservices"),
            requiredYears = 5,
            seniorityLevel = "senior",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val trace = W5TraceContext("test-001", "", 1000L)
        val result = engine.evaluate(profile, job, trace)

        assertTrue("Total score should be > 0.5 for strong match", result.totalScore > 0.5)
        assertTrue("Confidence should be > 0 for accepted profiles", result.confidence > 0.0)
        assertEquals("test-001", result.correlationId)
        assertEquals(7, result.axisScores.size)
        assertTrue(result.gapAnalysis.missing.isNotEmpty())
    }

    @Test
    fun `100-run identical output validation`() {
        val profile = ValidatedProfile(
            correlationId = "stable-001",
            skills = listOf("Kotlin", "Java"),
            roles = listOf("Developer"),
            currentTitle = "Developer",
            experienceYears = 3,
            seniorityLevel = "mid",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "stable-001",
            requiredSkills = listOf("Kotlin"),
            title = "Developer",
            keywords = listOf("Kotlin", "development"),
            requiredYears = 2,
            seniorityLevel = "mid",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val trace = W5TraceContext("stable-001", "", 1000L)
        val first = engine.evaluate(profile, job, trace)

        for (i in 1..99) {
            val result = engine.evaluate(profile, job, trace)
            assertEquals("Run $i: totalScore should be identical", first.totalScore, result.totalScore, 0.0)
            assertEquals("Run $i: confidence should be identical", first.confidence, result.confidence, 0.0)
            assertEquals("Run $i: axisScores should be identical", first.axisScores, result.axisScores)
            assertEquals("Run $i: gapAnalysis should be identical", first.gapAnalysis, result.gapAnalysis)
        }
    }

    @Test
    fun `correlationId continuity verification`() {
        val profile = ValidatedProfile(
            correlationId = "cont-001",
            skills = listOf("Kotlin"),
            roles = listOf("Developer"),
            currentTitle = "Developer",
            experienceYears = 2,
            seniorityLevel = "mid",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "cont-001",
            requiredSkills = listOf("Kotlin"),
            title = "Developer",
            keywords = listOf("Kotlin"),
            requiredYears = 2,
            seniorityLevel = "mid",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val trace = W5TraceContext("cont-001", "", 1000L)
        val result = engine.evaluate(profile, job, trace)

        assertEquals("correlationId should match input trace", trace.correlationId, result.correlationId)
        assertNotNull("output trace correlationId should not be null", result.trace.correlationId)
        assertEquals("output trace correlationId should match", trace.correlationId, result.trace.correlationId)
    }

    @Test
    fun `division by zero safety - empty requiredYears`() {
        val profile = ValidatedProfile(
            correlationId = "dz-001",
            skills = listOf("Kotlin"),
            roles = listOf("Developer"),
            currentTitle = "Developer",
            experienceYears = 5,
            seniorityLevel = "senior",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "dz-001",
            requiredSkills = listOf("Kotlin"),
            title = "Developer",
            keywords = listOf("Kotlin"),
            requiredYears = null,
            seniorityLevel = "senior",
            completedFields = setOf("requiredSkills", "title", "keywords", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val trace = W5TraceContext("dz-001", "", 1000L)
        val result = engine.evaluate(profile, job, trace)
        assertEquals(0.0, result.axisScores.find { it.axisName == "ExperienceAlignment" }!!.score, 0.0)
    }

    @Test
    fun `division by zero safety - empty keywords`() {
        val profile = ValidatedProfile(
            correlationId = "dz-002",
            skills = listOf("Kotlin"),
            roles = listOf("Developer"),
            currentTitle = "Developer",
            experienceYears = 5,
            seniorityLevel = "senior",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "dz-002",
            requiredSkills = listOf("Kotlin"),
            title = "Developer",
            keywords = emptyList(),
            requiredYears = 3,
            seniorityLevel = "senior",
            completedFields = setOf("requiredSkills", "title", "requiredYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val trace = W5TraceContext("dz-002", "", 1000L)
        val result = engine.evaluate(profile, job, trace)
        assertEquals(0.0, result.axisScores.find { it.axisName == "KeywordCoverage" }!!.score, 0.0)
    }

    @Test
    fun `division by zero safety - empty requiredSkills skillMatch`() {
        val profile = ValidatedProfile(
            correlationId = "dz-003",
            skills = emptyList(),
            roles = listOf("Developer"),
            currentTitle = "Developer",
            experienceYears = 5,
            seniorityLevel = "senior",
            completedFields = setOf("roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "dz-003",
            requiredSkills = emptyList(),
            title = "Developer",
            keywords = listOf("Dev"),
            requiredYears = 3,
            seniorityLevel = "senior",
            completedFields = setOf("title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true,
            isDegraded = false
        )

        val trace = W5TraceContext("dz-003", "", 1000L)
        val result = engine.evaluate(profile, job, trace)
        assertFalse("Should not throw exception", result.totalScore.isNaN())
    }

    @Test
    fun `division by zero safety - RequiredProfileFields empty`() {
        val profile = ValidatedProfile(
            correlationId = "dz-004",
            skills = listOf("Kotlin"),
            roles = listOf("Developer"),
            currentTitle = "Developer",
            experienceYears = 5,
            seniorityLevel = "senior",
            completedFields = emptySet(),
            isAccepted = true,
            isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "dz-004",
            requiredSkills = listOf("Kotlin"),
            title = "Developer",
            keywords = listOf("Kotlin"),
            requiredYears = 3,
            seniorityLevel = "senior",
            completedFields = emptySet(),
            isAccepted = true,
            isDegraded = false
        )

        val trace = W5TraceContext("dz-004", "", 1000L)
        val result = engine.evaluate(profile, job, trace)
        assertFalse("Should not throw when completedFields is empty", result.totalScore.isNaN())
        assertFalse("Confidence should not be NaN", result.confidence.isNaN())
    }

    @Test
    fun `null handling - currentTitle and seniorityLevel are null`() {
        val profile = ValidatedProfile(
            correlationId = "null-001",
            skills = listOf("Kotlin"),
            roles = emptyList(),
            currentTitle = null,
            experienceYears = 0,
            seniorityLevel = null,
            completedFields = setOf("skills"),
            isAccepted = true,
            isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "null-001",
            requiredSkills = listOf("Kotlin"),
            title = "Backend Developer",
            keywords = listOf("Kotlin"),
            requiredYears = null,
            seniorityLevel = null,
            completedFields = setOf("requiredSkills", "title", "keywords"),
            isAccepted = true,
            isDegraded = false
        )

        val trace = W5TraceContext("null-001", "", 1000L)
        val result = engine.evaluate(profile, job, trace)
        assertFalse("Should handle null currentTitle and seniorityLevel", result.totalScore.isNaN())
        assertEquals(0.0, result.axisScores.find { it.axisName == "SeniorityFit" }!!.score, 0.0)
    }

    @Test
    fun `skillMatch evaluator independent test`() {
        val profile = ValidatedProfile(
            correlationId = "axis-sm",
            skills = listOf("Kotlin", "Java", "AWS"),
            roles = emptyList(),
            currentTitle = null,
            experienceYears = 0,
            seniorityLevel = null,
            completedFields = setOf("skills"),
            isAccepted = true,
            isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "axis-sm",
            requiredSkills = listOf("Kotlin", "AWS", "Kubernetes"),
            title = "",
            keywords = emptyList(),
            requiredYears = null,
            seniorityLevel = null,
            completedFields = setOf("requiredSkills"),
            isAccepted = true,
            isDegraded = false
        )

        val score = AxisEvaluators.skillMatch(profile, job, assets)
        assertTrue("SkillMatch should be > 0", score > 0.0)
        assertTrue("SkillMatch should be < 1.0", score < 1.0)
    }

    @Test
    fun `experienceAlignment evaluator independent test`() {
        val underqualified = ValidatedProfile(
            correlationId = "axis-ea-1", skills = listOf("Kotlin"), roles = emptyList(),
            currentTitle = null, experienceYears = 2, seniorityLevel = null,
            completedFields = setOf("skills", "experienceYears"), isAccepted = true, isDegraded = false
        )
        val qualified = ValidatedProfile(
            correlationId = "axis-ea-2", skills = listOf("Kotlin"), roles = emptyList(),
            currentTitle = null, experienceYears = 5, seniorityLevel = null,
            completedFields = setOf("skills", "experienceYears"), isAccepted = true, isDegraded = false
        )
        val overqualified = ValidatedProfile(
            correlationId = "axis-ea-3", skills = listOf("Kotlin"), roles = emptyList(),
            currentTitle = null, experienceYears = 10, seniorityLevel = null,
            completedFields = setOf("skills", "experienceYears"), isAccepted = true, isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "axis-ea", requiredSkills = listOf("Kotlin"), title = "",
            keywords = emptyList(), requiredYears = 5, seniorityLevel = null,
            completedFields = setOf("requiredSkills", "requiredYears"), isAccepted = true, isDegraded = false
        )

        assertEquals(0.4, AxisEvaluators.experienceAlignment(underqualified, job), 0.001)
        assertEquals(1.0, AxisEvaluators.experienceAlignment(qualified, job), 0.001)
        assertEquals(1.0, AxisEvaluators.experienceAlignment(overqualified, job), 0.001)
    }

    @Test
    fun `roleRelevance evaluator independent test`() {
        val profile = ValidatedProfile(
            correlationId = "axis-rr", skills = emptyList(),
            roles = listOf("Backend Engineer", "API Developer"),
            currentTitle = "Backend Engineer", experienceYears = 0, seniorityLevel = null,
            completedFields = setOf("roles", "currentTitle"), isAccepted = true, isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "axis-rr", requiredSkills = emptyList(),
            title = "Backend Engineer", keywords = emptyList(), requiredYears = null,
            seniorityLevel = null, completedFields = setOf("title"), isAccepted = true, isDegraded = false
        )

        val score = AxisEvaluators.roleRelevance(profile, job, assets)
        assertTrue("RoleRelevance should be > 0", score > 0.0)
    }

    @Test
    fun `keywordCoverage evaluator independent test`() {
        val profile = ValidatedProfile(
            correlationId = "axis-kc", skills = listOf("Kotlin", "Java", "Spring"),
            roles = emptyList(), currentTitle = null, experienceYears = 0, seniorityLevel = null,
            completedFields = setOf("skills"), isAccepted = true, isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "axis-kc", requiredSkills = emptyList(),
            title = "", keywords = listOf("Kotlin", "Java", "Kubernetes"),
            requiredYears = null, seniorityLevel = null,
            completedFields = setOf("keywords"), isAccepted = true, isDegraded = false
        )

        val score = AxisEvaluators.keywordCoverage(profile, job, assets)
        assertEquals(2.0 / 3.0, score, 0.001)
    }

    @Test
    fun `seniorityFit evaluator independent test`() {
        val profile = ValidatedProfile(
            correlationId = "axis-sf", skills = emptyList(), roles = emptyList(),
            currentTitle = null, experienceYears = 0, seniorityLevel = "senior",
            completedFields = setOf("seniorityLevel"), isAccepted = true, isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "axis-sf", requiredSkills = emptyList(), title = "",
            keywords = emptyList(), requiredYears = null, seniorityLevel = "senior",
            completedFields = setOf("seniorityLevel"), isAccepted = true, isDegraded = false
        )

        assertEquals(1.0, AxisEvaluators.seniorityFit(profile, job), 0.001)
    }

    @Test
    fun `seniorityFit returns 0 when either level is null`() {
        val profile = ValidatedProfile(
            correlationId = "axis-sf-null", skills = emptyList(), roles = emptyList(),
            currentTitle = null, experienceYears = 0, seniorityLevel = null,
            completedFields = emptySet(), isAccepted = true, isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "axis-sf-null", requiredSkills = emptyList(), title = "",
            keywords = emptyList(), requiredYears = null, seniorityLevel = "senior",
            completedFields = setOf("seniorityLevel"), isAccepted = true, isDegraded = false
        )

        assertEquals(0.0, AxisEvaluators.seniorityFit(profile, job), 0.001)
    }

    @Test
    fun `structuralCompleteness evaluator independent test`() {
        val fullProfile = ValidatedProfile(
            correlationId = "sc-full", skills = listOf("K"), roles = listOf("R"),
            currentTitle = "T", experienceYears = 1, seniorityLevel = "mid",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val partialProfile = ValidatedProfile(
            correlationId = "sc-partial", skills = listOf("K"), roles = emptyList(),
            currentTitle = null, experienceYears = 0, seniorityLevel = null,
            completedFields = setOf("skills"),
            isAccepted = true, isDegraded = false
        )
        val emptyProfile = ValidatedProfile(
            correlationId = "sc-empty", skills = emptyList(), roles = emptyList(),
            currentTitle = null, experienceYears = 0, seniorityLevel = null,
            completedFields = emptySet(),
            isAccepted = true, isDegraded = false
        )

        val fullJob = ValidatedJob(
            correlationId = "sc-full", requiredSkills = listOf("K"), title = "T",
            keywords = listOf("K"), requiredYears = 3, seniorityLevel = "mid",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val emptyJob = ValidatedJob(
            correlationId = "sc-empty", requiredSkills = emptyList(), title = "",
            keywords = emptyList(), requiredYears = null, seniorityLevel = null,
            completedFields = emptySet(),
            isAccepted = true, isDegraded = false
        )

        assertEquals(1.0, AxisEvaluators.structuralCompleteness(fullProfile, fullJob), 0.001)
        assertEquals(0.1, AxisEvaluators.structuralCompleteness(partialProfile, emptyJob), 0.001)
        assertEquals(0.0, AxisEvaluators.structuralCompleteness(emptyProfile, emptyJob), 0.001)
    }

    @Test
    fun `constraintCompliance evaluator independent test`() {
        val accepted = ValidatedProfile(
            correlationId = "cc", skills = emptyList(), roles = emptyList(),
            currentTitle = null, experienceYears = 0, seniorityLevel = null,
            completedFields = emptySet(), isAccepted = true, isDegraded = false
        )
        val rejected = ValidatedProfile(
            correlationId = "cc", skills = emptyList(), roles = emptyList(),
            currentTitle = null, experienceYears = 0, seniorityLevel = null,
            completedFields = emptySet(), isAccepted = false, isDegraded = false
        )
        val acceptedJob = ValidatedJob(
            correlationId = "cc", requiredSkills = emptyList(), title = "",
            keywords = emptyList(), requiredYears = null, seniorityLevel = null,
            completedFields = emptySet(), isAccepted = true, isDegraded = false
        )

        assertEquals(1.0, AxisEvaluators.constraintCompliance(accepted, acceptedJob), 0.001)
        assertEquals(0.0, AxisEvaluators.constraintCompliance(rejected, acceptedJob), 0.001)
    }

    @Test
    fun `gapAnalyzer independent test`() {
        val profile = ValidatedProfile(
            correlationId = "ga", skills = listOf("Kotlin", "Java"),
            roles = emptyList(), currentTitle = null, experienceYears = 0, seniorityLevel = null,
            completedFields = setOf("skills"), isAccepted = true, isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "ga", requiredSkills = listOf("Kotlin", "Kubernetes", "Python"),
            title = "", keywords = emptyList(), requiredYears = null, seniorityLevel = null,
            completedFields = setOf("requiredSkills"), isAccepted = true, isDegraded = false
        )

        val gap = GapAnalyzer.analyze(profile, job, assets)

        assertTrue("Kotlin should be weak", gap.weak.contains("Kotlin"))
        assertTrue("Kubernetes should be missing", gap.missing.contains("Kubernetes"))
        assertTrue("Python should be missing", gap.missing.contains("Python"))
    }

    @Test
    fun `hintBuilder independent test`() {
        val gap = GapAnalysis(
            missing = listOf("Kubernetes"),
            weak = listOf("Docker"),
            matched = listOf("Kotlin")
        )

        val axisScores = listOf(
            AxisScore("SkillMatch", 0.5),
            AxisScore("ExperienceAlignment", 0.3),
            AxisScore("SeniorityFit", 0.2)
        )

        val hints = HintBuilder.build(gap, axisScores)

        assertTrue("Should contain acquire hint", hints.hints.any { it.contains("Acquire") })
        assertTrue("Should contain strengthen hint", hints.hints.any { it.contains("Strengthen") })
        assertTrue("Should contain experience hint", hints.hints.any { it.contains("ExperienceAlignment") })
        assertTrue("Should contain seniority hint", hints.hints.any { it.contains("SeniorityFit") })
    }

    @Test
    fun `degraded profile reduces confidence`() {
        val accepted = ValidatedProfile(
            correlationId = "degraded", skills = listOf("Kotlin"), roles = listOf("Dev"),
            currentTitle = "Dev", experienceYears = 3, seniorityLevel = "mid",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val degraded = accepted.copy(isDegraded = true)

        val job = ValidatedJob(
            correlationId = "degraded", requiredSkills = listOf("Kotlin"), title = "Dev",
            keywords = listOf("Kotlin"), requiredYears = 3, seniorityLevel = "mid",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )

        val trace = W5TraceContext("degraded", "", 1000L)
        val acceptedResult = engine.evaluate(accepted, job, trace)
        val degradedResult = engine.evaluate(degraded, job, trace)

        assertTrue("Degraded profile should have lower confidence", degradedResult.confidence < acceptedResult.confidence)
        assertEquals("Total score should be unaffected by degradation", acceptedResult.totalScore, degradedResult.totalScore, 0.001)
    }

    @Test
    fun `rejected profile results in zero confidence`() {
        val rejectedProfile = ValidatedProfile(
            correlationId = "rejected", skills = listOf("Kotlin"), roles = listOf("Dev"),
            currentTitle = "Dev", experienceYears = 3, seniorityLevel = "mid",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = false, isDegraded = false
        )

        val job = ValidatedJob(
            correlationId = "rejected", requiredSkills = listOf("Kotlin"), title = "Dev",
            keywords = listOf("Kotlin"), requiredYears = 3, seniorityLevel = "mid",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )

        val trace = W5TraceContext("rejected", "", 1000L)
        val result = engine.evaluate(rejectedProfile, job, trace)

        assertEquals("Rejected profile should have 0 confidence", 0.0, result.confidence, 0.0)
    }

    @Test
    fun `adapter produces correct ValidatedProfile from StructuredProfileExtraction`() {
        val traceId = "adapter-test-001"
        val profile = buildTestProfile()
        val profileDecision = ValidationDecision.Accept(ValidationTrace("P2", traceId))

        val job = buildTestJob()
        val jobRequiredSkills = listOf("Kotlin", "AWS")
        val jobPreferredSkills = listOf("Docker")
        val jobDecision = ValidationDecision.Accept(ValidationTrace("P2", traceId))

        val result = W5Adapter.adapt(
            profile = profile,
            profileDecision = profileDecision,
            job = job,
            jobRequiredSkills = jobRequiredSkills,
            jobPreferredSkills = jobPreferredSkills,
            jobRequiredYears = 5,
            jobDecision = jobDecision,
            correlationId = traceId,
            asOfYear = 2026
        )

        val vp = result.profile
        assertEquals(traceId, vp.correlationId)
        assertTrue(vp.skills.containsAll(listOf("Kotlin", "Java", "AWS")))
        assertTrue(vp.roles.contains("Senior Backend Engineer"))
        assertEquals("Senior Backend Engineer", vp.currentTitle)
        assertTrue(vp.experienceYears > 0)
        assertNotNull(vp.seniorityLevel)
        assertTrue(vp.isAccepted)
        assertFalse(vp.isDegraded)
        assertEquals(5, vp.completedFields.size)
    }

    @Test
    fun `adapter produces correct ValidatedJob from JobExtractionResult`() {
        val traceId = "adapter-test-002"
        val profile = buildTestProfile()
        val profileDecision = ValidationDecision.Accept(ValidationTrace("P2", traceId))

        val job = buildTestJob()
        val jobRequiredSkills = listOf("Kotlin", "AWS")
        val jobPreferredSkills = listOf("Docker")
        val jobDecision = ValidationDecision.Accept(ValidationTrace("P2", traceId))

        val result = W5Adapter.adapt(
            profile = profile,
            profileDecision = profileDecision,
            job = job,
            jobRequiredSkills = jobRequiredSkills,
            jobPreferredSkills = jobPreferredSkills,
            jobRequiredYears = 5,
            jobDecision = jobDecision,
            correlationId = traceId,
            asOfYear = 2026
        )

        val vj = result.job
        assertEquals(traceId, vj.correlationId)
        assertTrue(vj.requiredSkills.containsAll(listOf("Kotlin", "AWS")))
        assertEquals("Senior Backend Engineer", vj.title)
        assertTrue(vj.keywords.isNotEmpty())
        assertNotNull(vj.requiredYears)
        assertNotNull(vj.seniorityLevel)
        assertTrue(vj.isAccepted)
        assertFalse(vj.isDegraded)
    }

    @Test
    fun `adapter handles degraded decisions`() {
        val traceId = "adapter-test-003"
        val profile = buildTestProfile()
        val profileDecision = ValidationDecision.Degraded(
            ValidationTrace("P2", traceId),
            listOf("Missing education")
        )

        val job = buildTestJob()
        val jobDecision = ValidationDecision.Degraded(
            ValidationTrace("P2", traceId),
            listOf("Low description quality")
        )

        val result = W5Adapter.adapt(
            profile = profile,
            profileDecision = profileDecision,
            job = job,
            jobRequiredSkills = listOf("Kotlin"),
            jobPreferredSkills = emptyList(),
            jobRequiredYears = null,
            jobDecision = jobDecision,
            correlationId = traceId,
            asOfYear = 2026
        )

        assertTrue(result.profile.isDegraded)
        assertTrue(result.job.isDegraded)
        assertTrue(result.profile.isAccepted)
        assertTrue(result.job.isAccepted)
    }

    @Test
    fun `adapter handles reject decisions`() {
        val traceId = "adapter-test-004"
        val profile = buildTestProfile()
        val profileDecision = ValidationDecision.Reject(
            "Profile does not meet minimum criteria",
            ValidationTrace("P2", traceId)
        )

        val job = buildTestJob()
        val jobDecision = ValidationDecision.Reject(
            "Job posting is not valid",
            ValidationTrace("P2", traceId)
        )

        val result = W5Adapter.adapt(
            profile = profile,
            profileDecision = profileDecision,
            job = job,
            jobRequiredSkills = listOf("Kotlin"),
            jobPreferredSkills = emptyList(),
            jobRequiredYears = null,
            jobDecision = jobDecision,
            correlationId = traceId,
            asOfYear = 2026
        )

        assertFalse(result.profile.isAccepted)
        assertFalse(result.job.isAccepted)
        assertFalse(result.profile.isDegraded)
        assertFalse(result.job.isDegraded)
    }

    @Test
    fun `tokenize utility produces correct tokens`() {
        val tokens = tokenize("Kotlin & Java (Spring Boot)", assets.stopWords)
        assertTrue(tokens.contains("kotlin"))
        assertTrue(tokens.contains("java"))
        assertTrue(tokens.contains("spring"))
        assertTrue(tokens.contains("boot"))
        assertFalse(tokens.contains("the"))
    }

    @Test
    fun `jaccard similarity produces correct values`() {
        assertEquals(1.0, jaccard(setOf("a", "b"), setOf("a", "b")), 0.001)
        assertEquals(0.0, jaccard(setOf("a", "b"), setOf("c", "d")), 0.001)
        assertEquals(0.333, jaccard(setOf("a", "b"), setOf("b", "c")), 0.001)
        assertEquals(1.0, jaccard(emptySet(), emptySet()), 0.001)
    }

    @Test
    fun `expand utility adds synonyms`() {
        val expanded = expand(setOf("kotlin"), assets.keywordDictionary)
        assertTrue(expanded.contains("kotlin"))
        assertTrue(expanded.contains("kotlinlang"))
    }

    @Test
    fun `SeniorityDeriver derive from title`() {
        assertEquals("senior", SeniorityDeriver.derive("Senior Software Engineer", null))
        assertEquals("junior", SeniorityDeriver.derive("Junior Developer", null))
        assertEquals("lead", SeniorityDeriver.derive("Lead Architect", null))
    }

    @Test
    fun `SeniorityDeriver derive from experience`() {
        assertEquals("junior", SeniorityDeriver.derive(null, 1))
        assertEquals("mid", SeniorityDeriver.derive(null, 3))
        assertEquals("senior", SeniorityDeriver.derive(null, 7))
        assertEquals("lead", SeniorityDeriver.derive(null, 15))
    }

    @Test
    fun `SeniorityDeriver toIndex`() {
        assertEquals(0, SeniorityDeriver.toIndex("junior"))
        assertEquals(1, SeniorityDeriver.toIndex("mid"))
        assertEquals(2, SeniorityDeriver.toIndex("senior"))
        assertEquals(3, SeniorityDeriver.toIndex("lead"))
        assertNull(SeniorityDeriver.toIndex("unknown"))
    }

    @Test
    fun `computeInputHash is deterministic`() {
        val p1 = ValidatedProfile(
            correlationId = "h1", skills = listOf("A"), roles = listOf("B"),
            currentTitle = "C", experienceYears = 1, seniorityLevel = "D",
            completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )
        val j1 = ValidatedJob(
            correlationId = "h1", requiredSkills = listOf("E"), title = "F",
            keywords = listOf("G"), requiredYears = 1, seniorityLevel = "H",
            completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
            isAccepted = true, isDegraded = false
        )

        val hash1 = computeInputHash(p1, j1)
        val hash2 = computeInputHash(p1, j1)

        assertEquals("Hash should be deterministic", hash1, hash2)
    }

    // --- Test helpers ---

    private fun buildTestProfile(): StructuredProfileExtraction {
        return StructuredProfileExtraction(
            personalInfo = ExtractedPersonalInfo(
                name = ExtractedField("John Doe", 1.0f, ExtractionSourceType.DIRECT),
                email = ExtractedField("john@example.com", 1.0f, ExtractionSourceType.DIRECT),
                phone = null,
                location = ExtractedField("San Francisco", 0.8f, ExtractionSourceType.DIRECT),
                linkedIn = null,
                headline = ExtractedField("Senior Backend Engineer", 0.9f, ExtractionSourceType.DIRECT)
            ),
            summary = ExtractedField("Experienced backend engineer", 0.8f, ExtractionSourceType.DIRECT),
            skills = listOf(
                ExtractedField("Kotlin", 0.95f, ExtractionSourceType.DIRECT),
                ExtractedField("Java", 0.9f, ExtractionSourceType.DIRECT),
                ExtractedField("AWS", 0.85f, ExtractionSourceType.DIRECT)
            ).toImmutableList(),
            languages = emptyList<ExtractedField>().toImmutableList(),
            workHistory = listOf(
                ExtractedExperience(
                    jobTitle = ExtractedField("Senior Backend Engineer", 0.95f, ExtractionSourceType.DIRECT),
                    company = ExtractedField("TechCorp", 0.95f, ExtractionSourceType.DIRECT),
                    startDate = ExtractedField("2020-01", 0.9f, ExtractionSourceType.DIRECT),
                    endDate = ExtractedField("2024-12", 0.9f, ExtractionSourceType.DIRECT),
                    description = ExtractedField("Built backend services", 0.8f, ExtractionSourceType.DIRECT),
                    confidence = 0.9f
                ),
                ExtractedExperience(
                    jobTitle = ExtractedField("Backend Developer", 0.95f, ExtractionSourceType.DIRECT),
                    company = ExtractedField("StartupInc", 0.95f, ExtractionSourceType.DIRECT),
                    startDate = ExtractedField("2016-06", 0.9f, ExtractionSourceType.DIRECT),
                    endDate = ExtractedField("2020-01", 0.9f, ExtractionSourceType.DIRECT),
                    description = ExtractedField("Built APIs", 0.8f, ExtractionSourceType.DIRECT),
                    confidence = 0.9f
                )
            ).toImmutableList(),
            education = emptyList<ExtractedEducation>().toImmutableList(),
            certifications = emptyList<ExtractedCertification>().toImmutableList(),
            overallConfidence = 0.9f,
            warnings = emptyList<String>().toImmutableList(),
            metadata = ExtractionMetadata("gpt-4")
        )
    }

    private fun buildTestJob(): JobExtractionResult {
        return JobExtractionResult(
            extractionId = "ext-001",
            extractedAt = System.currentTimeMillis(),
            jobTitle = "Senior Backend Engineer",
            company = "TargetCorp",
            description = "We are looking for a Senior Backend Engineer with 5+ years of experience in Kotlin and AWS. Must have experience with microservices and Docker.",
            confidenceScore = 0.85f,
            confidenceBreakdown = ConfidenceBreakdown(
                baseScore = 0.8f,
                titleContribution = 0.1f,
                companyContribution = 0.05f,
                qualificationContribution = 0.05f,
                penalties = emptyList(),
                finalScore = 0.85f
            ),
            gate0Result = Gate0JobResult(
                score = 85,
                threshold = 50,
                accepted = true,
                reason = Gate0JobReason.ACCEPTED,
                detectedSignals = listOf("tech_role", "experienced"),
                appliedPenalties = emptyList(),
                rejectionCauses = emptyList(),
                narrativeDensity = 0.7f
            ),
            metrics = JobExtractionMetrics(
                durationMs = 1200,
                sourceType = JobSourceType.PDF,
                rawTextLength = 5000,
                gate0DurationMs = 100,
                gate1DurationMs = 200,
                gate2DurationMs = 300,
                gate3DurationMs = 400,
                gate4DurationMs = 200,
                qualificationPassed = true,
                narrativeDensity = 0.7f
            )
        )
    }
}
