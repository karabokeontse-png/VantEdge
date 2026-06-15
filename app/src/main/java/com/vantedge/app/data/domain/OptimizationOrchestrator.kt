package com.vantedge.app.data.domain

import com.vantedge.app.data.engine.CompatibilityEngine
import com.vantedge.app.data.engine.GeneratorEngine
import com.vantedge.app.data.model.*
import com.vantedge.app.data.storage.HistoryStore

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

        val compatibility = runAnalysisFresh(profile, jobTitle, company, jobDescription)

        val cycle = GenerationCycle(
            jobTitle = jobTitle,
            company = company,
            jobDescription = jobDescription,
            profileSnapshot = profile,
            state = CycleState.AnalysisOnly(compatibility),
            title = improvementContext,
            isVisibleInHistory = true
        )

        historyStore.saveCycle(cycle)
        return cycle
    }

    suspend fun runGenerationFromCycle(
        cycle: GenerationCycle,
        improvementContext: String? = null
    ): GenerationCycle {

        val compatibility = when (val s = cycle.state) {
            is CycleState.AnalysisOnly -> s.compatibility
            is CycleState.GenerationReady -> s.compatibility
            is CycleState.FullCycle -> s.compatibility
        }

        val enrichedJobDescription =
            if (!improvementContext.isNullOrBlank())
                "${cycle.jobDescription}\n\n---\n$improvementContext"
            else cycle.jobDescription

        var cvJson: String? = null
        generatorEngine.generateCv(
            profile = cycle.profileSnapshot,
            jobDescription = enrichedJobDescription,
            designId = "modern",
            schemeId = "navy",
            jobTitle = cycle.jobTitle,
            company = cycle.company,
            onResult = { cvJson = it }
        )
        cvJson = cvJson ?: "{\"matchedKeywords\":[]}"

        var coverLetterBody: String? = null
        generatorEngine.generateCoverLetter(
            profile = cycle.profileSnapshot,
            jobDescription = enrichedJobDescription,
            designId = "modern",
            schemeId = "navy",
            jobTitle = cycle.jobTitle,
            company = cycle.company,
            onResult = { coverLetterBody = it }
        )

        val matchedKeywords = try {
            val json = org.json.JSONObject(cvJson!!)
            val arr = json.getJSONArray("matchedKeywords")
            (0 until arr.length()).map { arr.getString(it) }
        } catch (e: Exception) {
            emptyList()
        }

        val readyCycle = cycle.copy(
            state = CycleState.GenerationReady(
                compatibility = compatibility,
                matchedKeywords = matchedKeywords,
                coverLetterBody = coverLetterBody ?: ""
            ),
            title = improvementContext,
            isVisibleInHistory = true
        )

        historyStore.saveCycle(readyCycle)
        return readyCycle
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

        onProgress(PipelineStep.ANALYSING)
        val compatibility = runAnalysisFresh(profile, jobTitle, company, jobDescription)

        val enrichedJobDescription =
            if (!improvementContext.isNullOrBlank())
                "$jobDescription\n\n---\n$improvementContext"
            else jobDescription

        onProgress(PipelineStep.GENERATING_CV)
        var cvJson: String? = null
        generatorEngine.generateCv(
            profile = profile,
            jobDescription = enrichedJobDescription,
            designId = "modern",
            schemeId = "navy",
            jobTitle = jobTitle,
            company = company,
            onResult = { cvJson = it }
        )
        cvJson = cvJson ?: "{\"matchedKeywords\":[]}"

        onProgress(PipelineStep.GENERATING_COVER_LETTER)
        var coverLetterBody: String? = null
        generatorEngine.generateCoverLetter(
            profile = profile,
            jobDescription = enrichedJobDescription,
            designId = "modern",
            schemeId = "navy",
            jobTitle = jobTitle,
            company = company,
            onResult = { coverLetterBody = it }
        )

        val matchedKeywords = try {
            val json = org.json.JSONObject(cvJson!!)
            val arr = json.getJSONArray("matchedKeywords")
            (0 until arr.length()).map { arr.getString(it) }
        } catch (e: Exception) {
            emptyList()
        }

        val cycle = GenerationCycle(
            jobTitle = jobTitle,
            company = company,
            jobDescription = jobDescription,
            profileSnapshot = profile,
            state = CycleState.GenerationReady(
                compatibility = compatibility,
                matchedKeywords = matchedKeywords,
                coverLetterBody = coverLetterBody ?: ""
            ),
            title = improvementContext,
            isVisibleInHistory = true
        )

        historyStore.saveCycle(cycle)
        return cycle
    }

    private suspend fun runAnalysisFresh(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        jobDescription: String
    ): CompatibilityRecord {
        var result: CompatibilityRecord? = null
        compatibilityEngine.analyze(
            profile = profile,
            jobTitle = jobTitle,
            company = company,
            jobDescription = jobDescription,
            onResult = { result = it }
        )
        return result ?: throw Exception("Analysis failed. Check your connection.")
    }

    suspend fun applyDesign(
        cycleId: String,
        design: DesignConfig
    ): GenerationCycle {

        val cycle = historyStore.getCycleByIdSuspend(cycleId)
            ?: throw Exception("Cycle not found.")

        val readyState = cycle.state as? CycleState.GenerationReady
            ?: throw Exception("Cycle is not ready for design.")

        val previousCycles = historyStore.getCyclesForJob(cycle.jobTitle, cycle.company)
        val version = previousCycles.count { it.state is CycleState.FullCycle } + 1

        val (cvHtml, coverLetterHtml) = generatorEngine.applyDesignToContent(
            profile = cycle.profileSnapshot,
            jobTitle = cycle.jobTitle,
            company = cycle.company,
            matchedKeywordsJson = "{\"matchedKeywords\":${
                readyState.matchedKeywords.joinToString(
                    prefix = "[",
                    postfix = "]",
                    separator = ","
                ) { "\"$it\"" }
            }}",
            coverLetterBody = readyState.coverLetterBody,
            designId = design.templateId,
            schemeId = design.colorScheme
        )

        val fullCycle = cycle.copy(
            state = CycleState.FullCycle(
                compatibility = readyState.compatibility,
                cvContent = cvHtml,
                coverLetterContent = coverLetterHtml,
                design = design
            ),
            version = version,
            title = null,
            isVisibleInHistory = true
        )

        historyStore.saveCycle(fullCycle)
        return fullCycle
    }
}