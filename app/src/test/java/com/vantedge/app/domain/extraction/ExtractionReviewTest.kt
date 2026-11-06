package com.vantedge.app.domain.extraction

import com.vantedge.app.data.model.ConfidenceBreakdown
import com.vantedge.app.data.model.Gate0JobReason
import com.vantedge.app.data.model.Gate0JobResult
import com.vantedge.app.data.model.JobExtractionMetrics
import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.app.data.model.JobSourceType
import com.vantedge.app.data.model.extraction.ExtractionMethod
import com.vantedge.app.data.model.extraction.FieldConfidence
import com.vantedge.app.data.viewmodel.ExtractionReviewController
import com.vantedge.pipeline.validation.ValidationDecision
import com.vantedge.pipeline.validation.ValidationTrace
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class ExtractionReviewTest {

    private fun trace() = ValidationTrace(pipelineStage = "P2", correlationId = "corr-review")

    private fun extraction(): JobExtractionResult = JobExtractionResult(
        extractionId = "ext-review",
        extractedAt = 1L,
        jobTitle = "Senior Engineer",
        company = "Acme",
        description = "Build and operate platform services.",
        requiredSkills = listOf("Kotlin", "SQL"),
        preferredSkills = listOf("AWS"),
        confidenceScore = 0.62f,
        confidenceBreakdown = ConfidenceBreakdown(
            baseScore = 0.5f,
            titleContribution = 0.05f,
            companyContribution = 0.05f,
            qualificationContribution = 0.02f,
            penalties = emptyList(),
            finalScore = 0.62f
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
            durationMs = 1L,
            sourceType = JobSourceType.USER_INPUT,
            rawTextLength = 40,
            gate0DurationMs = 0,
            gate1DurationMs = 0,
            gate2DurationMs = 0,
            gate3DurationMs = 0,
            gate4DurationMs = 0,
            qualificationPassed = true,
            narrativeDensity = 0.2f
        ),
        educationRequired = "Degree"
    )

    private fun fieldReview(name: String = "jobTitle"): FieldReview = FieldReview(
        fieldName = name,
        confidence = FieldConfidence(
            value = null,
            llmConfidence = 0.4f,
            validationConfidence = 1f,
            consistencyConfidence = 1f,
            overallConfidence = 0.4f,
            source = ExtractionMethod.LLM,
            validationFailures = emptyList()
        ),
        reviewReason = "Below review threshold"
    )

    @Test
    fun `review required preserves fields warnings and confidence and blocks downstream`() {
        val result = extraction()
        val review = ExtractionOutcome.ReviewRequired(
            result = result,
            decision = ValidationDecision.Degraded(trace(), listOf("P2 warning")),
            extractionReview = listOf(fieldReview())
        ).toExtractionReviewOrNull()

        assertNotNull(review)
        review!!
        assertSame(result, review.result)
        assertEquals(listOf("P2 warning"), review.warnings)
        assertEquals(listOf("jobTitle"), review.fieldReviews.map { it.fieldName })
        assertEquals(0.62f, review.confidenceScore, 0f)
        assertTrue(review.requiresAcknowledgment)
        assertTrue(review.blocksDownstream)
        assertFalse(review.isUnvalidatedFallback)
    }

    @Test
    fun `review required with no field reviews still blocks and keeps warnings visible`() {
        val review = ExtractionOutcome.ReviewRequired(
            result = extraction(),
            decision = ValidationDecision.Degraded(trace(), listOf("P2 warning only")),
            extractionReview = emptyList()
        ).toExtractionReviewOrNull()

        assertNotNull(review)
        review!!
        assertTrue(review.fieldReviews.isEmpty())
        assertEquals(listOf("P2 warning only"), review.warnings)
        assertTrue(review.blocksDownstream)
    }

    @Test
    fun `emergency fallback is unvalidated and gated`() {
        val result = extraction()
        val review = ExtractionOutcome.EmergencyFallback(result, "trace-9").toExtractionReviewOrNull()

        assertNotNull(review)
        review!!
        assertSame(result, review.result)
        assertTrue(review.isUnvalidatedFallback)
        assertTrue(review.requiresAcknowledgment)
        assertEquals(listOf(EMERGENCY_EXTRACTION_NOTICE), review.warnings)
        assertEquals("trace-9", review.traceId)
    }

    @Test
    fun `accepted and rejected outcomes do not present a review`() {
        assertNull(
            ExtractionOutcome.Accepted(extraction(), ValidationDecision.Accept(trace()))
                .toExtractionReviewOrNull()
        )
        assertNull(ExtractionOutcome.Rejected("not a job", "trace-10").toExtractionReviewOrNull())
    }

    @Test
    fun `controller blocks until acknowledging and clears after explicit acknowledgement`() {
        val controller = ExtractionReviewController()
        assertTrue(controller.canProceed)
        assertNull(controller.current)

        controller.present(
            ExtractionOutcome.ReviewRequired(
                result = extraction(),
                decision = ValidationDecision.Degraded(trace(), listOf("warning")),
                extractionReview = listOf(fieldReview("company"))
            )
        )
        assertFalse(controller.canProceed)
        assertEquals(listOf("company"), controller.current?.fieldReviews?.map { it.fieldName })

        val corrected = extraction().copy(jobTitle = "Corrected title", requiredSkills = listOf("Kotlin"))
        val returned = controller.acknowledge(corrected)

        assertSame(corrected, returned)
        assertTrue(controller.canProceed)
        assertNull(controller.current)
    }

    @Test
    fun `controller can clear a pending review without treating it as acknowledged data`() {
        val controller = ExtractionReviewController()
        controller.present(ExtractionOutcome.EmergencyFallback(extraction(), "trace-11"))
        assertFalse(controller.canProceed)

        controller.clear()

        assertTrue(controller.canProceed)
        assertNull(controller.current)
    }
}
