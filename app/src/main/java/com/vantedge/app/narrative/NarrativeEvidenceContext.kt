package com.vantedge.app.narrative

data class NarrativeEvidenceContext(
    val score: Int,
    val vacancyScore: Int,
    val matchedSkillCount: Int,
    val totalSkillOpportunities: Int,
    val gapCount: Int,
    val criticalGapCount: Int,
    val w5Evidence: W5Evidence,
    val unmappedRequirementCount: Int,
    val unmappedCategories: List<String>,
    val profileSkills: List<String>,
    val profileExperienceYears: Int?,
    val profileEducation: String?,
    val dataIntegrityNote: String?,
    val unmappedRequirements: List<UnmappedRequirement>
)

data class UnmappedRequirement(
    val category: String,
    val text: String
)
