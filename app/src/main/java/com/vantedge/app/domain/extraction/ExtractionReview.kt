package com.vantedge.app.domain.extraction

import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.pipeline.validation.ValidationDecision

const val EMERGENCY_EXTRACTION_NOTICE = "Emergency extraction — validation not performed"

/**
 * Presentation-facing view of an extraction that cannot be used downstream
 * until the user has explicitly reviewed it.
 */
data class ExtractionReview(
    val result: JobExtractionResult,
    val warnings: List<String>,
    val fieldReviews: List<FieldReview>,
    val confidenceScore: Float,
    val requiresAcknowledgment: Boolean,
    val isUnvalidatedFallback: Boolean = false,
    val traceId: String? = null
) {
    val blocksDownstream: Boolean
        get() = requiresAcknowledgment
}

fun ExtractionOutcome.toExtractionReviewOrNull(): ExtractionReview? = when (this) {
    is ExtractionOutcome.ReviewRequired -> ExtractionReview(
        result = result,
        warnings = (decision as? ValidationDecision.Degraded)?.warnings ?: emptyList(),
        fieldReviews = extractionReview,
        confidenceScore = result.confidenceScore,
        requiresAcknowledgment = true
    )

    is ExtractionOutcome.EmergencyFallback -> ExtractionReview(
        result = result,
        warnings = listOf(EMERGENCY_EXTRACTION_NOTICE),
        fieldReviews = emptyList(),
        confidenceScore = result.confidenceScore,
        requiresAcknowledgment = true,
        isUnvalidatedFallback = true,
        traceId = traceId
    )

    is ExtractionOutcome.Accepted, is ExtractionOutcome.Rejected -> null
}
