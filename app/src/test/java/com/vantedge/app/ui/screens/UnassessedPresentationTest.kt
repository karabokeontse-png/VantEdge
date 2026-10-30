package com.vantedge.app.ui.screens

import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.ProfileStats
import com.vantedge.app.data.model.RequirementAssessment
import com.vantedge.app.data.model.RequirementAssessmentState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class UnassessedPresentationTest {

    private fun record(assessments: List<RequirementAssessment>?) = CompatibilityRecord(
        jobTitle = "Developer",
        company = "Acme",
        jobDescription = "Developer",
        score = 80,
        roleSummary = "",
        eligibilitySummary = "",
        profileStats = ProfileStats(0, 0, 0, 0, 0, ""),
        relevancyItems = emptyList(),
        gaps = emptyList(),
        criticalGapCount = 0,
        evaluationComplete = false,
        requirementAssessments = assessments
    )

    private fun unassessed(text: String, reason: String? = "TAXONOMY_MISS") =
        RequirementAssessment(
            text = text,
            state = RequirementAssessmentState.UNASSESSED,
            reason = reason
        )

    @Test
    fun `legacy record has unknown coverage`() {
        val r = record(null)

        assertFalse(ScorePresentation.hasRecordedCoverage(r))
        assertNull(ScorePresentation.unassessedCount(r))
        assertTrue(ScorePresentation.unassessedRequirements(r).isEmpty())
    }

    @Test
    fun `recorded empty coverage is distinct from unknown coverage`() {
        val r = record(emptyList())

        assertTrue(ScorePresentation.hasRecordedCoverage(r))
        assertEquals(0, ScorePresentation.unassessedCount(r))
        assertTrue(ScorePresentation.unassessedRequirements(r).isEmpty())
    }

    @Test
    fun `unassessed requirements are counted and listed`() {
        val r = record(
            listOf(
                unassessed("Kubernetes", "PROFILE_SKILL_EVIDENCE_ABSENT"),
                RequirementAssessment(
                    text = "Kotlin",
                    state = RequirementAssessmentState.MATCHED,
                    canonical = "Kotlin"
                ),
                unassessed("Security clearance", "UNSUPPORTED_CATEGORY")
            )
        )

        assertEquals(2, ScorePresentation.unassessedCount(r))
        assertEquals(
            listOf("Kubernetes", "Security clearance"),
            ScorePresentation.unassessedRequirements(r).map { it.text }
        )
    }

    @Test
    fun `reason labels are stable and readable`() {
        assertEquals(
            "No skill evidence in your profile",
            ScorePresentation.unassessedReasonLabel("PROFILE_SKILL_EVIDENCE_ABSENT")
        )
        assertEquals(
            "No qualification evidence in your profile",
            ScorePresentation.unassessedReasonLabel("PROFILE_QUALIFICATION_EVIDENCE_ABSENT")
        )
        assertEquals(
            "Not in the assessed requirement list",
            ScorePresentation.unassessedReasonLabel("TAXONOMY_MISS")
        )
        assertEquals(
            "This requirement type is not assessed yet",
            ScorePresentation.unassessedReasonLabel("UNSUPPORTED_CATEGORY")
        )
        assertEquals(
            "Preferred item, not assessed",
            ScorePresentation.unassessedReasonLabel("PREFERENCE_NOT_ASSESSED")
        )
        assertEquals(
            "Could not be assessed",
            ScorePresentation.unassessedReasonLabel(null)
        )
    }

    @Test
    fun `coverage does not make an incomplete record authoritative`() {
        val r = record(listOf(unassessed("Kubernetes")))

        assertFalse(ScorePresentation.isAuthoritative(r))
        assertFalse(ScorePresentation.eligibilityClaimAllowed(r))
        assertEquals("—", ScorePresentation.scoreText(r))
    }
}
