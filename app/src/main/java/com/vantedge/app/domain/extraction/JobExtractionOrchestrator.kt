package com.vantedge.app.domain.extraction

import com.vantedge.app.data.engine.extraction.ConfidenceEngine
import com.vantedge.app.data.engine.extraction.DocumentPreprocessor
import com.vantedge.app.data.engine.extraction.DocumentStructureAnalyzer
import com.vantedge.app.data.engine.extraction.LlmJobExtractor
import com.vantedge.app.data.engine.extraction.RuleBasedEmergencyExtractor
import com.vantedge.app.data.engine.extraction.SchemaValidator
import com.vantedge.app.data.engine.extraction.SemanticValidator
import com.vantedge.app.data.model.ConfidenceBreakdown
import com.vantedge.app.data.model.Gate0JobResult
import com.vantedge.app.data.model.Gate0JobReason
import com.vantedge.app.data.model.JobExtractionMetrics
import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.app.data.model.JobSourceType
import com.vantedge.app.data.model.extraction.ExtractionMethod
import com.vantedge.app.data.network.AiGateway
import com.vantedge.app.domain.PipelineTrace
import com.vantedge.pipeline.validation.P2ValidationEngine
import com.vantedge.pipeline.validation.ValidationDecision
import java.util.UUID

class JobExtractionOrchestrator(
    private val aiGateway: AiGateway?
) {
    companion object {
        internal suspend fun extractJobStandalone(
            rawText: String,
            sourceType: JobSourceType,
            onProgress: (String) -> Unit = {}
        ): Result<JobExtractionResult> {
            return JobExtractionOrchestrator(aiGateway = null).extractJob(rawText, sourceType, onProgress)
        }
    }

    suspend fun extractJob(
        rawText: String,
        sourceType: JobSourceType,
        onProgress: (String) -> Unit = {}
    ): Result<JobExtractionResult> {
        val startMs = System.currentTimeMillis()
        val correlationId = UUID.randomUUID().toString().take(8)
        PipelineTrace.entry("JobExtractionOrchestrator", mapOf(
            "textLength" to rawText.length,
            "sourceType" to sourceType,
            "correlationId" to correlationId
        ))

        return try {
            onProgress("Preprocessing document...")

            val preprocessResult = DocumentPreprocessor.preprocess(rawText, correlationId)
            val cleanedText = preprocessResult.text

            onProgress("Analyzing document structure...")

            val structuredDoc = DocumentStructureAnalyzer.analyze(cleanedText, correlationId)

            val extractionMethod: ExtractionMethod
            val llmResult: LlmJobExtractor.LlmFieldResult?

            if (aiGateway != null) {
                extractionMethod = ExtractionMethod.LLM
                onProgress("Extracting with AI...")

                val extractor = LlmJobExtractor(aiGateway)
                llmResult = extractor.extract(structuredDoc, onProgress, correlationId)

                onProgress("Validating extraction...")

                val schemaResult = SchemaValidator.validate(llmResult.rawJson, extractionMethod, correlationId)
                val schemaFailures = schemaResult.failures.map { it.field }

                val semanticResult = SemanticValidator.validate(
                    jobTitle = llmResult.jobTitle,
                    company = llmResult.company,
                    description = llmResult.description,
                    source = extractionMethod,
                    correlationId = correlationId
                )

                val confidenceResult = ConfidenceEngine.compute(
                    llmConfidenceMap = llmResult.fieldConfidence,
                    semanticAdjustments = semanticResult.fieldConfidenceAdjustments,
                    schemaFailures = schemaFailures,
                    source = extractionMethod,
                    correlationId = correlationId
                )

                val decision = ExtractionDecisionCoordinator.decide(
                    confidenceResult.fieldConfidenceMap,
                    extractionMethod,
                    correlationId
                )

                val durMs = System.currentTimeMillis() - startMs
                when (decision) {
                    is ExtractionDecision.Accept -> {
                        PipelineTrace.warn("JobExtractionOrchestrator", "LLM extraction accepted", correlationId)
                    }
                    is ExtractionDecision.Retry -> {
                        PipelineTrace.warn("JobExtractionOrchestrator", "Retry requested: ${decision.reason}", correlationId)
                    }
                    is ExtractionDecision.UserReview -> {
                        val reviewFields = decision.fields.joinToString(", ") { "${it.fieldName}=${"%.2f".format(it.confidence.overallConfidence)}" }
                        PipelineTrace.warn("JobExtractionOrchestrator", "User review needed: ${decision.fields.size} fields [$reviewFields]", correlationId)
                    }
                    is ExtractionDecision.Failure -> {
                        PipelineTrace.error("JobExtractionOrchestrator", "Extraction failed: ${decision.reason}", correlationId = correlationId)
                    }
                }

                val title = llmResult.jobTitle
                val company = llmResult.company
                val description = llmResult.description ?: buildFallbackDescription(structuredDoc.blocks.joinToString("\n") { it.text })

                val overallConf = confidenceResult.overallConfidence

                val result = buildJobExtractionResult(
                    title = title,
                    company = company,
                    description = description,
                    overallConfidence = overallConf,
                    sourceType = sourceType,
                    startMs = startMs,
                    extractionMethod = extractionMethod
                )

                val p2Result = P2ValidationEngine.validateJobExtractionResult(result, correlationId)
                PipelineTrace.dataQuality("P2Validation", "P2_DECISION", mapOf(
                    "correlationId" to correlationId,
                    "orchestrator" to "JobExtractionOrchestrator",
                    "decision" to p2Result.decision.javaClass.simpleName
                ), correlationId)
                when (p2Result.decision) {
                    is ValidationDecision.Reject -> {
                        PipelineTrace.warn("JobExtractionOrchestrator", "P2 rejection: ${p2Result.decision.reason}", correlationId)
                    }
                    is ValidationDecision.Degraded -> {
                        PipelineTrace.warn("JobExtractionOrchestrator", "P2 degraded: ${p2Result.decision.warnings}", correlationId)
                    }
                    else -> {}
                }

                PipelineTrace.exit("JobExtractionOrchestrator", durMs, mapOf(
                    "status" to "SUCCESS",
                    "title" to (title ?: ""),
                    "company" to (company ?: ""),
                    "confidence" to "%.2f".format(overallConf),
                    "method" to extractionMethod,
                    "correlationId" to correlationId
                ))
                Result.success(result)
            } else {
                PipelineTrace.warn("JobExtractionOrchestrator", "AiGateway unavailable — falling back to RuleBasedEmergencyExtractor", correlationId)

                val emergencyResult = RuleBasedEmergencyExtractor.extractJob(rawText, sourceType, correlationId)
                val emergencyDurMs = System.currentTimeMillis() - startMs
                PipelineTrace.exit("JobExtractionOrchestrator", emergencyDurMs, mapOf(
                    "status" to "EMERGENCY",
                    "success" to emergencyResult.isSuccess,
                    "method" to "EMERGENCY_RULE_BASED",
                    "correlationId" to correlationId
                ))
                return emergencyResult
            }
        } catch (e: Exception) {
            val failureDurMs = System.currentTimeMillis() - startMs
            PipelineTrace.error("JobExtractionOrchestrator", e.message ?: "EXTRACTION_FAILED", e, correlationId)

            return try {
                PipelineTrace.warn("JobExtractionOrchestrator", "Primary extraction failed — falling back to emergency extractor", correlationId)
                RuleBasedEmergencyExtractor.extractJob(rawText, sourceType, correlationId)
            } catch (fallbackError: Exception) {
                PipelineTrace.error("JobExtractionOrchestrator", "Emergency fallback also failed", fallbackError, correlationId)
                Result.failure(Exception("EXTRACTION_FAILED: ${e.message}"))
            }
        }
    }

    private fun buildJobExtractionResult(
        title: String?,
        company: String?,
        description: String,
        overallConfidence: Float,
        sourceType: JobSourceType,
        startMs: Long,
        extractionMethod: ExtractionMethod
    ): JobExtractionResult {
        val durationMs = System.currentTimeMillis() - startMs
        val titleContrib = if (title != null && title.length > 5) 0.15f else 0f
        val companyContrib = if (company != null && company.length > 3) 0.15f else 0f

        val confidenceBreakdown = ConfidenceBreakdown(
            baseScore = overallConfidence * 0.6f,
            titleContribution = titleContrib,
            companyContribution = companyContrib,
            qualificationContribution = 0f,
            penalties = if (extractionMethod == ExtractionMethod.EMERGENCY_RULE_BASED) listOf("EMERGENCY_FALLBACK") else emptyList(),
            finalScore = overallConfidence
        )

        return JobExtractionResult(
            extractionId = UUID.randomUUID().toString(),
            extractedAt = System.currentTimeMillis(),
            jobTitle = title,
            company = company,
            description = description,
            confidenceScore = overallConfidence,
            confidenceBreakdown = confidenceBreakdown,
            gate0Result = Gate0JobResult(
                score = 3,
                threshold = 2,
                accepted = true,
                reason = Gate0JobReason.ACCEPTED,
                detectedSignals = emptyList(),
                appliedPenalties = emptyList(),
                rejectionCauses = emptyList(),
                narrativeDensity = 0.0f
            ),
            metrics = JobExtractionMetrics(
                durationMs = durationMs,
                sourceType = sourceType,
                rawTextLength = description.length,
                gate0DurationMs = 0,
                gate1DurationMs = 0,
                gate2DurationMs = 0,
                gate3DurationMs = 0,
                gate4DurationMs = 0,
                qualificationPassed = true,
                narrativeDensity = 0.0f
            )
        )
    }

    private fun buildFallbackDescription(blockText: String): String {
        return blockText.take(5000)
    }
}
