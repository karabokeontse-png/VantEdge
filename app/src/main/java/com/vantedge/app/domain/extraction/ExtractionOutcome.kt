package com.vantedge.app.domain.extraction

import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.pipeline.validation.ValidationDecision

sealed class ExtractionOutcome {
    data class Accepted(val result: JobExtractionResult, val decision: ValidationDecision) : ExtractionOutcome()
    data class ReviewRequired(val result: JobExtractionResult, val decision: ValidationDecision) : ExtractionOutcome()
    data class Rejected(val reason: String, val traceId: String) : ExtractionOutcome()
    data class EmergencyFallback(val result: JobExtractionResult, val traceId: String) : ExtractionOutcome()
}
