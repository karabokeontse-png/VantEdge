package com.vantedge.app.w5.scoring

import com.vantedge.app.data.domain.CompatibilityMappingResult
import com.vantedge.app.data.domain.CompatibilityRecordMapper
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.domain.PipelineTrace

class DeterministicCompatibilityEngine(
    private val w5ScoreEngine: W5ScoreEngine,
    private val compatibilityRecordMapper: CompatibilityRecordMapper,
    private val skillTaxonomy: Set<String>,
    private val stopWords: Set<String>
) {

    suspend fun analyze(
        profile: UserProfile,
        normalizedProfile: NormalizedProfile,
        jobTitle: String,
        company: String,
        jobDescription: String,
        requiredSkills: List<String> = emptyList(),
        preferredSkills: List<String> = emptyList(),
        educationRequired: String? = null,
        correlationId: String
    ): CompatibilityMappingResult {
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("DeterministicCompatibilityEngine", mapOf(
            "correlationId" to correlationId,
            "jobTitle" to jobTitle,
            "company" to company,
            "descLength" to jobDescription.length
        ))

        val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)

        val validatedProfile = DirectW5InputAdapter.adaptProfile(
            profile = profile,
            normalizedProfile = normalizedProfile,
            correlationId = correlationId,
            currentYear = currentYear
        )

        PipelineTrace.dataQuality(
            stage = "DeterministicCompatibilityEngine",
            issue = "DIRECT_ADAPTOR_PROFILE",
            details = mapOf(
                "correlationId" to correlationId,
                "profileSkillCount" to validatedProfile.skills.size,
                "profileDegraded" to validatedProfile.isDegraded
            ),
            correlationId = correlationId
        )

        val adaptationResult = JobRequirementAdapter.adapt(
            jobDescription = jobDescription,
            requiredSkills = requiredSkills,
            preferredSkills = preferredSkills,
            educationRequired = educationRequired,
            skillTaxonomy = skillTaxonomy,
            stopWords = stopWords,
            correlationId = correlationId
        )
        val validatedJob = adaptationResult.validatedJob
        val diagnostics = adaptationResult.diagnostics

        val isDegraded = validatedJob.isDegraded
        if (isDegraded) {
            PipelineTrace.warn("DeterministicCompatibilityEngine", "Degraded job data: requiredSkills=${validatedJob.requiredSkills.size}, preferredSkills=${validatedJob.keywords.size}, unmapped=${diagnostics.summary.totalUnmapped}", correlationId)
        }

        PipelineTrace.dataQuality(
            stage = "DeterministicCompatibilityEngine",
            issue = "JOB_ADAPTATION",
            details = mapOf(
                "correlationId" to correlationId,
                "requiredSkills" to validatedJob.requiredSkills.size,
                "preferredSkills" to validatedJob.keywords.size,
                "requiredYears" to (validatedJob.requiredYears ?: "null"),
                "unmappedRequirements" to diagnostics.summary.totalUnmapped,
                "jobDegraded" to validatedJob.isDegraded
            ),
            correlationId = correlationId
        )

        val trace = W5TraceContext(
            correlationId = correlationId,
            inputHash = computeInputHash(validatedProfile, validatedJob),
            timestamp = System.currentTimeMillis()
        )

        val scoreResult = w5ScoreEngine.evaluate(validatedProfile, validatedJob, trace)

        val mappingResult = compatibilityRecordMapper.map(
            scoreResult = scoreResult,
            profile = profile,
            normalizedProfile = normalizedProfile,
            validatedProfile = validatedProfile,
            jobTitle = jobTitle,
            company = company,
            jobDescription = jobDescription,
            roleSummary = "",
            eligibilitySummary = "",
            currentYear = currentYear,
            validatedJob = validatedJob,
            diagnostics = diagnostics
        )

        val record = mappingResult.record

        val durationMs = System.currentTimeMillis() - startMs
        PipelineTrace.exit("DeterministicCompatibilityEngine", durationMs, mapOf(
            "correlationId" to correlationId,
            "score" to record.score,
            "gapCount" to record.gaps.size,
            "matchedCount" to record.relevancyItems.size
        ))

        return mappingResult
    }
}
