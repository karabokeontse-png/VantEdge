package com.vantedge.pipeline.contract

import com.fasterxml.jackson.databind.JsonNode

/**
 * Structural projection of the original parsed object.
 * Same shape as input JSON, but guaranteed to satisfy schema constraints
 * for the specified job type.
 *
 * Immutability contract: [node] is always a deep copy of the original tree.
 * Downstream consumers must never receive the original mutable reference.
 */
data class ValidatedAiPayload private constructor(
    val node: JsonNode,
    val jobType: JobType
) {
    companion object {
        fun create(node: JsonNode, jobType: JobType): ValidatedAiPayload {
            return ValidatedAiPayload(node.deepCopy(), jobType)
        }
    }
}
