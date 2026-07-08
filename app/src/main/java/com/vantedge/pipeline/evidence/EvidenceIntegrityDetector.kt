package com.vantedge.pipeline.evidence

import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.w5.scoring.FabricationDetector

object EvidenceIntegrityDetector {

    private val ALLOWED_RELEVANCY_GROUPS = setOf("HIGH", "MEDIUM", "LOW", "PROFESSIONAL_MISMATCH")
    private val ALLOWED_IMPORTANCE_LEVELS = setOf("MANDATORY", "IMPORTANT", "NICE_TO_HAVE")

    fun validate(record: CompatibilityRecord, registry: EvidenceRegistry): ValidationReport {
        val entries = mutableListOf<ValidationReportEntry>()
        val correlationId = when (val result = registry.query(EvidenceCategory.SystemEvidence, "correlationId")) {
            is EvidenceResult.Found -> result.value as String
            else -> "unknown"
        }

        val fabricationReport = FabricationDetector.detect(record)
        for (issue in fabricationReport.issues) {
            when (issue.type) {
                "hallucination_marker" -> {
                    entries += ValidationReportEntry(
                        fieldPath = issue.field,
                        violationType = ViolationType.THIN,
                        expectedEvidence = "ProfileEvidence + JobEvidence: no hallucination markers",
                        actualEvidence = "Found hallucination marker in ${issue.field}",
                        detail = "Hallucination marker detected by FabricationDetector"
                    )
                }
                "hallucinated_url" -> {
                    entries += ValidationReportEntry(
                        fieldPath = issue.field,
                        violationType = ViolationType.MISMATCH,
                        expectedEvidence = "JobEvidence: valid URL from approved provider whitelist",
                        actualEvidence = "Hallucinated URL detected by FabricationDetector",
                        detail = "Course URL is hallucinated: ${issue.field}"
                    )
                }
            }
        }

        entries += validateScore(record)
        entries += validateVacancyScore(record)
        entries += validateProfileStats(record, registry)
        entries += validateQualificationRatio(record)
        entries += validateRelevancyItems(record, registry)
        entries += validateGaps(record, registry)
        entries += validateCriticalGapCount(record)

        return ValidationReport(entries, correlationId)
    }

    private fun validateScore(record: CompatibilityRecord): ValidationReportEntry {
        val inRange = record.score in 0..100
        return ValidationReportEntry(
            fieldPath = "score",
            violationType = if (inRange) ViolationType.THIN else ViolationType.MISMATCH,
            expectedEvidence = "Computed: integer in [0, 100]",
            actualEvidence = "score=${record.score}",
            detail = if (!inRange) "Score ${record.score} out of range [0, 100]" else null
        )
    }

    private fun validateVacancyScore(record: CompatibilityRecord): ValidationReportEntry {
        val inRange = record.vacancyScore in 0..100
        return ValidationReportEntry(
            fieldPath = "vacancyScore",
            violationType = if (inRange) ViolationType.THIN else ViolationType.MISMATCH,
            expectedEvidence = "Computed: integer in [0, 100]",
            actualEvidence = "vacancyScore=${record.vacancyScore}",
            detail = if (!inRange) "VacancyScore ${record.vacancyScore} out of range [0, 100]" else null
        )
    }

    private fun validateProfileStats(record: CompatibilityRecord, registry: EvidenceRegistry): List<ValidationReportEntry> {
        val entries = mutableListOf<ValidationReportEntry>()
        val stats = record.profileStats

        val expectedSkillCount = when (val result = registry.query(EvidenceCategory.ComputedEvidence, "skillCount")) {
            is EvidenceResult.Computed -> result.value as Int
            else -> -1
        }
        entries += ValidationReportEntry(
            fieldPath = "profileStats.skillCount",
            violationType = if (stats.skillCount == expectedSkillCount) ViolationType.VALID else ViolationType.MISMATCH,
            expectedEvidence = "ProfileEvidence: skills.size = $expectedSkillCount",
            actualEvidence = "profileStats.skillCount = ${stats.skillCount}",
            detail = if (stats.skillCount != expectedSkillCount) "Expected $expectedSkillCount, got ${stats.skillCount}" else null
        )

        val expectedCertCount = when (val result = registry.query(EvidenceCategory.ComputedEvidence, "certificationCount")) {
            is EvidenceResult.Computed -> result.value as Int
            else -> -1
        }
        entries += ValidationReportEntry(
            fieldPath = "profileStats.certificationCount",
            violationType = if (stats.certificationCount == expectedCertCount) ViolationType.VALID else ViolationType.MISMATCH,
            expectedEvidence = "ProfileEvidence: certifications.size = $expectedCertCount",
            actualEvidence = "profileStats.certificationCount = ${stats.certificationCount}",
            detail = if (stats.certificationCount != expectedCertCount) "Expected $expectedCertCount, got ${stats.certificationCount}" else null
        )

        val expectedYears = when (val result = registry.query(EvidenceCategory.ComputedEvidence, "yearsExperience")) {
            is EvidenceResult.Computed -> result.value as Int
            else -> -1
        }
        val yearsValid = kotlin.math.abs(stats.yearsExperience - expectedYears) <= 2
        entries += ValidationReportEntry(
            fieldPath = "profileStats.yearsExperience",
            violationType = if (yearsValid) ViolationType.VALID else ViolationType.MISMATCH,
            expectedEvidence = "ProfileEvidence: computed from workHistory = $expectedYears (tolerance +/-2)",
            actualEvidence = "profileStats.yearsExperience = ${stats.yearsExperience}",
            detail = if (!yearsValid) "Expected ~$expectedYears, got ${stats.yearsExperience}" else null
        )

        val actualMatched = record.relevancyItems.count { it.matchPercent > 0 }
        entries += ValidationReportEntry(
            fieldPath = "profileStats.matchedCount",
            violationType = if (stats.matchedCount == actualMatched) ViolationType.VALID else ViolationType.MISMATCH,
            expectedEvidence = "Computed: relevancyItems.count { matchPercent > 0 } = $actualMatched",
            actualEvidence = "profileStats.matchedCount = ${stats.matchedCount}",
            detail = if (stats.matchedCount != actualMatched) "Expected $actualMatched, got ${stats.matchedCount}" else null
        )

        val actualGapCount = record.gaps.size
        entries += ValidationReportEntry(
            fieldPath = "profileStats.gapCount",
            violationType = if (stats.gapCount == actualGapCount) ViolationType.VALID else ViolationType.MISMATCH,
            expectedEvidence = "Computed: gaps.size = $actualGapCount",
            actualEvidence = "profileStats.gapCount = ${stats.gapCount}",
            detail = if (stats.gapCount != actualGapCount) "Expected $actualGapCount, got ${stats.gapCount}" else null
        )

        return entries
    }

    private fun validateQualificationRatio(record: CompatibilityRecord): List<ValidationReportEntry> {
        val entries = mutableListOf<ValidationReportEntry>()
        val ratio = record.qualificationRatio
        val stats = record.profileStats

        entries += ValidationReportEntry(
            fieldPath = "qualificationRatio.matched",
            violationType = if (ratio.matched == stats.matchedCount) ViolationType.VALID else ViolationType.MISMATCH,
            expectedEvidence = "Computed: must equal profileStats.matchedCount = ${stats.matchedCount}",
            actualEvidence = "qualificationRatio.matched = ${ratio.matched}",
            detail = if (ratio.matched != stats.matchedCount) "Expected ${stats.matchedCount}, got ${ratio.matched}" else null
        )

        entries += ValidationReportEntry(
            fieldPath = "qualificationRatio.gaps",
            violationType = if (ratio.gaps == stats.gapCount) ViolationType.VALID else ViolationType.MISMATCH,
            expectedEvidence = "Computed: must equal profileStats.gapCount = ${stats.gapCount}",
            actualEvidence = "qualificationRatio.gaps = ${ratio.gaps}",
            detail = if (ratio.gaps != stats.gapCount) "Expected ${stats.gapCount}, got ${ratio.gaps}" else null
        )

        val expectedTotal = ratio.matched + ratio.gaps
        entries += ValidationReportEntry(
            fieldPath = "qualificationRatio.total",
            violationType = if (ratio.total == expectedTotal) ViolationType.VALID else ViolationType.MISMATCH,
            expectedEvidence = "Computed: matched + gaps = $expectedTotal",
            actualEvidence = "qualificationRatio.total = ${ratio.total}",
            detail = if (ratio.total != expectedTotal) "Expected $expectedTotal, got ${ratio.total}" else null
        )

        return entries
    }

    private fun validateRelevancyItems(record: CompatibilityRecord, registry: EvidenceRegistry): List<ValidationReportEntry> {
        val entries = mutableListOf<ValidationReportEntry>()

        for ((index, item) in record.relevancyItems.withIndex()) {
            val fieldPrefix = "relevancyItems[$index]"

            val skillResult = registry.query(EvidenceCategory.ProfileEvidence, "skill.${item.name}")
            val certResult = registry.query(EvidenceCategory.ProfileEvidence, "cert.${item.name}")
            val inProfile = skillResult is EvidenceResult.Found || certResult is EvidenceResult.Found

            entries += ValidationReportEntry(
                fieldPath = "$fieldPrefix.name",
                violationType = if (inProfile) ViolationType.VALID else ViolationType.MISSING,
                expectedEvidence = "ProfileEvidence: skill.${item.name} or cert.${item.name}",
                actualEvidence = "name=${item.name}, inProfile=$inProfile",
                detail = if (!inProfile) "Relevancy item '${item.name}' not found in profile skills or certifications" else null
            )

            val expectedType = when {
                skillResult is EvidenceResult.Found -> "skill"
                certResult is EvidenceResult.Found -> "certification"
                else -> null
            }
            val typeValid = expectedType != null && item.type == expectedType
            entries += ValidationReportEntry(
                fieldPath = "$fieldPrefix.type",
                violationType = if (typeValid) ViolationType.VALID else ViolationType.MISMATCH,
                expectedEvidence = "ProfileEvidence: type should be $expectedType",
                actualEvidence = "type=${item.type}",
                detail = if (!typeValid) "Expected type $expectedType, got ${item.type}" else null
            )

            val percentValid = item.matchPercent in 0..100
            entries += ValidationReportEntry(
                fieldPath = "$fieldPrefix.matchPercent",
                violationType = if (percentValid) ViolationType.VALID else ViolationType.MISMATCH,
                expectedEvidence = "Computed: integer in [0, 100]",
                actualEvidence = "matchPercent=${item.matchPercent}",
                detail = if (!percentValid) "matchPercent ${item.matchPercent} out of range" else null
            )

            val groupValid = item.relevancyGroup in ALLOWED_RELEVANCY_GROUPS
            entries += ValidationReportEntry(
                fieldPath = "$fieldPrefix.relevancyGroup",
                violationType = if (groupValid) ViolationType.VALID else ViolationType.MISMATCH,
                expectedEvidence = "Computed: one of $ALLOWED_RELEVANCY_GROUPS",
                actualEvidence = "relevancyGroup=${item.relevancyGroup}",
                detail = if (!groupValid) "Invalid relevancyGroup: ${item.relevancyGroup}" else null
            )
        }

        return entries
    }

    private fun validateGaps(record: CompatibilityRecord, registry: EvidenceRegistry): List<ValidationReportEntry> {
        val entries = mutableListOf<ValidationReportEntry>()

        for ((index, gap) in record.gaps.withIndex()) {
            val fieldPrefix = "gaps[$index]"

            val skillResult = registry.query(EvidenceCategory.ProfileEvidence, "skill.${gap.skill}")
            val certResult = registry.query(EvidenceCategory.ProfileEvidence, "cert.${gap.skill}")
            val inProfile = skillResult is EvidenceResult.Found || certResult is EvidenceResult.Found

            entries += ValidationReportEntry(
                fieldPath = "$fieldPrefix.skill",
                violationType = if (!inProfile) ViolationType.VALID else ViolationType.MISMATCH,
                expectedEvidence = "ProfileEvidence: skill.${gap.skill} must be MISSING (gap skill should not be in profile)",
                actualEvidence = "skill=${gap.skill}, inProfile=$inProfile",
                detail = if (inProfile) "Gap skill '${gap.skill}' was found in profile — cannot be a gap" else null
            )

            val jobResult = registry.query(EvidenceCategory.JobEvidence, "contains.${gap.skill}")
            val inJob = when (jobResult) {
                is EvidenceResult.Computed -> jobResult.value as Boolean
                else -> false
            }

            entries += ValidationReportEntry(
                fieldPath = "$fieldPrefix.skill.jobReference",
                violationType = if (inJob) ViolationType.VALID else ViolationType.THIN,
                expectedEvidence = "JobEvidence: contains.${gap.skill} should be true",
                actualEvidence = "skill=${gap.skill}, inJobDescription=$inJob",
                detail = if (!inJob) "Gap skill '${gap.skill}' not found in job description text" else null
            )

            val importanceValid = gap.importance in ALLOWED_IMPORTANCE_LEVELS
            entries += ValidationReportEntry(
                fieldPath = "$fieldPrefix.importance",
                violationType = if (importanceValid) ViolationType.VALID else ViolationType.MISMATCH,
                expectedEvidence = "Computed: one of $ALLOWED_IMPORTANCE_LEVELS",
                actualEvidence = "importance=${gap.importance}",
                detail = if (!importanceValid) "Invalid importance: ${gap.importance}" else null
            )
        }

        return entries
    }

    private fun validateCriticalGapCount(record: CompatibilityRecord): ValidationReportEntry {
        val expected = record.gaps.count { it.importance == "MANDATORY" }
        return ValidationReportEntry(
            fieldPath = "criticalGapCount",
            violationType = if (record.criticalGapCount == expected) ViolationType.VALID else ViolationType.MISMATCH,
            expectedEvidence = "Computed: gaps.count { importance == MANDATORY } = $expected",
            actualEvidence = "criticalGapCount=${record.criticalGapCount}",
            detail = if (record.criticalGapCount != expected) "Expected $expected, got ${record.criticalGapCount}" else null
        )
    }
}
