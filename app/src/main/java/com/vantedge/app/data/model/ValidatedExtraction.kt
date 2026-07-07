package com.vantedge.app.data.model

import com.vantedge.pipeline.validation.ValidationDecision

data class ValidatedExtraction(
    val extraction: StructuredProfileExtraction,
    val decision: ValidationDecision,
    val correlationId: String,
    val diagnostics: ValidationDiagnostics? = null
)

data class ValidationDiagnostics(
    val rejectionReason: String?,
    val failedRules: List<String>,
    val warnings: List<String>
)
