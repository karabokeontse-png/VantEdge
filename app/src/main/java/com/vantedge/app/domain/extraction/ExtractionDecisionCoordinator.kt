package com.vantedge.app.domain.extraction

import com.vantedge.app.data.model.extraction.ExtractionMethod
import com.vantedge.app.data.model.extraction.FieldConfidence
import com.vantedge.app.domain.PipelineTrace

object ExtractionDecisionCoordinator {
    private const val ACCEPT_THRESHOLD = 0.90f
    private const val ACCEPT_WARNING_THRESHOLD = 0.70f
    private const val USER_REVIEW_THRESHOLD = 0.50f

    fun decide(
        fieldConfidenceMap: Map<String, FieldConfidence>,
        source: ExtractionMethod = ExtractionMethod.LLM,
        correlationId: String? = null
    ): ExtractionDecision {
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("ExtractionDecisionCoordinator", mapOf(
            "fields" to fieldConfidenceMap.size,
            "method" to source,
            "correlationId" to correlationId
        ))

        if (fieldConfidenceMap.isEmpty()) {
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.exit("ExtractionDecisionCoordinator", durationMs, mapOf(
                "decision" to "Failure",
                "reason" to "UNRECOVERABLE_PARSE_ERROR",
                "method" to source,
                "correlationId" to correlationId
            ))
            return ExtractionDecision.Failure(FailureReason.UNRECOVERABLE_PARSE_ERROR)
        }

        val sortedKeys = fieldConfidenceMap.keys.sorted()
        val lowConfidenceFields = mutableMapOf<String, FieldConfidence>()
        for (key in sortedKeys) {
            val conf = fieldConfidenceMap[key]!!
            val overall = conf.overallConfidence
            if (overall == null || overall < ACCEPT_THRESHOLD) {
                lowConfidenceFields[key] = conf
            }
        }

        if (lowConfidenceFields.size == fieldConfidenceMap.size &&
            lowConfidenceFields.values.all { it.overallConfidence == null }
        ) {
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.exit("ExtractionDecisionCoordinator", durationMs, mapOf(
                "decision" to "Failure",
                "reason" to "UNRECOVERABLE_PARSE_ERROR",
                "detail" to "NO_MEASURED_CONFIDENCE",
                "method" to source,
                "correlationId" to correlationId
            ))
            return ExtractionDecision.Failure(FailureReason.UNRECOVERABLE_PARSE_ERROR)
        }

        if (lowConfidenceFields.isEmpty()) {
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.exit("ExtractionDecisionCoordinator", durationMs, mapOf(
                "decision" to "Accept",
                "method" to source,
                "correlationId" to correlationId
            ))
            return ExtractionDecision.Accept
        }

        val criticalFields = listOf("jobTitle", "company", "description")
        val criticalLow = lowConfidenceFields.filter { it.key in criticalFields }

        val criticalBelowReview = criticalLow.filter { (_, conf) ->
            val overall = conf.overallConfidence
            overall == null || overall < USER_REVIEW_THRESHOLD
        }

        if (criticalBelowReview.isNotEmpty()) {
            val reviews = criticalBelowReview.map { (field, conf) ->
                FieldReview(
                    fieldName = field,
                    confidence = conf,
                    reviewReason = "Overall confidence ${conf.overallConfidence?.let { "%.2f".format(it) } ?: "unknown"} below review threshold $USER_REVIEW_THRESHOLD"
                )
            }
            val durationMs = System.currentTimeMillis() - startMs
            val reviewFields = reviews.joinToString(", ") { "${it.fieldName}=${it.confidence.overallConfidence?.let { v -> "%.2f".format(v) } ?: "unknown"}" }
            PipelineTrace.exit("ExtractionDecisionCoordinator", durationMs, mapOf(
                "decision" to "UserReview",
                "reason" to "criticalFieldsBelowThreshold",
                "fields" to "[$reviewFields]",
                "method" to source,
                "correlationId" to correlationId
            ))
            return ExtractionDecision.UserReview(reviews)
        }

        val acceptWithWarning = lowConfidenceFields.all { (_, conf) ->
            val overall = conf.overallConfidence
            overall != null && overall >= ACCEPT_WARNING_THRESHOLD
        }
        if (acceptWithWarning) {
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.exit("ExtractionDecisionCoordinator", durationMs, mapOf(
                "decision" to "AcceptWithWarning",
                "lowFields" to lowConfidenceFields.size,
                "method" to source,
                "correlationId" to correlationId
            ))
            return ExtractionDecision.Accept
        }

        val reviews = lowConfidenceFields.map { (field, conf) ->
            FieldReview(
                fieldName = field,
                confidence = conf,
                reviewReason = "Confidence ${conf.overallConfidence?.let { "%.2f".format(it) } ?: "unknown"} below warning threshold $ACCEPT_WARNING_THRESHOLD"
            )
        }
        val durationMs = System.currentTimeMillis() - startMs
        val reviewFields = reviews.joinToString(", ") { "${it.fieldName}=${it.confidence.overallConfidence?.let { v -> "%.2f".format(v) } ?: "unknown"}" }
        PipelineTrace.exit("ExtractionDecisionCoordinator", durationMs, mapOf(
            "decision" to "UserReview",
            "reason" to "fieldsBelowWarningThreshold",
            "fields" to "[$reviewFields]",
            "method" to source,
            "correlationId" to correlationId
        ))
        return ExtractionDecision.UserReview(reviews)
    }
}
