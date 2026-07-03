package com.vantedge.app.data.domain

import com.fasterxml.jackson.databind.json.JsonMapper
import com.vantedge.app.data.engine.CompatibilityEngine
import com.vantedge.app.data.engine.CompatibilityResult
import com.vantedge.app.data.engine.EngineResult
import com.vantedge.app.data.engine.GeneratorEngine
import com.vantedge.app.data.engine.extraction.JsonExtractionEngine
import com.vantedge.app.data.model.DesignConfig
import com.vantedge.app.data.model.GenerationCycle
import com.vantedge.app.data.model.GenerationMode
import com.vantedge.app.data.domain.PipelineStep
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.network.AiGateway
import com.vantedge.app.data.network.AiRequest
import com.vantedge.app.data.storage.HistoryStore
import com.vantedge.app.domain.PipelineTrace
import com.vantedge.pipeline.contract.ContractValidationResult
import com.vantedge.pipeline.contract.ContractValidator
import com.vantedge.pipeline.contract.ExtractedAiPayload
import com.vantedge.pipeline.contract.ExtractionMetadata
import com.vantedge.pipeline.contract.JobType
import com.vantedge.pipeline.validation.P2ValidationEngine
import com.vantedge.pipeline.validation.ValidationDecision
import java.util.UUID

class OptimizationOrchestrator(
    private val aiGateway: AiGateway,
    private val contractValidator: ContractValidator,
    private val compatibilityEngine: CompatibilityEngine,
    private val generatorEngine: GeneratorEngine,
    private val historyStore: HistoryStore
) : CompatibilityOrchestrator {

    suspend fun runAnalysisOnly(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        jobDescription: String,
        improvementContext: String? = null
    ): GenerationCycle {
        val correlationId = UUID.randomUUID().toString().take(8)
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("analysis_only", mapOf(
            "correlationId" to correlationId,
            "jobTitle" to jobTitle,
            "company" to company
        ))

        return try {
            val analysisResult = runAnalysisFresh(profile, jobTitle, company, jobDescription)
            val compatibility = when (analysisResult) {
                is CompatibilityResult.Success -> analysisResult.data
                is CompatibilityResult.Failure -> throw IllegalStateException(
                    "Compatibility analysis failed: ${analysisResult.type} - ${analysisResult.message}"
                )
            }

            val cycle = GenerationCycle(
                jobTitle = jobTitle,
                company = company,
                jobDescription = jobDescription,
                profileSnapshot = profile,
                compatibility = compatibility,
                title = improvementContext,
                isVisibleInHistory = true
            )

            historyStore.saveCycle(cycle)

            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.exit("analysis_only", durationMs, mapOf(
                "correlationId" to correlationId,
                "status" to "success",
                "score" to compatibility.score
            ))
            cycle
        } catch (e: Exception) {
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.error("analysis_only", e.message ?: "unknown", e, correlationId)
            PipelineTrace.exit("analysis_only", durationMs, mapOf(
                "correlationId" to correlationId,
                "status" to "failure",
                "error" to (e.message ?: "unknown")
            ))
            throw e
        }
    }

    suspend fun runGenerationFromCycle(
        cycle: GenerationCycle,
        improvementContext: String? = null
    ): GenerationCycle {
        val correlationId = UUID.randomUUID().toString().take(8)
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("generation_from_cycle", mapOf(
            "correlationId" to correlationId,
            "jobTitle" to cycle.jobTitle,
            "company" to cycle.company
        ))

        return try {
            val compatibility = cycle.compatibility
                ?: throw Exception("Cannot generate: cycle has no compatibility analysis.")

            val enrichedJobDescription =
                if (!improvementContext.isNullOrBlank())
                    "${cycle.jobDescription}\n\n---\n$improvementContext"
                else cycle.jobDescription

            var cvError: String? = null
            var cvJson: String? = null
            generatorEngine.generateCv(
                profile = cycle.profileSnapshot,
                jobDescription = enrichedJobDescription,
                designId = "modern",
                schemeId = "navy",
                jobTitle = cycle.jobTitle,
                company = cycle.company,
                correlationId = correlationId,
                onResult = { result ->
                    when (result) {
                        is EngineResult.Success -> {
                            cvJson = result.data
                            cvError = null
                        }
                        is EngineResult.Failure -> {
                            cvJson = null
                            cvError = result.detail ?: result.type
                        }
                    }
                }
            )

            var coverLetterBody: String? = null
            var coverLetterError: String? = null
            generatorEngine.generateCoverLetter(
                profile = cycle.profileSnapshot,
                jobDescription = enrichedJobDescription,
                designId = "modern",
                schemeId = "navy",
                jobTitle = cycle.jobTitle,
                company = cycle.company,
                correlationId = correlationId,
                onResult = { result ->
                    when (result) {
                        is EngineResult.Success -> {
                            coverLetterBody = result.data
                            coverLetterError = null
                        }
                        is EngineResult.Failure -> {
                            coverLetterBody = null
                            coverLetterError = result.detail ?: result.type
                        }
                    }
                }
            )

            val matchedKeywords = if (cvJson == null) {
                emptyList()
            } else {
                try {
                    val json = org.json.JSONObject(cvJson)
                    val arr = json.getJSONArray("matchedKeywords")
                    (0 until arr.length()).map { arr.getString(it) }
                } catch (e: Exception) {
                    throw IllegalStateException(
                        "Failed to parse matchedKeywords from cvJson",
                        e
                    )
                }
            }

            val readyCycle = cycle.copy(
                compatibility = compatibility,
                matchedKeywords = matchedKeywords,
                cvContent = cvJson,
                coverLetterContent = coverLetterBody,
                cvErrorMessage = cvError,
                coverLetterErrorMessage = coverLetterError,
                title = improvementContext,
                isVisibleInHistory = true
            )

            historyStore.saveCycle(readyCycle)

            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.exit("generation_from_cycle", durationMs, mapOf(
                "correlationId" to correlationId,
                "status" to "success",
                "cvError" to (cvError ?: "none"),
                "coverLetterError" to (coverLetterError ?: "none")
            ))
            readyCycle
        } catch (e: Exception) {
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.error("generation_from_cycle", e.message ?: "unknown", e, correlationId)
            PipelineTrace.exit("generation_from_cycle", durationMs, mapOf(
                "correlationId" to correlationId,
                "status" to "failure",
                "error" to (e.message ?: "unknown")
            ))
            throw e
        }
    }

    suspend fun runFullPipeline(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        jobDescription: String,
        mode: GenerationMode,
        improvementContext: String? = null,
        onProgress: (PipelineStep) -> Unit = {}
    ): GenerationCycle {
        val correlationId = UUID.randomUUID().toString().take(8)
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("full_pipeline", mapOf(
            "correlationId" to correlationId,
            "jobTitle" to jobTitle,
            "company" to company,
            "mode" to mode.name
        ))

        return try {
            onProgress(PipelineStep.ANALYSING)
            val analysisResult = runAnalysisFresh(profile, jobTitle, company, jobDescription)
            val compatibility = when (analysisResult) {
                is CompatibilityResult.Success -> analysisResult.data
                is CompatibilityResult.Failure -> throw IllegalStateException(
                    "Compatibility analysis failed: ${analysisResult.type} - ${analysisResult.message}"
                )
            }

            val enrichedJobDescription =
                if (!improvementContext.isNullOrBlank())
                    "$jobDescription\n\n---\n$improvementContext"
                else jobDescription

            onProgress(PipelineStep.GENERATING_CV)
            var cvError: String? = null
            var cvJson: String? = null
            generatorEngine.generateCv(
                profile = profile,
                jobDescription = enrichedJobDescription,
                designId = "modern",
                schemeId = "navy",
                jobTitle = jobTitle,
                company = company,
                correlationId = correlationId,
                onResult = { result ->
                    when (result) {
                        is EngineResult.Success -> {
                            cvJson = result.data
                            cvError = null
                        }
                        is EngineResult.Failure -> {
                            cvJson = null
                            cvError = result.detail ?: result.type
                        }
                    }
                }
            )

            onProgress(PipelineStep.GENERATING_COVER_LETTER)
            var coverLetterBody: String? = null
            var coverLetterError: String? = null
            generatorEngine.generateCoverLetter(
                profile = profile,
                jobDescription = enrichedJobDescription,
                designId = "modern",
                schemeId = "navy",
                jobTitle = jobTitle,
                company = company,
                correlationId = correlationId,
                onResult = { result ->
                    when (result) {
                        is EngineResult.Success -> {
                            coverLetterBody = result.data
                            coverLetterError = null
                        }
                        is EngineResult.Failure -> {
                            coverLetterBody = null
                            coverLetterError = result.detail ?: result.type
                        }
                    }
                }
            )

            val matchedKeywords = if (cvJson == null) {
                emptyList()
            } else {
                try {
                    val json = org.json.JSONObject(cvJson)
                    val arr = json.getJSONArray("matchedKeywords")
                    (0 until arr.length()).map { arr.getString(it) }
                } catch (e: Exception) {
                    throw IllegalStateException(
                        "Failed to parse matchedKeywords from cvJson",
                        e
                    )
                }
            }

            val cycle = GenerationCycle(
                jobTitle = jobTitle,
                company = company,
                jobDescription = jobDescription,
                profileSnapshot = profile,
                compatibility = compatibility,
                matchedKeywords = matchedKeywords,
                cvContent = cvJson,
                coverLetterContent = coverLetterBody,
                cvErrorMessage = cvError,
                coverLetterErrorMessage = coverLetterError,
                title = improvementContext,
                isVisibleInHistory = true
            )

            historyStore.saveCycle(cycle)

            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.exit("full_pipeline", durationMs, mapOf(
                "correlationId" to correlationId,
                "status" to "success",
                "score" to compatibility.score,
                "cvError" to (cvError ?: "none"),
                "coverLetterError" to (coverLetterError ?: "none")
            ))
            cycle
        } catch (e: Exception) {
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.error("full_pipeline", e.message ?: "unknown", e, correlationId)
            PipelineTrace.exit("full_pipeline", durationMs, mapOf(
                "correlationId" to correlationId,
                "status" to "failure",
                "error" to (e.message ?: "unknown")
            ))
            throw e
        }
    }

    override suspend fun runAnalysisFresh(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        jobDescription: String
    ): CompatibilityResult {
        val correlationId = UUID.randomUUID().toString().take(8)
        val systemPrompt = """
You are an elite ATS analyst and career strategist. Perform a deep compatibility analysis.
Return ONLY a valid JSON object. No markdown. No explanation. No code blocks.

Schema (every field required unless noted):
- score: int 0-100 overall compatibility
- vacancyScore: int 0-100 hard vacancy match
- roleSummary: string 3-4 sentence role analysis
- eligibilitySummary: string 3-4 sentence candidate fit assessment
- dataIntegrityNote: string (optional)
- profileStats: { yearsExperience:int, certificationCount:int, skillCount:int, matchedCount:int, gapCount:int, dataIntegrityNote:string }
- qualificationRatio: { matched:int, total:int, gaps:int }
- relevancyItems[]: { name:string, type:string("skill"|"certification"), matchPercent:int 0-100, aiDescription:string, relevancyGroup:string("HIGH"|"MEDIUM"|"LOW"|"PROFESSIONAL_MISMATCH") }
- gaps[]: { skill:string, importance:string("MANDATORY"|"IMPORTANT"|"NICE_TO_HAVE"), description:string, experienceGap:bool, platformGap:bool, courses[]:{ title:string, provider:string, url:string, category:string, hasCertificate:bool, estimatedDuration:string, relevancyPercent:int 0-100, priority:int } }

STRICT RULES:
- score is 0-100 integer: overall compatibility
- vacancyScore is 0-100 integer: how well candidate meets the hard vacancy requirements only
- relevancyItems must include ALL certifications and skills from the candidate profile
- relevancyGroup must be exactly one of: "HIGH", "MEDIUM", "LOW", "PROFESSIONAL_MISMATCH"
- gaps must only list skills/certs the candidate does NOT have but the job needs
- importance must be exactly "MANDATORY", "IMPORTANT", or "NICE_TO_HAVE"
- experienceGap = true if the candidate has the skill but lacks sufficient years
- platformGap = true if the gap is about a specific platform/vendor tool
- For each gap provide 2-3 real course recommendations
- ONLY use real URLs from: Coursera, Udemy, edX, Google, Microsoft, LinkedIn Learning, AWS, freeCodeCamp, Cisco, CompTIA
        """.trimIndent()

        val userPrompt = """
CANDIDATE PROFILE:
Name: ${profile.name}
Summary: ${profile.summary}
Skills: ${profile.skills.joinToString(", ")}
Certifications: ${profile.certifications.joinToString(", ")}
Experience: ${profile.workHistory.joinToString("\n") { "${it.role} at ${it.company} (${it.startDate}-${it.endDate}): ${it.description}" }}
Education: ${profile.education.joinToString(", ")}
Languages: ${profile.languages.joinToString(", ")}

JOB DESCRIPTION:
$jobDescription
        """.trimIndent()

        val request = AiRequest(systemPrompt = systemPrompt, userPrompt = userPrompt)
        val aiResponse = aiGateway.generate("compatibility", request, 120_000L)

        if (aiResponse == null) {
            return CompatibilityResult.Failure("null_response", "AI returned null")
        }

        val extractionResult = JsonExtractionEngine.extract(aiResponse)
        if (!extractionResult.success) {
            return CompatibilityResult.Failure(
                "no_json",
                extractionResult.failureReason ?: "No JSON found",
                rawResponse = aiResponse
            )
        }

        val jsonNode = try {
            JsonMapper.builder().build().readTree(extractionResult.content)
        } catch (e: Exception) {
            return CompatibilityResult.Failure("parse_error", e.message, rawResponse = aiResponse)
        }

        val metadata = ExtractionMetadata(
            requestId = "",
            correlationId = "",
            modelName = "",
            extractionStrategy = extractionResult.strategy,
            rawLength = aiResponse.length,
            normalizedLength = extractionResult.content.length
        )
        val payload = ExtractedAiPayload(
            rawJson = aiResponse,
            parsedObject = jsonNode,
            metadata = metadata
        )

        return when (val validation = contractValidator.validate(JobType.VACANCY_SCORING, payload)) {
            is ContractValidationResult.Success -> {
                val validatedNode = validation.validatedObject.node
                val record = compatibilityEngine.analyze(
                    node = validatedNode,
                    jobTitle = jobTitle,
                    company = company,
                    jobDescription = jobDescription
                )
                val p2Result = P2ValidationEngine.validateContractResult(validatedNode, correlationId)
                PipelineTrace.dataQuality("P2Validation", "P2_DECISION", mapOf(
                    "correlationId" to correlationId,
                    "orchestrator" to "OptimizationOrchestrator",
                    "decision" to p2Result.decision.javaClass.simpleName
                ), correlationId)
                when (p2Result.decision) {
                    is ValidationDecision.Reject -> {
                        CompatibilityResult.Failure("p2_rejection", p2Result.decision.reason, rawResponse = aiResponse)
                    }
                    else -> {
                        if (p2Result.decision is ValidationDecision.Degraded) {
                            PipelineTrace.warn("OptimizationOrchestrator", "P2 degraded: ${p2Result.decision.warnings}")
                        }
                        CompatibilityResult.Success(record)
                    }
                }
            }
            is ContractValidationResult.Failure -> {
                CompatibilityResult.Failure(
                    type = "contract_violation",
                    message = validation.details,
                    rawResponse = aiResponse
                )
            }
        }
    }

    suspend fun applyDesign(
        cycleId: String,
        design: DesignConfig
    ): GenerationCycle {
        val correlationId = UUID.randomUUID().toString().take(8)
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("apply_design", mapOf(
            "correlationId" to correlationId,
            "cycleId" to cycleId,
            "designId" to design.templateId
        ))

        return try {
            val cycle = historyStore.getCycleByIdSuspend(cycleId)
                ?: throw Exception("Cycle not found.")

            val compatibility = cycle.compatibility
                ?: throw Exception("Cycle is not ready for design.")

            val previousCycles = historyStore.getCyclesForJob(cycle.jobTitle, cycle.company)
            val version = previousCycles.count { it.design != null } + 1

            val (cvHtml, coverLetterHtml) = generatorEngine.applyDesignToContent(
                profile = cycle.profileSnapshot,
                jobTitle = cycle.jobTitle,
                company = cycle.company,
                matchedKeywordsJson = "{\"matchedKeywords\":${
                    cycle.matchedKeywords.joinToString(
                        prefix = "[",
                        postfix = "]",
                        separator = ","
                    ) { "\"$it\"" }
                }}",
                coverLetterBody = cycle.coverLetterContent ?: "",
                designId = design.templateId,
                schemeId = design.colorScheme
            )

            val fullCycle = cycle.copy(
                compatibility = compatibility,
                cvContent = cvHtml,
                coverLetterContent = coverLetterHtml,
                design = design,
                version = version,
                title = null,
                isVisibleInHistory = true
            )

            historyStore.saveCycle(fullCycle)

            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.exit("apply_design", durationMs, mapOf(
                "correlationId" to correlationId,
                "status" to "success",
                "version" to version
            ))
            fullCycle
        } catch (e: Exception) {
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.error("apply_design", e.message ?: "unknown", e, correlationId)
            PipelineTrace.exit("apply_design", durationMs, mapOf(
                "correlationId" to correlationId,
                "status" to "failure",
                "error" to (e.message ?: "unknown")
            ))
            throw e
        }
    }

    suspend fun generateCv(
        profile: UserProfile,
        jobDescription: String,
        designId: String,
        schemeId: String,
        jobTitle: String,
        company: String,
        onResult: (EngineResult) -> Unit
    ) {
        val correlationId = UUID.randomUUID().toString().take(8)
        generatorEngine.generateCv(
            profile = profile,
            jobDescription = jobDescription,
            designId = designId,
            schemeId = schemeId,
            jobTitle = jobTitle,
            company = company,
            correlationId = correlationId,
            onResult = onResult
        )
    }

    suspend fun generateCoverLetter(
        profile: UserProfile,
        jobDescription: String,
        designId: String,
        schemeId: String,
        jobTitle: String,
        company: String,
        onResult: (EngineResult) -> Unit
    ) {
        val correlationId = UUID.randomUUID().toString().take(8)
        generatorEngine.generateCoverLetter(
            profile = profile,
            jobDescription = jobDescription,
            designId = designId,
            schemeId = schemeId,
            jobTitle = jobTitle,
            company = company,
            correlationId = correlationId,
            onResult = onResult
        )
    }

    suspend fun generateCvDocx(
        profile: UserProfile,
        jobDescription: String,
        onResult: (EngineResult) -> Unit
    ) {
        val correlationId = UUID.randomUUID().toString().take(8)
        generatorEngine.generateCvDocx(
            profile = profile,
            jobDescription = jobDescription,
            correlationId = correlationId,
            onResult = onResult
        )
    }
}
