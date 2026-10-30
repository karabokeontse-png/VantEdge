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

        val skillMatchAxis = scoreResult.axisScores.first { it.axisName == "SkillMatch" }
        val skillMatch = skillMatchAxis.score
        val experienceAlignmentAxis = scoreResult.axisScores.first { it.axisName == "ExperienceAlignment" }
        val experienceAlignment = experienceAlignmentAxis.score
        val constraintAxis = scoreResult.axisScores.first { it.axisName == "ConstraintCompliance" }
        val constraintCompliance = constraintAxis.score
        val vacancyScore = (
            (skillMatch * 0.5) + (experienceAlignment * 0.3) + (constraintCompliance * 0.2)
        ).coerceIn(0.0, 1.0).times(100).toInt()

        val provisionalScore = scoreResult.provisionalTotalScore
            ?.coerceIn(0.0, 1.0)
            ?.let { (it * 100).toInt() }

        val vacancyComponents = listOf(
            Triple("SkillMatch", 0.5, skillMatchAxis),
            Triple("ExperienceAlignment", 0.3, experienceAlignmentAxis),
            Triple("ConstraintCompliance", 0.2, constraintAxis)
        )
        val measuredVacancyComponents = vacancyComponents.filter { it.third.isMeasured }
        val measuredVacancyWeight = measuredVacancyComponents.sumOf { it.second }
        val hasVacancyEvidence = measuredVacancyComponents.any {
            it.first == "SkillMatch" || it.first == "ExperienceAlignment"
        }
        val provisionalVacancyScore = if (!hasVacancyEvidence || measuredVacancyWeight <= 1e-9) {
            null
        } else {
            (measuredVacancyComponents.sumOf { it.third.score * it.second } / measuredVacancyWeight)
                .coerceIn(0.0, 1.0)
                .times(100)
                .toInt()
        }

        val w5Evidence = W5Evidence(
            skillMatch = skillMatch,
            experienceAlignment = experienceAlignmentAxis.takeIf { it.isMeasured }?.score,
            constraintCompliance = constraintCompliance,
            confidence = scoreResult.confidence
        )

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

        val qualificationRequirementsPresent = validatedJob.requiredQualifications.isNotEmpty()
        val profileQualificationsAssessed = validatedProfile.qualifications.isNotEmpty()
        val qualificationsAssessed = !qualificationRequirementsPresent || profileQualificationsAssessed

        val matchedQualifications = if (qualificationsAssessed) {
            validatedJob.requiredQualifications.filter { jobReq ->
                validatedProfile.qualifications.any { profileQual ->
                    profileQual.category == jobReq.category &&
                    jaccard(
                        tokenize(profileQual.originalText, emptySet()).toSet(),
                        tokenize(jobReq.originalText, emptySet()).toSet()
                    ) > 0.3
                }
            }
        } else {
            emptyList()
        }

        val missingQualifications = if (qualificationsAssessed) {
            validatedJob.requiredQualifications.filterNot { jobReq ->
                validatedProfile.qualifications.any { profileQual ->
                    profileQual.category == jobReq.category &&
                    jaccard(
                        tokenize(profileQual.originalText, emptySet()).toSet(),
                        tokenize(jobReq.originalText, emptySet()).toSet()
                    ) > 0.3
                }
            }
        } else {
            emptyList()
        }

        val gaps = gapAnalysis.missing.map {
            GapItem(skill = it, importance = "MANDATORY", description = "", experienceGap = false, platformGap = false, courses = emptyList())
        } + gapAnalysis.weak.map {
            GapItem(skill = it, importance = "IMPORTANT", description = "", experienceGap = false, platformGap = false, courses = emptyList())
        } + missingQualifications.map {
            GapItem(skill = "${it.category}: ${it.originalText}", importance = "MANDATORY", description = "", experienceGap = false, platformGap = false, courses = emptyList())
        }

        val criticalGapCount = gaps.count { it.importance == "MANDATORY" }

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

        val requirementAssessments = buildRequirementAssessments(
            validatedJob = validatedJob,
            inventory = diagnostics.inventory,
            gapAnalysis = gapAnalysis,
            matchedQualifications = matchedQualifications,
            missingQualifications = missingQualifications,
            qualificationsAssessed = qualificationsAssessed
        )
        val blockingUnassessed = requirementAssessments.any {
            it.state == RequirementAssessmentState.UNASSESSED && it.importance != "PREFERRED"
        }

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
            criticalGapCount = criticalGapCount,
            analysisSource = CompatibilityAnalysisSource.DETERMINISTIC_W5,
            evaluationComplete = scoreResult.isComplete && qualificationsAssessed && !blockingUnassessed,
            provisionalScore = provisionalScore,
            provisionalVacancyScore = provisionalVacancyScore,
            requirementAssessments = requirementAssessments
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
                "classificationWarningCount" to evidenceReport.diagnostics.size,
                "provisionalScore" to (record.provisionalScore ?: "unknown"),
                "provisionalVacancyScore" to (record.provisionalVacancyScore ?: "unknown"),
                "unassessedRequirementCount" to requirementAssessments.count {
                    it.state == RequirementAssessmentState.UNASSESSED
                }
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

    private val qualificationCategories = setOf(
        RequirementCategory.DEGREE,
        RequirementCategory.CERTIFICATION,
        RequirementCategory.LICENCE,
        RequirementCategory.SECURITY_CLEARANCE
    )

    private fun buildRequirementAssessments(
        validatedJob: ValidatedJob,
        inventory: List<RequirementEvidence>,
        gapAnalysis: GapAnalysis,
        matchedQualifications: List<RequirementEvidence>,
        missingQualifications: List<RequirementEvidence>,
        qualificationsAssessed: Boolean
    ): List<RequirementAssessment> {
        val source = if (inventory.isNotEmpty()) {
            inventory
        } else {
            synthesizeRequirementInventory(validatedJob)
        }
        val matchedSkills = gapAnalysis.matched.map { it.name }.toSet()
        val weakSkills = gapAnalysis.weak.toSet()
        val missingSkills = gapAnalysis.missing.toSet()

        return source.map { evidence ->
            when {
                evidence.category == RequirementCategory.SKILL ->
                    assessSkill(evidence, matchedSkills, weakSkills, missingSkills)
                evidence.category in qualificationCategories ->
                    assessQualification(
                        evidence,
                        matchedQualifications,
                        missingQualifications,
                        qualificationsAssessed
                    )
                evidence.classificationStatus == ClassificationStatus.UNMAPPED ->
                    unassessed(evidence, UnassessedReason.TAXONOMY_MISS)
                else ->
                    unassessed(evidence, UnassessedReason.UNSUPPORTED_CATEGORY)
            }
        }.distinctBy {
            listOf(it.text, it.category, it.canonical, it.importance)
        }
    }

    private fun assessSkill(
        evidence: RequirementEvidence,
        matchedSkills: Set<String>,
        weakSkills: Set<String>,
        missingSkills: Set<String>
    ): RequirementAssessment {
        val canonical = evidence.matchedTaxonomySkill
            ?: return unassessed(
                evidence,
                if (evidence.classificationStatus == ClassificationStatus.UNSUPPORTED) {
                    UnassessedReason.UNSUPPORTED_CATEGORY
                } else {
                    UnassessedReason.TAXONOMY_MISS
                }
            )

        if (evidence.importance == "PREFERRED") {
            return unassessed(evidence, UnassessedReason.PREFERENCE_NOT_ASSESSED)
        }

        val matched = canonical in matchedSkills || evidence.originalText in matchedSkills
        val weak = canonical in weakSkills || evidence.originalText in weakSkills
        val missing = canonical in missingSkills || evidence.originalText in missingSkills

        return when {
            matched -> RequirementAssessment(
                text = evidence.originalText,
                state = RequirementAssessmentState.MATCHED,
                canonical = canonical,
                category = evidence.category.name,
                importance = evidence.importance ?: "MANDATORY"
            )
            weak -> RequirementAssessment(
                text = evidence.originalText,
                state = RequirementAssessmentState.CONFIRMED_GAP,
                canonical = canonical,
                category = evidence.category.name,
                importance = "IMPORTANT"
            )
            missing -> RequirementAssessment(
                text = evidence.originalText,
                state = RequirementAssessmentState.CONFIRMED_GAP,
                canonical = canonical,
                category = evidence.category.name,
                importance = "MANDATORY"
            )
            else -> unassessed(evidence, UnassessedReason.PROFILE_SKILL_EVIDENCE_ABSENT)
        }
    }

    private fun assessQualification(
        evidence: RequirementEvidence,
        matchedQualifications: List<RequirementEvidence>,
        missingQualifications: List<RequirementEvidence>,
        qualificationsAssessed: Boolean
    ): RequirementAssessment {
        val canonical = "${evidence.category}: ${evidence.originalText}"
        val importance = evidence.importance ?: "MANDATORY"

        if (!qualificationsAssessed) {
            return RequirementAssessment(
                text = evidence.originalText,
                state = RequirementAssessmentState.UNASSESSED,
                canonical = canonical,
                reason = UnassessedReason.PROFILE_QUALIFICATION_EVIDENCE_ABSENT.name,
                category = evidence.category.name,
                importance = importance
            )
        }

        val matched = matchedQualifications.any {
            it.category == evidence.category && it.originalText == evidence.originalText
        }
        val missing = missingQualifications.any {
            it.category == evidence.category && it.originalText == evidence.originalText
        }

        return when {
            matched -> RequirementAssessment(
                text = evidence.originalText,
                state = RequirementAssessmentState.MATCHED,
                canonical = canonical,
                category = evidence.category.name,
                importance = importance
            )
            missing -> RequirementAssessment(
                text = evidence.originalText,
                state = RequirementAssessmentState.CONFIRMED_GAP,
                canonical = canonical,
                category = evidence.category.name,
                importance = "MANDATORY"
            )
            else -> RequirementAssessment(
                text = evidence.originalText,
                state = RequirementAssessmentState.UNASSESSED,
                canonical = canonical,
                reason = UnassessedReason.PROFILE_QUALIFICATION_EVIDENCE_ABSENT.name,
                category = evidence.category.name,
                importance = importance
            )
        }
    }

    private fun unassessed(
        evidence: RequirementEvidence,
        reason: UnassessedReason
    ): RequirementAssessment = RequirementAssessment(
        text = evidence.originalText,
        state = RequirementAssessmentState.UNASSESSED,
        canonical = evidence.matchedTaxonomySkill,
        reason = reason.name,
        category = evidence.category.name,
        importance = evidence.importance ?: "MANDATORY"
    )

    private fun synthesizeRequirementInventory(validatedJob: ValidatedJob): List<RequirementEvidence> =
        validatedJob.requiredSkills.map {
            RequirementEvidence(
                originalText = it,
                category = RequirementCategory.SKILL,
                classificationStatus = ClassificationStatus.NORMALIZED,
                matchedTaxonomySkill = it,
                importance = "MANDATORY"
            )
        } + validatedJob.requiredQualifications.map {
            RequirementEvidence(
                originalText = it.originalText,
                category = it.category,
                classificationStatus = it.classificationStatus,
                matchedTaxonomySkill = it.matchedTaxonomySkill,
                importance = it.importance ?: "MANDATORY"
            )
        }

}
