package com.vantedge.app.w5.scoring


enum class RequirementCategory {
    SKILL,
    DEGREE,
    CERTIFICATION,
    EXPERIENCE,
    LANGUAGE,
    REGULATION,
    DOMAIN_KNOWLEDGE,
    SOFT_SKILL,
    LICENCE,
    SECURITY_CLEARANCE,
    PHYSICAL_REQUIREMENT,
    TRAVEL,
    OTHER,
    UNCLASSIFIED
}


enum class ClassificationStatus {
    NORMALIZED,
    UNSUPPORTED,
    UNMAPPED
}


data class RequirementEvidence(
    val originalText: String,
    val category: RequirementCategory,
    val classificationStatus: ClassificationStatus,
    val matchedTaxonomySkill: String? = null
)
