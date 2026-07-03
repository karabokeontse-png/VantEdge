package com.vantedge.pipeline.contract

/**
 * Immutable metadata describing the extraction context.
 */
data class ExtractionMetadata(
    val requestId: String,
    val correlationId: String,
    val modelName: String,
    val extractionStrategy: String,
    val rawLength: Int,
    val normalizedLength: Int
)
