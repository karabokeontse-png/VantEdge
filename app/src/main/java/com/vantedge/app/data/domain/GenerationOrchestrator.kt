package com.vantedge.app.data.domain

import com.vantedge.app.data.model.DesignConfig
import com.vantedge.app.data.model.GenerationCycle
import com.vantedge.app.data.model.GenerationMode
import com.vantedge.app.data.model.GenerationRequest
import com.vantedge.app.data.model.UserProfile

/**
 * Narrow orchestration contract consumed by [com.vantedge.app.data.viewmodel.CycleViewModel].
 *
 * Declared so the view model can be driven by a deterministic fake in JVM tests.
 * [OptimizationOrchestrator] is the sole production implementation and its behaviour is
 * unchanged; this interface only exposes the four entry points the view model invokes.
 */
interface GenerationOrchestrator {

    suspend fun runAnalysisOnly(
        profile: UserProfile, jobTitle: String, company: String, jobDescription: String,
        improvementContext: String? = null,
        requiredSkills: List<String> = emptyList(),
        preferredSkills: List<String> = emptyList(),
        educationRequired: String? = null
    ): GenerationCycle

    suspend fun runGenerationFromCycle(
        cycle: GenerationCycle,
        improvementContext: String? = null
    ): GenerationCycle

    suspend fun runFullPipeline(
        profile: UserProfile, jobTitle: String, company: String, jobDescription: String,
        mode: GenerationMode, improvementContext: String? = null, onProgress: (PipelineStep) -> Unit = {},
        requiredSkills: List<String> = emptyList(),
        preferredSkills: List<String> = emptyList(),
        educationRequired: String? = null,
        request: GenerationRequest = GenerationRequest.BOTH
    ): GenerationCycle

    suspend fun applyDesign(cycleId: String, design: DesignConfig): GenerationCycle
}
