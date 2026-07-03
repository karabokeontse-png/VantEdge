package com.vantedge.app.domain.extraction

import com.vantedge.app.data.model.extraction.FieldConfidence

sealed interface ExtractionDecision {
    data object Accept : ExtractionDecision
    data class Retry(val reason: RetryReason) : ExtractionDecision
    data class UserReview(val fields: List<FieldReview>) : ExtractionDecision
    data class Failure(val reason: FailureReason) : ExtractionDecision
}

enum class RetryReason {
    TRUNCATED_RESPONSE,
    INVALID_JSON,
    TIMEOUT,
    TRANSPORT_INTERRUPTION
}

data class FieldReview(
    val fieldName: String,
    val confidence: FieldConfidence,
    val reviewReason: String
)

enum class FailureReason {
    ALL_MODELS_EXHAUSTED,
    VALIDATION_BLOCKER,
    UNRECOVERABLE_PARSE_ERROR
}
