package com.vantedge.pipeline.contract

/**
 * Output of contract validation.
 * Success carries a structurally validated projection.
 * Failure carries deterministic classification and observability data.
 */
sealed class ContractValidationResult {
    abstract val confidence: Float

    data class Success(
        val validatedObject: ValidatedAiPayload,
        override val confidence: Float = 1.0f
    ) : ContractValidationResult()

    data class Failure(
        val reason: ContractFailureReason,
        val details: String,
        val correlationId: String,
        val rawSnapshot: String,
        override val confidence: Float = 0.0f
    ) : ContractValidationResult()
}
