package com.vantedge.app.domain.extraction

import com.vantedge.app.data.model.extraction.ExtractionMethod
import com.vantedge.app.data.model.extraction.FieldConfidence
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExtractionDecisionCoordinatorUnknownTest {

    private fun field(overall: Float?, llm: Float? = overall) = FieldConfidence(
        value = null,
        llmConfidence = llm,
        validationConfidence = 1.0f,
        consistencyConfidence = 1.0f,
        overallConfidence = overall,
        source = ExtractionMethod.LLM,
        validationFailures = emptyList()
    )

    @Test
    fun `all confidence values null fail unrecoverably`() {
        val decision = ExtractionDecisionCoordinator.decide(
            mapOf(
                "jobTitle" to field(null),
                "company" to field(null),
                "description" to field(null)
            )
        )

        assertTrue(decision is ExtractionDecision.Failure)
        assertEquals(
            FailureReason.UNRECOVERABLE_PARSE_ERROR,
            (decision as ExtractionDecision.Failure).reason
        )
    }

    @Test
    fun `critical field with null confidence requires review and preserves unknown reason`() {
        val decision = ExtractionDecisionCoordinator.decide(
            mapOf(
                "jobTitle" to field(null),
                "company" to field(0.95f),
                "description" to field(0.95f)
            )
        )

        assertTrue(decision is ExtractionDecision.UserReview)
        val review = (decision as ExtractionDecision.UserReview).fields.single()
        assertEquals("jobTitle", review.fieldName)
        assertTrue(review.reviewReason.contains("unknown"))
    }

    @Test
    fun `noncritical field with null confidence is not accepted as measured evidence`() {
        val decision = ExtractionDecisionCoordinator.decide(
            mapOf(
                "jobTitle" to field(0.95f),
                "company" to field(0.95f),
                "description" to field(0.95f),
                "skills" to field(null)
            )
        )

        assertTrue(decision is ExtractionDecision.UserReview)
        val review = (decision as ExtractionDecision.UserReview).fields.single()
        assertEquals("skills", review.fieldName)
        assertTrue(review.reviewReason.contains("unknown"))
    }

    @Test
    fun `empty confidence map fails unrecoverably`() {
        val decision = ExtractionDecisionCoordinator.decide(emptyMap())

        assertTrue(decision is ExtractionDecision.Failure)
        assertEquals(
            FailureReason.UNRECOVERABLE_PARSE_ERROR,
            (decision as ExtractionDecision.Failure).reason
        )
    }
}
