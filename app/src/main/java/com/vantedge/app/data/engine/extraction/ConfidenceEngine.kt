package com.vantedge.app.data.engine.extraction

import com.vantedge.app.data.model.extraction.ExtractionMethod
import com.vantedge.app.domain.PipelineTrace
import com.vantedge.app.data.model.extraction.FieldConfidence

data class ConfidenceResult(
    val fieldConfidenceMap: Map<String, FieldConfidence>,
    val overallConfidence: Float
)

object ConfidenceEngine {
    fun compute(
        llmConfidenceMap: Map<String, Float>,
        semanticAdjustments: Map<String, Float>,
        schemaFailures: List<String>,
        source: ExtractionMethod,
        correlationId: String? = null
    ): ConfidenceResult {
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("ConfidenceEngine", mapOf(
            "llmFields" to llmConfidenceMap.size,
            "semanticAdjustments" to semanticAdjustments.size,
            "schemaFailures" to schemaFailures.size,
            "method" to source,
            "correlationId" to correlationId
        ))

        val fieldConfidenceMap = mutableMapOf<String, FieldConfidence>()

        val allFields = (llmConfidenceMap.keys + semanticAdjustments.keys + schemaFailures).toSet().sorted()

        for (field in allFields) {
            val llmConf = llmConfidenceMap[field] ?: 0.3f

            val validationConf = if (field in schemaFailures) 0.0f else 1.0f

            val semanticAdj = semanticAdjustments[field] ?: 0f
            val consistencyConf = (1.0f + semanticAdj).coerceIn(0.0f, 1.0f)

            val overall = (llmConf * 0.5f + validationConf * 0.3f + consistencyConf * 0.2f)
                .coerceIn(0.0f, 1.0f)

            fieldConfidenceMap[field] = FieldConfidence(
                value = null,
                llmConfidence = llmConf,
                validationConfidence = validationConf,
                consistencyConfidence = consistencyConf,
                overallConfidence = overall,
                source = source,
                validationFailures = if (field in schemaFailures) listOf("Schema validation failed") else emptyList()
            )
        }

        val overall = if (fieldConfidenceMap.isNotEmpty()) {
            fieldConfidenceMap.values.sumOf { it.overallConfidence.toDouble() }.toFloat() / fieldConfidenceMap.size
        } else 0.0f

        val durationMs = System.currentTimeMillis() - startMs
        val fieldDetails = fieldConfidenceMap.keys.sorted().joinToString(", ") { name ->
            val fc = fieldConfidenceMap[name]!!
            "${name}=llm=${"%.2f".format(fc.llmConfidence)} val=${"%.2f".format(fc.validationConfidence)} con=${"%.2f".format(fc.consistencyConfidence)} overall=${"%.2f".format(fc.overallConfidence)}"
        }
        val schemaDetail = if (schemaFailures.isNotEmpty()) schemaFailures.joinToString(", ") else "none"
        PipelineTrace.exit("ConfidenceEngine", durationMs, mapOf(
            "overall" to "%.2f".format(overall),
            "fields" to fieldConfidenceMap.size,
            "method" to source,
            "schemaFailures" to "[$schemaDetail]",
            "fieldDetails" to "{$fieldDetails}",
            "correlationId" to correlationId
        ))
        return ConfidenceResult(fieldConfidenceMap, overall)
    }
}
