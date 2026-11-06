package com.vantedge.app.domain.extraction

import com.vantedge.app.data.model.JobSourceType
import com.vantedge.app.data.model.extraction.ExtractionMethod
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Production-builder regression tests for JobExtractionOrchestrator.buildJobExtractionResult().
 *
 * Invokes the real builder (made `internal` for testing). The AiGateway is null because the
 * builder reads no constructor dependency; the LLM-path call site forwards llmResult fields into
 * this builder. These tests prove the builder preserves the structured fields verbatim. They do
 * not prove live LLM/network integration; the call-site forwarding is verified by source inspection.
 */
class JobExtractionOrchestratorFieldPreservationTest {

    private fun build(
        requiredSkills: List<String> = emptyList(),
        preferredSkills: List<String> = emptyList(),
        educationRequired: String? = null
    ) = JobExtractionOrchestrator(aiGateway = null).buildJobExtractionResult(
        title = "Senior Engineer",
        company = "Acme Corp",
        description = "We are looking for a Senior Engineer.",
        requiredSkills = requiredSkills,
        preferredSkills = preferredSkills,
        educationRequired = educationRequired,
        overallConfidence = 0.85f,
        sourceType = JobSourceType.PDF,
        startMs = System.currentTimeMillis(),
        extractionMethod = ExtractionMethod.LLM
    )

    @Test
    fun `required skills list is preserved exactly including order and values`() {
        val skills = listOf("Kotlin", "Distributed Systems", "PostgreSQL")
        assertEquals(skills, build(requiredSkills = skills).requiredSkills)
    }

    @Test
    fun `preferred skills list is preserved exactly including order and values`() {
        val skills = listOf("Kubernetes", "Terraform", "GraphQL")
        assertEquals(skills, build(preferredSkills = skills).preferredSkills)
    }

    @Test
    fun `educationRequired non-null value is preserved exactly`() {
        val education = "Bachelor's degree in Computer Science"
        assertEquals(education, build(educationRequired = education).educationRequired)
    }

    @Test
    fun `empty skills and null education remain empty and null`() {
        val result = build()
        assertTrue(result.requiredSkills.isEmpty())
        assertTrue(result.preferredSkills.isEmpty())
        assertNull(result.educationRequired)
    }

    @Test
    fun `distinct required and preferred lists are not swapped or merged`() {
        val required = listOf("Kotlin", "AWS")
        val preferred = listOf("Docker", "Kubernetes")
        val result = build(requiredSkills = required, preferredSkills = preferred)
        assertEquals(required, result.requiredSkills)
        assertEquals(preferred, result.preferredSkills)
    }

    @Test
    fun `existing unrelated result fields remain populated`() {
        val result = build(requiredSkills = listOf("Kotlin"))
        assertEquals("Senior Engineer", result.jobTitle)
        assertEquals("Acme Corp", result.company)
        assertEquals("We are looking for a Senior Engineer.", result.description)
        assertEquals(0.85f, result.confidenceScore, 0.0f)
        assertTrue(result.extractionId.isNotBlank())
        assertEquals(JobSourceType.PDF, result.metrics.sourceType)
    }
}
