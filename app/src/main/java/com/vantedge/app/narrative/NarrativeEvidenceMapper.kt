package com.vantedge.app.narrative

import com.vantedge.app.data.domain.CompatibilityMappingResult

object NarrativeEvidenceMapper {

    fun map(
        mappingResult: CompatibilityMappingResult,
        profileSkills: List<String>,
        profileExperienceYears: Int?,
        profileEducation: String?
    ): NarrativeEvidenceContext {
        val record = mappingResult.record
        val evidenceReport = mappingResult.evidenceReport
        val w5 = mappingResult.w5Evidence

        return NarrativeEvidenceContext(
            score = record.score,
            vacancyScore = record.vacancyScore,
            matchedSkillCount = record.profileStats.matchedCount,
            totalSkillOpportunities = record.profileStats.matchedCount + record.profileStats.gapCount,
            gapCount = record.profileStats.gapCount,
            criticalGapCount = record.criticalGapCount,
            w5Evidence = w5,
            unmappedRequirementCount = evidenceReport.summary.totalUnmapped,
            unmappedCategories = evidenceReport.diagnostics.map { it.category }.distinct(),
            profileSkills = profileSkills,
            profileExperienceYears = profileExperienceYears,
            profileEducation = profileEducation,
            dataIntegrityNote = record.dataIntegrityNote,
            unmappedRequirements = evidenceReport.diagnostics.map {
                UnmappedRequirement(
                    category = it.category,
                    text = it.text
                )
            }
        )
    }
}
