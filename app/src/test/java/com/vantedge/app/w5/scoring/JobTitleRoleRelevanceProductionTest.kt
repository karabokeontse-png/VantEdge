package com.vantedge.app.w5.scoring

import com.vantedge.app.data.domain.CompatibilityRecordMapper
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.model.WorkExperience
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class JobTitleRoleRelevanceProductionTest {

    private val taxonomy = setOf("Kotlin", "Java", "AWS")
    private val stopWords = setOf("the", "and", "a", "to", "of", "for")

    private fun assets() = ValidationAssets(
        stopWords = stopWords,
        keywordDictionary = emptyMap()
    )

    private fun adapt(
        jobDescription: String,
        jobTitle: String,
        skills: List<String> = listOf("Kotlin")
    ): ValidatedJob = JobRequirementAdapter.adapt(
        jobDescription = jobDescription,
        jobTitle = jobTitle,
        requiredSkills = skills,
        skillTaxonomy = taxonomy,
        stopWords = stopWords,
        correlationId = "a1-test"
    ).validatedJob

    private fun userProfile() = UserProfile(
        name = "Test User",
        summary = "",
        skills = listOf("Kotlin", "Java"),
        workHistory = listOf(
            WorkExperience(
                role = "Backend Engineer",
                company = "Acme",
                startDate = "2018",
                endDate = "2021",
                description = "Built backend services"
            )
        )
    )

    private fun normalizedProfile() = NormalizedProfile(
        skills = listOf("Kotlin", "Java"),
        certifications = emptyList(),
        workHistory = listOf("Backend Engineer at Acme (2018-2021): Built backend services"),
        education = emptyList(),
        languages = emptyList(),
        summary = "",
        audit = SanitizationAudit(emptyList())
    )

    private fun validatedProfile(correlationId: String = "a1-test"): ValidatedProfile =
        DirectW5InputAdapter.adaptProfile(
            profile = userProfile(),
            normalizedProfile = normalizedProfile(),
            correlationId = correlationId,
            currentYear = 2021
        )

    @Test
    fun `adapter returns exact supplied vacancy title`() {
        val job = adapt(
            jobDescription = "We need a platform engineer.",
            jobTitle = "Staff Platform Engineer"
        )
        assertEquals("Staff Platform Engineer", job.title)
    }

    @Test
    fun `job seniority derives from requiredYears alone even when title keyword conflicts`() {
        val job = adapt(
            jobDescription = "We require 10 years experience.",
            jobTitle = "Junior Engineer"
        )
        assertEquals(10, job.requiredYears)
        assertEquals("senior", job.seniorityLevel)
    }

    @Test
    fun `missing required years leaves job seniority unknown`() {
        val job = adapt(
            jobDescription = "We need a platform engineer.",
            jobTitle = "Senior Engineer"
        )
        assertNull(job.requiredYears)
        assertNull(job.seniorityLevel)
    }

    @Test
    fun `production analysis path computes positive role relevance from supplied title`() {
        val vp = validatedProfile()
        val vj = adapt(
            jobDescription = "Backend Engineer required.",
            jobTitle = "Backend Engineer"
        )
        val result = W5ScoreEngine(assets()).evaluate(vp, vj, W5TraceContext("a1-test", "hash", 0L))
        val roleRelevance = result.axisScores.first { it.axisName == "RoleRelevance" }.score
        assertEquals(1.0, roleRelevance, 1e-9)
        assertTrue("RoleRelevance must be demonstrably positive", roleRelevance > 0.0)
    }

    @Test
    fun `structural completeness reflects populated title field`() {
        val vp = validatedProfile()
        val vj = adapt(
            jobDescription = "Backend Engineer required.",
            jobTitle = "Backend Engineer"
        )
        val expected = (1.0 + 3.0 / 5.0) / 2.0
        assertEquals(expected, AxisEvaluators.structuralCompleteness(vp, vj), 1e-9)
    }

    @Test
    fun `seniority fit is invariant to conflicting title for identical required years`() {
        val vp = validatedProfile()
        val junior = adapt(jobDescription = "Requires 10 years experience.", jobTitle = "Junior Engineer")
        val senior = adapt(jobDescription = "Requires 10 years experience.", jobTitle = "Senior Engineer")
        assertEquals("senior", junior.seniorityLevel)
        assertEquals("senior", senior.seniorityLevel)
        assertEquals(
            AxisEvaluators.seniorityFit(vp, junior),
            AxisEvaluators.seniorityFit(vp, senior),
            0.0
        )
    }

    @Test
    fun `total score aggregation is deterministic and follows weights`() {
        val vp = validatedProfile()
        val vj = adapt(
            jobDescription = "Backend Engineer required. 5 years experience.",
            jobTitle = "Backend Engineer"
        )
        val engine = W5ScoreEngine(assets())
        val r1 = engine.evaluate(vp, vj, W5TraceContext("a1-test", "hash", 0L))
        val r2 = engine.evaluate(vp, vj, W5TraceContext("a1-test", "hash", 0L))
        assertEquals(r1.totalScore, r2.totalScore, 0.0)
        val weighted = r1.axisScores.sumOf { it.score * AxisWeights.get(it.axisName) }
        assertEquals(weighted, r1.totalScore, 1e-12)
        assertTrue(r1.totalScore > 0.0)
    }

    @Test
    fun `production engine forwards job title so role relevance raises the score`() = runBlocking {
        val engine = DeterministicCompatibilityEngine(
            w5ScoreEngine = W5ScoreEngine(assets()),
            compatibilityRecordMapper = CompatibilityRecordMapper(),
            skillTaxonomy = taxonomy,
            stopWords = stopWords
        )

        val withTitle = engine.analyze(
            profile = userProfile(),
            normalizedProfile = normalizedProfile(),
            jobTitle = "Backend Engineer",
            company = "Acme",
            jobDescription = "Backend Engineer required.",
            correlationId = "a1-with-title"
        )
        val withoutTitle = engine.analyze(
            profile = userProfile(),
            normalizedProfile = normalizedProfile(),
            jobTitle = "",
            company = "Acme",
            jobDescription = "Backend Engineer required.",
            correlationId = "a1-without-title"
        )

        assertTrue(
            "Supplying the vacancy title must raise the score via RoleRelevance",
            withTitle.record.score > withoutTitle.record.score
        )
    }
}
