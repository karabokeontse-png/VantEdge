package com.vantedge.app.data.domain

import com.vantedge.app.data.model.*
import com.vantedge.app.domain.PipelineTrace
import com.vantedge.app.narrative.W5Evidence
import com.vantedge.app.w5.scoring.*

data class CompatibilityEvidenceReport(
    val diagnostics: List<RequirementDiagnostic>,
    val summary: DiagnosticsSummary
)

data class CompatibilityMappingResult(
    val record: CompatibilityRecord,
    val evidenceReport: CompatibilityEvidenceReport,
    val w5Evidence: W5Evidence
)

class CompatibilityRecordMapper {

    fun map(
        scoreResult: ScoreResult,
        profile: UserProfile,
        normalizedProfile: NormalizedProfile,
        validatedProfile: ValidatedProfile,
        jobTitle: String,
        company: String,
        jobDescription: String,
        roleSummary: String,
        eligibilitySummary: String,
        currentYear: Int,
        validatedJob: ValidatedJob,
        diagnostics: RequirementDiagnostics
    ): CompatibilityMappingResult {
        val correlationId = scoreResult.correlationId
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("CompatibilityRecordMapper", mapOf(
            "correlationId" to correlationId,
            "totalScore" to scoreResult.totalScore,
            "confidence" to scoreResult.confidence
        ))

        val evidenceReport = CompatibilityEvidenceReport(
            diagnostics = diagnostics.diagnostics,
            summary = diagnostics.summary
        )

        val gapAnalysis = scoreResult.gapAnalysis

        val score = (scoreResult.totalScore.coerceIn(0.0, 1.0) * 100).toInt()

        val skillMatch = scoreResult.axisScores.first { it.axisName == "SkillMatch" }.score
        val experienceAlignment = scoreResult.axisScores.first { it.axisName == "ExperienceAlignment" }.score
        val constraintCompliance = scoreResult.axisScores.first { it.axisName == "ConstraintCompliance" }.score
        val vacancyScore = (
            (skillMatch * 0.5) + (experienceAlignment * 0.3) + (constraintCompliance * 0.2)
        ).coerceIn(0.0, 1.0).times(100).toInt()

        val w5Evidence = W5Evidence(
            skillMatch = skillMatch,
            experienceAlignment = experienceAlignment,
            constraintCompliance = constraintCompliance,
            confidence = scoreResult.confidence
        )

        val gaps = gapAnalysis.missing.map {
            GapItem(skill = it, importance = "MANDATORY", description = "", experienceGap = false, platformGap = false, courses = emptyList())
        } + gapAnalysis.weak.map {
            GapItem(skill = it, importance = "IMPORTANT", description = "", experienceGap = false, platformGap = false, courses = emptyList())
        }

        val criticalGapCount = gaps.count { it.importance == "MANDATORY" }

        val relevancyItems = gapAnalysis.matched.map {
            RelevancyItem(
                name = it.name,
                type = "skill",
                matchPercent = (it.similarity * 100).toInt(),
                aiDescription = "",
                relevancyGroup = when {
                    it.similarity >= 0.75 -> "HIGH"
                    it.similarity >= 0.50 -> "MEDIUM"
                    it.similarity >= 0.30 -> "LOW"
                    else -> "PROFESSIONAL_MISMATCH"
                }
            )
        }

        val qualificationRatio = QualificationRatio(
            matched = gapAnalysis.matched.size,
            total = gapAnalysis.matched.size + gapAnalysis.weak.size + gapAnalysis.missing.size,
            gaps = gapAnalysis.weak.size + gapAnalysis.missing.size
        )

        val isDegraded = validatedJob.isDegraded

        val baseDataIntegrityNote = if (evidenceReport.summary.totalUnmapped > 0) {
            val categoryBreakdown = evidenceReport.diagnostics
                .groupBy { it.category }
                .mapValues { it.value.size }
                .entries
                .joinToString(", ") { "${it.value} ${it.key}" }
            "${evidenceReport.summary.totalUnmapped} unmapped requirement(s) ($categoryBreakdown)"
        } else if (isDegraded) {
            "Degraded job data — extraction confidence low"
        } else {
            "Derived from deterministic profile evidence"
        }

        val matchedQualifications = validatedJob.requiredQualifications.filter { jobReq ->
            validatedProfile.qualifications.any { profileQual ->
                profileQual.category == jobReq.category &&
                jaccard(
                    tokenize(profileQual.originalText, emptySet()).toSet(),
                    tokenize(jobReq.originalText, emptySet()).toSet()
                ) > 0.3
            }
        }

        val missingQualifications = validatedJob.requiredQualifications.filterNot { jobReq ->
            validatedProfile.qualifications.any { profileQual ->
                profileQual.category == jobReq.category &&
                jaccard(
                    tokenize(profileQual.originalText, emptySet()).toSet(),
                    tokenize(jobReq.originalText, emptySet()).toSet()
                ) > 0.3
            }
        }

        val qualificationNote = when {
            missingQualifications.isNotEmpty() && matchedQualifications.isNotEmpty() -> {
                " Matched: ${matchedQualifications.joinToString(", ") { "${it.category}: ${it.originalText}" }}. Missing: ${missingQualifications.joinToString(", ") { "${it.category}: ${it.originalText}" }}"
            }
            missingQualifications.isNotEmpty() -> {
                " Missing qualifications: ${missingQualifications.joinToString(", ") { "${it.category}: ${it.originalText}" }}"
            }
            matchedQualifications.isNotEmpty() -> {
                " All required qualifications matched: ${matchedQualifications.joinToString(", ") { "${it.category}: ${it.originalText}" }}"
            }
            else -> ""
        }

        val dataIntegrityNote = baseDataIntegrityNote + qualificationNote

        val profileStats = ProfileStats(
            yearsExperience = computeYearsExperience(profile.workHistory, currentYear),
            certificationCount = normalizedProfile.certifications.size,
            skillCount = validatedProfile.skills.size,
            matchedCount = gapAnalysis.matched.size,
            gapCount = gapAnalysis.missing.size + gapAnalysis.weak.size,
            dataIntegrityNote = dataIntegrityNote
        )

        val record = CompatibilityRecord(
            jobTitle = jobTitle,
            company = company,
            jobDescription = jobDescription,
            score = score,
            vacancyScore = vacancyScore,
            roleSummary = roleSummary,
            eligibilitySummary = eligibilitySummary,
            dataIntegrityNote = dataIntegrityNote,
            profileStats = profileStats,
            qualificationRatio = qualificationRatio,
            relevancyItems = relevancyItems,
            gaps = gaps,
            criticalGapCount = criticalGapCount
        )

        val durationMs = System.currentTimeMillis() - startMs
        PipelineTrace.dataQuality(
            stage = "CompatibilityRecordMapper",
            issue = "RECORD_MAPPED",
            details = mapOf(
                "correlationId" to correlationId,
                "score" to record.score,
                "vacancyScore" to record.vacancyScore,
                "criticalGapCount" to record.criticalGapCount,
                "gapCount" to record.gaps.size,
                "matchedCount" to record.relevancyItems.size,
                "dataIntegrityNote" to record.dataIntegrityNote,
                "unmappedRequirementCount" to evidenceReport.summary.totalUnmapped,
                "classificationWarningCount" to evidenceReport.diagnostics.size
            ),
            correlationId = correlationId
        )
        PipelineTrace.exit("CompatibilityRecordMapper", durationMs, mapOf(
            "correlationId" to correlationId,
            "score" to record.score,
            "gapCount" to record.gaps.size,
            "dataIntegrityNote" to record.dataIntegrityNote
        ))

        return CompatibilityMappingResult(
            record = record,
            evidenceReport = evidenceReport,
            w5Evidence = w5Evidence
        )
    }
}
