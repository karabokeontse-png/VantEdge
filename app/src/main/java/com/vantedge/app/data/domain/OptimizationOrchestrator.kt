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
import com.vantedge.app.data.model.QualificationRatio
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
import com.vantedge.pipeline.validation.EvidenceSummary
import com.vantedge.pipeline.validation.P2ValidationEngine
import com.vantedge.pipeline.validation.ValidationDecision
import com.vantedge.app.w5.scoring.ProfileSanitizer
import com.vantedge.app.w5.scoring.NormalizedProfile
import com.vantedge.pipeline.evidence.VantEdgeEvidenceRegistry
import com.vantedge.pipeline.evidence.EvidenceIntegrityDetector
import com.vantedge.pipeline.evidence.EvidencePolicyEnforcer
import com.vantedge.pipeline.evidence.EnforcementAction
import com.vantedge.pipeline.evidence.FabricationSeverity
import com.vantedge.pipeline.evidence.ViolationType
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

class OptimizationOrchestrator(
    private val aiGateway: AiGateway,
    private val contractValidator: ContractValidator,
    private val compatibilityEngine: CompatibilityEngine,
    private val generatorEngine: GeneratorEngine,
    private val historyStore: HistoryStore
) : CompatibilityOrchestrator {

    suspend fun runAnalysisOnly(
        profile: UserProfile, jobTitle: String, company: String, jobDescription: String,
        improvementContext: String? = null,
        requiredSkills: List<String> = emptyList(),
        preferredSkills: List<String> = emptyList(),
        educationRequired: String? = null
    ): GenerationCycle {
        val correlationId = UUID.randomUUID().toString().take(8)
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("analysis_only", mapOf("correlationId" to correlationId, "jobTitle" to jobTitle, "company" to company))
        return try {
            val analysisResult = runAnalysisFresh(profile, jobTitle, company, jobDescription, requiredSkills, preferredSkills, educationRequired)
            val compatibility = when (analysisResult) {
                is CompatibilityResult.Success -> analysisResult.data
                is CompatibilityResult.Failure -> throw IllegalStateException("Compatibility analysis failed: ${analysisResult.type} - ${analysisResult.message}")
            }
            val cycle = GenerationCycle(jobTitle = jobTitle, company = company, jobDescription = jobDescription,
                profileSnapshot = profile, compatibility = compatibility, title = improvementContext, isVisibleInHistory = true)
            historyStore.saveCycle(cycle)
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.exit("analysis_only", durationMs, mapOf("correlationId" to correlationId, "status" to "success", "score" to compatibility.score))
            cycle
        } catch (e: Exception) {
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.error("analysis_only", e.message ?: "unknown", e, correlationId)
            PipelineTrace.exit("analysis_only", durationMs, mapOf("correlationId" to correlationId, "status" to "failure", "error" to (e.message ?: "unknown")))
            throw e
        }
    }

    suspend fun runGenerationFromCycle(cycle: GenerationCycle, improvementContext: String? = null): GenerationCycle {
        val correlationId = UUID.randomUUID().toString().take(8)
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("generation_from_cycle", mapOf("correlationId" to correlationId, "jobTitle" to cycle.jobTitle, "company" to cycle.company))
        return try {
            val compatibility = cycle.compatibility ?: throw Exception("Cannot generate: cycle has no compatibility analysis.")
            val enrichedJobDescription = if (!improvementContext.isNullOrBlank()) "${cycle.jobDescription}\n\n---\n$improvementContext" else cycle.jobDescription
            val cvResult = coroutineScope { val d = CompletableDeferred<EngineResult>(); launch(Dispatchers.IO) { generatorEngine.generateCv(profile = cycle.profileSnapshot, jobDescription = enrichedJobDescription, designId = "modern", schemeId = "navy", jobTitle = cycle.jobTitle, company = cycle.company, correlationId = correlationId, onResult = { result -> d.complete(result) }) }; d.await() }
            val cvJson: String = when (cvResult) { is EngineResult.Success -> cvResult.data; is EngineResult.Failure -> "" }
            val cvError: String? = when (cvResult) { is EngineResult.Success -> null; is EngineResult.Failure -> cvResult.detail ?: cvResult.type }
            val clResult = coroutineScope { val d = CompletableDeferred<EngineResult>(); launch(Dispatchers.IO) { generatorEngine.generateCoverLetter(profile = cycle.profileSnapshot, jobDescription = enrichedJobDescription, designId = "modern", schemeId = "navy", jobTitle = cycle.jobTitle, company = cycle.company, correlationId = correlationId, onResult = { result -> d.complete(result) }) }; d.await() }
            val coverLetterBody: String? = when (clResult) { is EngineResult.Success -> clResult.data; is EngineResult.Failure -> null }
            val coverLetterError: String? = when (clResult) { is EngineResult.Success -> null; is EngineResult.Failure -> clResult.detail ?: clResult.type }
            val matchedKeywords = if (cvJson.isEmpty()) emptyList() else { try { val json = org.json.JSONObject(cvJson); val arr = json.getJSONArray("matchedKeywords"); (0 until arr.length()).map { arr.getString(it) } } catch (e: Exception) { throw IllegalStateException("Failed to parse matchedKeywords from cvJson", e) } }
            val readyCycle = cycle.copy(compatibility = compatibility, matchedKeywords = matchedKeywords, cvContent = cvJson, coverLetterContent = coverLetterBody, cvErrorMessage = cvError, coverLetterErrorMessage = coverLetterError, title = improvementContext, isVisibleInHistory = true)
            historyStore.saveCycle(readyCycle)
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.exit("generation_from_cycle", durationMs, mapOf("correlationId" to correlationId, "status" to "success", "cvError" to (cvError ?: "none"), "coverLetterError" to (coverLetterError ?: "none")))
            readyCycle
        } catch (e: Exception) {
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.error("generation_from_cycle", e.message ?: "unknown", e, correlationId)
            PipelineTrace.exit("generation_from_cycle", durationMs, mapOf("correlationId" to correlationId, "status" to "failure", "error" to (e.message ?: "unknown")))
            throw e
        }
    }

    suspend fun runFullPipeline(
        profile: UserProfile, jobTitle: String, company: String, jobDescription: String,
        mode: GenerationMode, improvementContext: String? = null, onProgress: (PipelineStep) -> Unit = {},
        requiredSkills: List<String> = emptyList(),
        preferredSkills: List<String> = emptyList(),
        educationRequired: String? = null
    ): GenerationCycle {
        val correlationId = UUID.randomUUID().toString().take(8)
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("full_pipeline", mapOf("correlationId" to correlationId, "jobTitle" to jobTitle, "company" to company, "mode" to mode.name))
        return try {
            onProgress(PipelineStep.ANALYSING)
            val analysisResult = runAnalysisFresh(profile, jobTitle, company, jobDescription, requiredSkills, preferredSkills, educationRequired)
            val compatibility = when (analysisResult) { is CompatibilityResult.Success -> analysisResult.data; is CompatibilityResult.Failure -> throw IllegalStateException("Compatibility analysis failed: ${analysisResult.type} - ${analysisResult.message}") }
            val enrichedJobDescription = if (!improvementContext.isNullOrBlank()) "$jobDescription\n\n---\n$improvementContext" else jobDescription
            onProgress(PipelineStep.GENERATING_CV)
            val cvResult = coroutineScope { val d = CompletableDeferred<EngineResult>(); launch(Dispatchers.IO) { generatorEngine.generateCv(profile = profile, jobDescription = enrichedJobDescription, designId = "modern", schemeId = "navy", jobTitle = jobTitle, company = company, correlationId = correlationId, onResult = { result -> d.complete(result) }) }; d.await() }
            val cvJson: String = when (cvResult) { is EngineResult.Success -> cvResult.data; is EngineResult.Failure -> "" }
            val cvError: String? = when (cvResult) { is EngineResult.Success -> null; is EngineResult.Failure -> cvResult.detail ?: cvResult.type }
            onProgress(PipelineStep.GENERATING_COVER_LETTER)
            val clResult = coroutineScope { val d = CompletableDeferred<EngineResult>(); launch(Dispatchers.IO) { generatorEngine.generateCoverLetter(profile = profile, jobDescription = enrichedJobDescription, designId = "modern", schemeId = "navy", jobTitle = jobTitle, company = company, correlationId = correlationId, onResult = { result -> d.complete(result) }) }; d.await() }
            val coverLetterBody: String? = when (clResult) { is EngineResult.Success -> clResult.data; is EngineResult.Failure -> null }
            val coverLetterError: String? = when (clResult) { is EngineResult.Success -> null; is EngineResult.Failure -> clResult.detail ?: clResult.type }
            val matchedKeywords = if (cvJson.isEmpty()) emptyList() else { try { val json = org.json.JSONObject(cvJson); val arr = json.getJSONArray("matchedKeywords"); (0 until arr.length()).map { arr.getString(it) } } catch (e: Exception) { throw IllegalStateException("Failed to parse matchedKeywords from cvJson", e) } }
            val cycle = GenerationCycle(jobTitle = jobTitle, company = company, jobDescription = jobDescription, profileSnapshot = profile, compatibility = compatibility, matchedKeywords = matchedKeywords, cvContent = cvJson, coverLetterContent = coverLetterBody, cvErrorMessage = cvError, coverLetterErrorMessage = coverLetterError, title = improvementContext, isVisibleInHistory = true)
            historyStore.saveCycle(cycle)
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.exit("full_pipeline", durationMs, mapOf("correlationId" to correlationId, "status" to "success", "score" to compatibility.score, "cvError" to (cvError ?: "none"), "coverLetterError" to (coverLetterError ?: "none")))
            cycle
        } catch (e: Exception) {
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.error("full_pipeline", e.message ?: "unknown", e, correlationId)
            PipelineTrace.exit("full_pipeline", durationMs, mapOf("correlationId" to correlationId, "status" to "failure", "error" to (e.message ?: "unknown")))
            throw e
        }
    }

    override suspend fun runAnalysisFresh(
        profile: UserProfile, jobTitle: String, company: String, jobDescription: String,
        requiredSkills: List<String>,
        preferredSkills: List<String>,
        educationRequired: String?
    ): CompatibilityResult {
        val correlationId = UUID.randomUUID().toString().take(8)
        PipelineTrace.dataQuality(stage = "runAnalysisFresh", issue = "STRUCTURED_REQUIREMENTS", details = mapOf("correlationId" to correlationId, "requiredSkillsCount" to requiredSkills.size, "preferredSkillsCount" to preferredSkills.size, "educationRequired" to (educationRequired ?: "null")), correlationId = correlationId)
        val sanitizationResult = ProfileSanitizer.sanitize(profile)
        val normalizedProfile = NormalizedProfile.from(profile, sanitizationResult)
        PipelineTrace.dataQuality(stage = "ProfileSanitizer_ACTIVE", issue = "SANITIZATION_RESULT", details = mapOf("correlationId" to correlationId, "originalSkillCount" to profile.skills.size, "sanitizedSkillCount" to sanitizationResult.skills.size, "excludedTokens" to sanitizationResult.excluded.map { it.token }, "auditRuleIds" to sanitizationResult.audit.entries.map { it.ruleId }), correlationId = correlationId)

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
- relevancyItems must include relevant certifications and skills from the candidate profile.
- STRICT CONSTRAINT: Every item in relevancyItems MUST exactly match a skill or certification explicitly listed in the CANDIDATE PROFILE above. Do NOT invent, infer, or add any skills or certifications that are not present in the CANDIDATE PROFILE list.
- relevancyGroup must be exactly one of: "HIGH", "MEDIUM", "LOW", "PROFESSIONAL_MISMATCH"
- gaps must list job requirements the candidate does not fully satisfy. This includes skills/certs the candidate completely lacks, AND skills the candidate possesses but lacks the required years of experience (experienceGap=true).
- importance must be exactly "MANDATORY", "IMPORTANT", or "NICE_TO_HAVE"
- experienceGap = true if the candidate has the skill but lacks sufficient years
- platformGap = true if the gap is about a specific platform/vendor tool
- For each gap provide 2-3 real course recommendations
- ONLY use real URLs from: Coursera, Udemy, edX, Google, Microsoft, LinkedIn Learning, AWS, freeCodeCamp, Cisco, CompTIA
- STRICT CONSISTENCY: profileStats.matchedCount MUST equal the count of relevancyItems with matchPercent > 0. profileStats.gapCount MUST equal gaps.length. qualificationRatio.matched MUST equal profileStats.matchedCount, qualificationRatio.gaps MUST equal profileStats.gapCount, and qualificationRatio.total MUST equal matched + gaps. criticalGapCount MUST equal the count of gaps with importance "MANDATORY".
- CANONICAL REQUIREMENTS: When EXPLICIT REQUIRED SKILLS are provided below, they are the authoritative vacancy requirements. Use them as the ground truth for gap analysis, relevancyItems, and vacancyScore. Do NOT derive additional requirements from the raw job description text that contradict the explicit list.
- PRIORITY ORDERING: MANDATORY gaps must appear first in the gaps array, followed by IMPORTANT, then NICE_TO_HAVE. vacancyScore MUST reflect the ratio of explicit required skills the candidate satisfies.
        """.trimIndent()

        val userPrompt = """
CANDIDATE PROFILE:
Name: ${profile.name}
Summary: ${normalizedProfile.summary}
Skills: ${normalizedProfile.skills.joinToString(", ")}
Certifications: ${normalizedProfile.certifications.joinToString(", ")}
Experience: ${normalizedProfile.workHistory.joinToString("\n")}
Education: ${normalizedProfile.education.joinToString(", ")}
Languages: ${normalizedProfile.languages.joinToString(", ")}

EXPLICIT VACANCY REQUIREMENTS (CANONICAL — use these as ground truth):
Required Skills: ${requiredSkills.joinToString(", ")}
Preferred Skills: ${preferredSkills.joinToString(", ")}
Education Required: ${educationRequired ?: "Not specified"}

JOB DESCRIPTION:
$jobDescription
        """.trimIndent()

        val request = AiRequest(systemPrompt = systemPrompt, userPrompt = userPrompt, maxTokens = 8192)
        val aiResponse = aiGateway.generate("compatibility", request, 120_000L)
        if (aiResponse == null) return CompatibilityResult.Failure("null_response", "AI returned null")

        val extractionResult = JsonExtractionEngine.extract(aiResponse)
        if (!extractionResult.success) return CompatibilityResult.Failure("no_json", extractionResult.failureReason ?: "No JSON found", rawResponse = aiResponse)

        val jsonNode = try { JsonMapper.builder().build().readTree(extractionResult.content) } catch (e: Exception) { return CompatibilityResult.Failure("parse_error", e.message, rawResponse = aiResponse) }

        val metadata = ExtractionMetadata(requestId = "", correlationId = "", modelName = "", extractionStrategy = extractionResult.strategy, rawLength = aiResponse.length, normalizedLength = extractionResult.content.length)
        val payload = ExtractedAiPayload(rawJson = aiResponse, parsedObject = jsonNode, metadata = metadata)

        return when (val validation = contractValidator.validate(JobType.VACANCY_SCORING, payload)) {
            is ContractValidationResult.Success -> {
                var record = compatibilityEngine.analyze(
                    node = validation.validatedObject.node,
                    jobTitle = jobTitle,
                    company = company,
                    jobDescription = jobDescription
                )

                // TD-COMPATIBILITY-001: Deterministic override of simple counts (previously verified).
                record = record.copy(
                    profileStats = record.profileStats.copy(
                        skillCount = normalizedProfile.skills.size,
                        certificationCount = normalizedProfile.certifications.size
                    )
                )

                // TD-COMPATIBILITY-001 ESCALATION: Deterministic filtering of hallucinated 
                // relevancy items to eliminate E3 HALT. Aggregate scoring fields (matchedCount, 
                // gapCount, qualificationRatio) are preserved to avoid scope creep into TD-COMPATIBILITY-002.
                val validProfileAssets = (normalizedProfile.skills.map { it.lowercase().trim() } + 
                                          normalizedProfile.certifications.map { it.lowercase().trim() }).toSet()
                
                val filteredRelevancyItems = record.relevancyItems.filter { item ->
                    val isValid = validProfileAssets.contains(item.name.lowercase().trim())
                    if (!isValid) {
                        PipelineTrace.dataQuality(
                            stage = "OptimizationOrchestrator",
                            issue = "HALLUCINATED_RELEVANCY_ITEM_REMOVED",
                            details = mapOf("itemName" to item.name, "itemType" to item.type),
                            correlationId = correlationId
                        )
                    }
                    isValid
                }

                val removedCount = record.relevancyItems.size - filteredRelevancyItems.size
                val baseNote = record.dataIntegrityNote ?: ""
                val integrityNote = if (removedCount > 0) {
                    "System removed $removedCount hallucinated relevancy items to ensure data integrity. $baseNote".trim()
                } else {
                    baseNote
                }

                record = record.copy(
                    relevancyItems = filteredRelevancyItems,
                    dataIntegrityNote = integrityNote
                )

                PipelineTrace.dataQuality(
                    stage = "OptimizationOrchestrator",
                    issue = "DETERMINISTIC_OVERRIDE",
                    details = mapOf(
                        "correlationId" to correlationId,
                        "skillCount" to normalizedProfile.skills.size,
                        "certificationCount" to normalizedProfile.certifications.size,
                        "removedHallucinatedItems" to removedCount
                    ),
                    correlationId = correlationId
                )

                // TD-COMPATIBILITY-002: Deterministic Aggregate Reconciliation
                val deterministicMatched = filteredRelevancyItems.count { it.matchPercent > 0 }
                val deterministicGapCount = record.gaps.size
                val deterministicCritical = record.gaps.count { it.importance == "MANDATORY" }
                val deterministicRatio = QualificationRatio(
                    matched = deterministicMatched,
                    total = deterministicMatched + deterministicGapCount,
                    gaps = deterministicGapCount
                )

                val mismatches = mutableListOf<String>()
                if (record.profileStats.matchedCount != deterministicMatched) mismatches.add("matchedCount: LLM ${record.profileStats.matchedCount} -> $deterministicMatched")
                if (record.profileStats.gapCount != deterministicGapCount) mismatches.add("gapCount: LLM ${record.profileStats.gapCount} -> $deterministicGapCount")
                if (record.criticalGapCount != deterministicCritical) mismatches.add("criticalGapCount: LLM ${record.criticalGapCount} -> $deterministicCritical")
                if (record.qualificationRatio != deterministicRatio) mismatches.add("qualificationRatio: LLM ${record.qualificationRatio} -> $deterministicRatio")

                if (mismatches.isNotEmpty()) {
                    val reconciliationNote = "Aggregate reconciliation: " + mismatches.joinToString("; ")
                    val existingNote = record.profileStats.dataIntegrityNote
                    val newNote = if (existingNote.isBlank()) reconciliationNote else "$existingNote | $reconciliationNote"

                    record = record.copy(
                        profileStats = record.profileStats.copy(
                            matchedCount = deterministicMatched,
                            gapCount = deterministicGapCount,
                            dataIntegrityNote = newNote
                        ),
                        criticalGapCount = deterministicCritical,
                        qualificationRatio = deterministicRatio
                    )
                }

                val evidenceRegistry = VantEdgeEvidenceRegistry(
                    normalizedProfile = normalizedProfile,
                    userProfile = profile,
                    jobDescription = jobDescription,
                    correlationId = correlationId,
                    requiredSkills = requiredSkills,
                    preferredSkills = preferredSkills,
                    educationRequired = educationRequired
                )
                val validationReport = EvidenceIntegrityDetector.validate(record, evidenceRegistry)
                val enforcementDecision = EvidencePolicyEnforcer.enforce(validationReport)

                val integrityAnnotatedRecord = when (enforcementDecision.action) {
                    EnforcementAction.ANNOTATE,
                    EnforcementAction.CORRECT -> {
                        val notes = enforcementDecision.classifiedEntries
                            .filter { it.severity != FabricationSeverity.E0 }
                            .joinToString("; ") { "${it.entry.fieldPath}: ${it.entry.violationType}" }
                        record.copy(dataIntegrityNote = if (record.dataIntegrityNote.isBlank()) notes else "${record.dataIntegrityNote}; $notes")
                    }
                    else -> record
                }

                // Compute overall severity once for use in both HALT and later evidenceSummary
                val severityPriority = mapOf("E0" to 0, "E1" to 1, "E2" to 2, "E3" to 3, "E4" to 4)
                val maxSeverityNum = enforcementDecision.classifiedEntries
                    .map { severityPriority[it.severity.name] ?: 0 }
                    .maxOrNull() ?: 0
                val overallSeverity = severityPriority.entries.find { it.value == maxSeverityNum }?.key ?: "E0"

                when (enforcementDecision.action) {
                    EnforcementAction.HALT -> {
                        return CompatibilityResult.Failure(
                            type = "evidence_integrity_${overallSeverity.lowercase()}",
                            message = "Evidence integrity $overallSeverity violation. Violated fields: ${enforcementDecision.classifiedEntries.filter { it.severity.name == overallSeverity }.joinToString { "${it.entry.fieldPath}(${it.entry.violationType})" }}",
                            rawResponse = aiResponse
                        )
                    }
                    EnforcementAction.REGENERATE -> {
                        PipelineTrace.dataQuality(
                            stage = "OptimizationOrchestrator",
                            issue = "E3_REGENERATION_DEFERRED",
                            details = mapOf(
                                "correlationId" to correlationId,
                                "violatedFields" to enforcementDecision.classifiedEntries.filter { it.severity >= FabricationSeverity.E3 }.joinToString { "${it.entry.fieldPath}(${it.entry.violationType})" },
                                "reason" to "Full source required for AI generation call integration"
                            ),
                            correlationId = correlationId
                        )
                        return CompatibilityResult.Failure(
                            type = "evidence_integrity_e3",
                            message = "Evidence integrity E3 violation requires regeneration. Violated fields: ${enforcementDecision.classifiedEntries.filter { it.severity >= FabricationSeverity.E3 }.joinToString { "${it.entry.fieldPath}(${it.entry.violationType})" }}",
                            rawResponse = aiResponse
                        )
                    }
                    else -> { }
                }

                val e0Count = enforcementDecision.classifiedEntries.count { it.severity == FabricationSeverity.E0 }

                val evidenceSummary = EvidenceSummary(
                    overallSeverity = overallSeverity,
                    thinCount = enforcementDecision.classifiedEntries.count { it.entry.violationType == ViolationType.THIN },
                    mismatchCount = enforcementDecision.classifiedEntries.count { it.entry.violationType == ViolationType.MISMATCH },
                    unsupportedCount = enforcementDecision.classifiedEntries.count { it.entry.violationType == ViolationType.MISSING },
                    correctedCount = if (enforcementDecision.action == EnforcementAction.CORRECT) e0Count else 0,
                    annotatedCount = if (enforcementDecision.action == EnforcementAction.ANNOTATE) e0Count else 0,
                    haltRequired = enforcementDecision.action == EnforcementAction.HALT,
                    regenerateRequired = enforcementDecision.action == EnforcementAction.REGENERATE,
                    affectedFields = enforcementDecision.classifiedEntries.map { it.entry.fieldPath }
                )

                val p2Result = P2ValidationEngine.validateContractResult(
                    validation.validatedObject.node,
                    correlationId,
                    evidenceSummary
                )
                PipelineTrace.dataQuality(
                    "P2Validation",
                    "P2_DECISION",
                    mapOf(
                        "correlationId" to correlationId,
                        "orchestrator" to "OptimizationOrchestrator",
                        "decision" to p2Result.decision.javaClass.simpleName,
                        "confidencePenalty" to p2Result.confidencePenaltyPercent
                    ),
                    correlationId
                )

                when (p2Result.decision) {
                    is ValidationDecision.Reject -> CompatibilityResult.Failure(
                        "p2_rejection",
                        p2Result.decision.reason,
                        rawResponse = aiResponse
                    )
                    else -> {
                        if (p2Result.decision is ValidationDecision.Degraded) {
                            PipelineTrace.warn(
                                "OptimizationOrchestrator",
                                "P2 degraded: ${p2Result.decision.warnings}; confidencePenalty=${p2Result.confidencePenaltyPercent}%; evidenceFields=${p2Result.evidenceAffectedFields.joinToString()}"
                            )
                        }
                        CompatibilityResult.Success(integrityAnnotatedRecord)
                    }
                }
            }
            is ContractValidationResult.Failure -> CompatibilityResult.Failure(type = "contract_violation", message = validation.details, rawResponse = aiResponse)
        }
    }

    suspend fun applyDesign(cycleId: String, design: DesignConfig): GenerationCycle {
        val correlationId = UUID.randomUUID().toString().take(8)
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("apply_design", mapOf("correlationId" to correlationId, "cycleId" to cycleId, "designId" to design.templateId))
        return try {
            val cycle = historyStore.getCycleByIdSuspend(cycleId) ?: throw Exception("Cycle not found.")
            val compatibility = cycle.compatibility ?: throw Exception("Cycle is not ready for design.")
            val previousCycles = historyStore.getCyclesForJob(cycle.jobTitle, cycle.company)
            val version = previousCycles.count { it.design != null } + 1
            val (cvHtml, coverLetterHtml) = generatorEngine.applyDesignToContent(profile = cycle.profileSnapshot, jobTitle = cycle.jobTitle, company = cycle.company, matchedKeywordsJson = "{\"matchedKeywords\":${cycle.matchedKeywords.joinToString(prefix = "[", postfix = "]", separator = ",") { "\"$it\"" }}}", coverLetterBody = cycle.coverLetterContent ?: "", designId = design.templateId, schemeId = design.colorScheme)
            val fullCycle = cycle.copy(compatibility = compatibility, cvContent = cvHtml, coverLetterContent = coverLetterHtml, design = design, version = version, title = null, isVisibleInHistory = true)
            historyStore.saveCycle(fullCycle)
            val durationMs = System.currentTimeMillis() - startMs
            PipelineTrace.exit("apply_design", durationMs, mapOf("correlationId" to correlationId, "status" to "success", "version" to version))
            fullCycle
        } catch (e: Exception) { val durationMs = System.currentTimeMillis() - startMs; PipelineTrace.error("apply_design", e.message ?: "unknown", e, correlationId); PipelineTrace.exit("apply_design", durationMs, mapOf("correlationId" to correlationId, "status" to "failure", "error" to (e.message ?: "unknown"))); throw e }
    }

    suspend fun generateCv(profile: UserProfile, jobDescription: String, designId: String, schemeId: String, jobTitle: String, company: String, onResult: (EngineResult) -> Unit) {
        val correlationId = UUID.randomUUID().toString().take(8)
        generatorEngine.generateCv(profile = profile, jobDescription = jobDescription, designId = designId, schemeId = schemeId, jobTitle = jobTitle, company = company, correlationId = correlationId, onResult = onResult)
    }

    suspend fun generateCoverLetter(profile: UserProfile, jobDescription: String, designId: String, schemeId: String, jobTitle: String, company: String, onResult: (EngineResult) -> Unit) {
        val correlationId = UUID.randomUUID().toString().take(8)
        generatorEngine.generateCoverLetter(profile = profile, jobDescription = jobDescription, designId = designId, schemeId = schemeId, jobTitle = jobTitle, company = company, correlationId = correlationId, onResult = onResult)
    }

    suspend fun generateCvDocx(profile: UserProfile, jobDescription: String, onResult: (EngineResult) -> Unit) {
        val correlationId = UUID.randomUUID().toString().take(8)
        generatorEngine.generateCvDocx(profile = profile, jobDescription = jobDescription, correlationId = correlationId, onResult = onResult)
    }
}
