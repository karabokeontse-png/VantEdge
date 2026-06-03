package com.vantedge.app.data.model

data class RelevancyItem(
    val name: String,
    val type: String,           // "certification" or "skill"
    val matchPercent: Int,
    val aiDescription: String,
    val relevancyGroup: String  // "HIGH", "MEDIUM", "LOW", "PROFESSIONAL_MISMATCH"
)

data class GapItem(
    val skill: String,
    val importance: String,     // "MANDATORY", "IMPORTANT", "NICE_TO_HAVE"
    val description: String,
    val experienceGap: Boolean = false,
    val platformGap: Boolean = false,
    val courses: List<CourseRecommendation>
)

data class CourseRecommendation(
    val title: String,
    val provider: String,
    val url: String,
    val category: String,       // "free", "paid", "discounted"
    val hasCertificate: Boolean,
    val estimatedDuration: String,
    val relevancyPercent: Int = 0,
    val priority: Int = 1       // 1 = highest priority
)

data class ProfileStats(
    val yearsExperience: Int,
    val certificationCount: Int,
    val skillCount: Int,
    val matchedCount: Int,
    val gapCount: Int,
    val dataIntegrityNote: String
)

data class QualificationRatio(
    val matched: Int,
    val total: Int,
    val gaps: Int
)

data class CompatibilityRecord(
    val id: Long = System.currentTimeMillis(),
    val jobTitle: String,
    val company: String,
    val jobDescription: String,
    val score: Int,
    val vacancyScore: Int = 0,      // score specifically against vacancy requirements
    val roleSummary: String,
    val eligibilitySummary: String,
    val dataIntegrityNote: String = "",
    val profileStats: ProfileStats,
    val qualificationRatio: QualificationRatio = QualificationRatio(0, 0, 0),
    val relevancyItems: List<RelevancyItem>,
    val gaps: List<GapItem>,
    val criticalGapCount: Int,
    val createdAt: Long = System.currentTimeMillis()
)