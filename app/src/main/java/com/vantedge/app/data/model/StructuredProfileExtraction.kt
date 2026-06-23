package com.vantedge.app.data.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class StructuredProfileExtraction(
    val personalInfo: ExtractedPersonalInfo,
    val summary: ExtractedField?,
    val skills: ImmutableList<ExtractedField>,
    val languages: ImmutableList<ExtractedField>,
    val workHistory: ImmutableList<ExtractedExperience>,
    val education: ImmutableList<ExtractedEducation>,
    val certifications: ImmutableList<ExtractedCertification>,
    val overallConfidence: Float,
    val warnings: ImmutableList<String>,
    val metadata: ExtractionMetadata
)
