package com.vantedge.app.data.model

import java.util.UUID

sealed class CycleState {
    data class AnalysisOnly(
        val compatibility: CompatibilityRecord
    ) : CycleState()

    data class GenerationReady(
        val compatibility: CompatibilityRecord,
        val matchedKeywords: List<String>,
        val coverLetterBody: String
    ) : CycleState()

    data class FullCycle(
        val compatibility: CompatibilityRecord,
        val cvContent: String,
        val coverLetterContent: String,
        val design: DesignConfig
    ) : CycleState()
}

data class DesignConfig(
    val templateId: String,
    val colorScheme: String
)

enum class GenerationMode {
    NEW_APPLICATION,
    QUICK_ANALYSIS,
    QUICK_GENERATE,
    IMPROVE
}

data class GenerationCycle(
    val id: String = UUID.randomUUID().toString(),
    val jobTitle: String,
    val company: String,
    val jobDescription: String,
    val profileSnapshot: UserProfile,
    val profileSchemaVersion: Int = 1,
    val state: CycleState,
    val version: Int? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val isCommitted: Boolean = false,
    val isVisibleInHistory: Boolean = false,
    val title: String? = null
)

data class CompatibilityResult(
    val score: Int,
    val matchedSkills: List<String>,
    val missingSkills: List<String>,
    val weakSections: List<String>,
    val suggestions: List<String>,
    val courses: List<CourseRecommendation>
)

enum class DocumentFormat {
    HTML, DOCX
}