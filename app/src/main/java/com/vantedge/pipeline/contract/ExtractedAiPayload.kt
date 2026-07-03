package com.vantedge.pipeline.contract

import com.fasterxml.jackson.databind.JsonNode

/**
 * Input to the Contract Enforcement Layer.
 * Contains raw JSON, parsed Jackson tree, and extraction metadata.
 */
data class ExtractedAiPayload(
    val rawJson: String,
    val parsedObject: JsonNode,
    val metadata: ExtractionMetadata
)
