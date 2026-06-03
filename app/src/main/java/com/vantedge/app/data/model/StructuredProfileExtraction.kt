package com.vantedge.app.data.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

enum class ExtractionSourceType {
    DIRECT, INFERRED, UNKNOWN
}

@Immutable
data class ExtractedField(
    val value: String,
    val confidence: Float,
    val sourceType: ExtractionSourceType
)

@Immutable
data class ExtractedPersonalInfo(
    val name: ExtractedField?,
    val email: ExtractedField?,
    val phone: ExtractedField?,
    val location: ExtractedField?,
    val linkedIn: ExtractedField?,
    val headline: ExtractedField?
)

@Immutable
data class ExtractedExperience(
    val jobTitle: ExtractedField,
    val company: ExtractedField,
    val startDate: ExtractedField?,
    val endDate: ExtractedField?,
    val description: ExtractedField?,
    val confidence: Float
)

@Immutable
data class ExtractedEducation(
    val institution: ExtractedField,
    val qualification: ExtractedField,
    val fieldOfStudy: ExtractedField?,
    val graduationYear: ExtractedField?,
    val confidence: Float
)

@Immutable
data class ExtractedCertification(
    val name: ExtractedField,
    val issuer: ExtractedField?,
    val confidence: Float
)

@Immutable
data class ExtractionMetadata(
    val modelUsed: String
)

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
