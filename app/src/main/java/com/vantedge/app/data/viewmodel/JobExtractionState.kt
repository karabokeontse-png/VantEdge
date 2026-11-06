package com.vantedge.app.data.viewmodel

import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.app.domain.extraction.FieldReview

sealed class JobExtractionState {
    object Idle : JobExtractionState()
    data class Extracting(val statusText: String) : JobExtractionState()
    data class Success(val result: JobExtractionResult) : JobExtractionState()
    data class SuccessWithWarning(
        val result: JobExtractionResult,
        val warnings: List<String>,
        val extractionReview: List<FieldReview> = emptyList(),
        val requiresAcknowledgment: Boolean = false,
        val isUnvalidatedFallback: Boolean = false,
        val confidenceScore: Float = result.confidenceScore,
        val traceId: String? = null
    ) : JobExtractionState()
    data class Failure(val message: String) : JobExtractionState()
}
