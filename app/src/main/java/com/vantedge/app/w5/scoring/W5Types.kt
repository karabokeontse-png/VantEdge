package com.vantedge.app.w5.scoring

data class W5TraceContext(
    val correlationId: String,
    val inputHash: String,
    val timestamp: Long
)

data class ValidatedProfile(
    val correlationId: String,
    val skills: List<String>,
    val roles: List<String>,
    val currentTitle: String?,
    val experienceYears: Int,
    val seniorityLevel: String?,
    val completedFields: Set<String>,
    val isAccepted: Boolean,
    val isDegraded: Boolean
)

data class ValidatedJob(
    val correlationId: String,
    val requiredSkills: List<String>,
    val title: String,
    val keywords: List<String>,
    val requiredYears: Int?,
    val seniorityLevel: String?,
    val completedFields: Set<String>,
    val isAccepted: Boolean,
    val isDegraded: Boolean
)

data class W5Input(
    val profile: ValidatedProfile,
    val job: ValidatedJob,
    val trace: W5TraceContext
)

data class ScoreResult(
    val correlationId: String,
    val totalScore: Double,
    val confidence: Double,
    val axisScores: List<AxisScore>,
    val breakdown: ScoreBreakdown,
    val gapAnalysis: GapAnalysis,
    val hints: RecommendationHints,
    val trace: W5TraceContext
)

data class AxisScore(
    val axisName: String,
    val score: Double
)

data class ScoreBreakdown(
    val axisBreakdowns: List<AxisBreakdown>
)

data class AxisBreakdown(
    val axisName: String,
    val score: Double,
    val weight: Double,
    val contributingFactors: List<Factor>
)

data class Factor(
    val field: String,
    val value: String,
    val contribution: Double
)

data class GapAnalysis(
    val missing: List<String>,
    val weak: List<String>,
    val matched: List<String>
)

data class RecommendationHints(
    val hints: List<String>
)

data class ValidationAssets(
    val stopWords: Set<String>,
    val keywordDictionary: Map<String, List<String>>
)
