package com.vantedge.app.data.viewmodel

import com.vantedge.app.data.model.ConfidenceBreakdown
import com.vantedge.app.data.model.Gate0JobReason
import com.vantedge.app.data.model.Gate0JobResult
import com.vantedge.app.data.model.JobExtractionMetrics
import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.app.data.model.JobSourceType
import com.vantedge.app.domain.extraction.ExtractionOutcome
import com.vantedge.pipeline.validation.ValidationDecision
import com.vantedge.pipeline.validation.ValidationTrace
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class ExtractionOutcomeMapperTest {

    private fun trace() = ValidationTrace(pipelineStage = "P2", correlationId = "test-correlation")

    private fun result() = JobExtractionResult(
        extractionId = "ext-1",
        extractedAt = 0L,
        jobTitle = "Engineer",
        company = "Acme Corp",
        description = "Responsibilities include building software systems and shipping features.",
        requiredSkills = listOf("Kotlin"),
        preferredSkills = emptyList(),
        confidenceScore = 0.9f,
        confidenceBreakdown = ConfidenceBreakdown(
            baseScore = 0.5f,
            titleContribution = 0.2f,
            companyContribution = 0.1f,
            qualificationContribution = 0.1f,
            penalties = emptyList(),
            finalScore = 0.9f
        ),
        gate0Result = Gate0JobResult(
            score = 3,
            threshold = 2,
            accepted = true,
            reason = Gate0JobReason.ACCEPTED,
            detectedSignals = emptyList(),
            appliedPenalties = emptyList(),
            rejectionCauses = emptyList(),
            narrativeDensity = 0.2f
        ),
        metrics = JobExtractionMetrics(
            durationMs = 10L,
            sourceType = JobSourceType.USER_INPUT,
            rawTextLength = 80,
            gate0DurationMs = 0,
            gate1DurationMs = 0,
            gate2DurationMs = 0,
            gate3DurationMs = 0,
            gate4DurationMs = 0,
            qualificationPassed = true,
            narrativeDensity = 0.2f
        )
    )

    @Test
    fun `accepted outcome maps to Success with same result instance`() {
        val extraction = result()
        val state = mapOutcomeToState(ExtractionOutcome.Accepted(extraction, ValidationDecision.Accept(trace())))
        assertTrue(state is JobExtractionState.Success)
        assertSame(extraction, (state as JobExtractionState.Success).result)
    }

    @Test
    fun `degraded outcome maps to SuccessWithWarning carrying warnings`() {
        val warnings = listOf("Low confidence title", "Missing closing date")
        val extraction = result()
        val state = mapOutcomeToState(
            ExtractionOutcome.ReviewRequired(extraction, ValidationDecision.Degraded(trace(), warnings))
        )
        assertTrue(state is JobExtractionState.SuccessWithWarning)
        val sw = state as JobExtractionState.SuccessWithWarning
        assertSame(extraction, sw.result)
        assertEquals(warnings, sw.warnings)
    }

    @Test
    fun `emergency fallback maps to SuccessWithWarning with fixed notice`() {
        val extraction = result()
        val state = mapOutcomeToState(ExtractionOutcome.EmergencyFallback(extraction, "corr-42"))
        assertTrue(state is JobExtractionState.SuccessWithWarning)
        val sw = state as JobExtractionState.SuccessWithWarning
        assertSame(extraction, sw.result)
        assertEquals(listOf("Emergency extraction — validation not performed"), sw.warnings)
    }

    @Test
    fun `rejected outcome maps to Failure carrying reason`() {
        val state = mapOutcomeToState(ExtractionOutcome.Rejected("Not a job posting", "corr-43"))
        assertTrue(state is JobExtractionState.Failure)
        assertEquals("Not a job posting", (state as JobExtractionState.Failure).message)
    }
}
