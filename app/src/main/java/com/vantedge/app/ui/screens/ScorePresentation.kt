package com.vantedge.app.ui.screens

import com.vantedge.app.data.domain.ScoreCompleteness
import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.RequirementAssessment
import com.vantedge.app.data.model.RequirementAssessmentState

enum class SkillsMatchEmptyState {
    INCOMPLETE,
    CONFIRMED_GAPS,
    EXCEED,
    MEET
}

object ScorePresentation {

    fun isAuthoritative(record: CompatibilityRecord): Boolean =
        ScoreCompleteness.isAuthoritative(record)

    fun eligibilityClaimAllowed(record: CompatibilityRecord): Boolean =
        isAuthoritative(record) && record.gaps.isEmpty()

    fun scoreText(record: CompatibilityRecord): String =
        if (isAuthoritative(record)) "${record.score}%" else "—"

    fun scoreLabel(record: CompatibilityRecord): String {
        if (!isAuthoritative(record)) return "Incomplete evaluation"
        return when {
            record.score >= 80 -> "Strong match"
            record.score >= 60 -> "Decent match"
            record.score >= 40 -> "Partial match"
            else -> "Weak match"
        }
    }

    fun sweepDegrees(record: CompatibilityRecord): Float =
        if (isAuthoritative(record)) (record.score / 100f) * 270f else 0f

    fun historyScoreText(record: CompatibilityRecord?): String =
        if (record != null && isAuthoritative(record)) "${record.score}%" else "Incomplete evaluation"

    fun eligibilityHeading(record: CompatibilityRecord): String = when {
        !isAuthoritative(record) -> "How you fit — not fully assessed"
        eligibilityClaimAllowed(record) -> "How you fit"
        else -> "How you fit — confirmed gaps"
    }

    fun eligibilityBody(record: CompatibilityRecord): String = when {
        !isAuthoritative(record) ->
            "Eligibility is not definitive because the evaluation is incomplete. Confirmed gaps are listed separately."
        eligibilityClaimAllowed(record) -> record.eligibilitySummary
        else ->
            "Confirmed gaps remain for this role. Review the gap analysis before treating this as a full eligibility result."
    }

    fun skillsMatchEmptyState(record: CompatibilityRecord): SkillsMatchEmptyState = when {
        !isAuthoritative(record) -> SkillsMatchEmptyState.INCOMPLETE
        !eligibilityClaimAllowed(record) -> SkillsMatchEmptyState.CONFIRMED_GAPS
        record.score >= 80 -> SkillsMatchEmptyState.EXCEED
        else -> SkillsMatchEmptyState.MEET
    }

    fun hasRecordedCoverage(record: CompatibilityRecord): Boolean =
        record.requirementAssessments != null

    fun unassessedCount(record: CompatibilityRecord): Int? =
        record.requirementAssessments?.count { it.state == RequirementAssessmentState.UNASSESSED }

    fun unassessedRequirements(record: CompatibilityRecord): List<RequirementAssessment> =
        record.requirementAssessments
            ?.filter { it.state == RequirementAssessmentState.UNASSESSED }
            ?: emptyList()

    fun unassessedReasonLabel(reason: String?): String = when (reason) {
        "PROFILE_SKILL_EVIDENCE_ABSENT" -> "No skill evidence in your profile"
        "PROFILE_QUALIFICATION_EVIDENCE_ABSENT" -> "No qualification evidence in your profile"
        "TAXONOMY_MISS" -> "Not in the assessed requirement list"
        "UNSUPPORTED_CATEGORY" -> "This requirement type is not assessed yet"
        "PREFERENCE_NOT_ASSESSED" -> "Preferred item, not assessed"
        else -> "Could not be assessed"
    }
}
