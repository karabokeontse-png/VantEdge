package com.vantedge.app.data.domain

import com.vantedge.app.data.engine.CompatibilityEngine
import com.vantedge.app.data.engine.CompatibilityResult
import com.vantedge.app.data.engine.EngineResult
import com.vantedge.app.data.engine.GeneratorEngine
import com.vantedge.app.data.model.*
import com.vantedge.app.data.storage.HistoryStore
import com.vantedge.app.domain.PipelineTrace
import java.util.UUID

class OptimizationOrchestrator(
    private val compatibilityEngine: CompatibilityEngine,
    private val generatorEngine: GeneratorEngine,
    private val historyStore: HistoryStore
) {

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
            val compatibility = runAnalysisFresh(profile, jobTitle, company, jobDescription)

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
            PipelineTrace.error("analysis_only", e.message ?: "unknown", e, correlationId = correlationId)
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
            PipelineTrace.error("generation_from_cycle", e.message ?: "unknown", e, correlationId = correlationId)
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
            val compatibility = runAnalysisFresh(profile, jobTitle, company, jobDescription)

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
            PipelineTrace.error("full_pipeline", e.message ?: "unknown", e, correlationId = correlationId)
            PipelineTrace.exit("full_pipeline", durationMs, mapOf(
                "correlationId" to correlationId,
                "status" to "failure",
                "error" to (e.message ?: "unknown")
            ))
            throw e
        }
    }

    private suspend fun runAnalysisFresh(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        jobDescription: String
    ): CompatibilityRecord {
        val result = compatibilityEngine.analyze(
            profile = profile,
            jobTitle = jobTitle,
            company = company,
            jobDescription = jobDescription
        )
        return when (result) {
            is CompatibilityResult.Success -> result.data
            is CompatibilityResult.Failure -> throw IllegalStateException(
                "Compatibility analysis failed: ${result.type} - ${result.message}"
            )
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
            PipelineTrace.error("apply_design", e.message ?: "unknown", e, correlationId = correlationId)
            PipelineTrace.exit("apply_design", durationMs, mapOf(
                "correlationId" to correlationId,
                "status" to "failure",
                "error" to (e.message ?: "unknown")
            ))
            throw e
        }
    }
}
