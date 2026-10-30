package com.vantedge.app.ui.screens

import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.GapItem
import com.vantedge.app.data.model.ProfileStats
import com.vantedge.app.data.model.RequirementAssessment
import com.vantedge.app.data.model.RequirementAssessmentState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ScorePresentationTest {

    private fun gap(skill: String) = GapItem(
        skill = skill,
        importance = "MANDATORY",
        description = "",
        courses = emptyList()
    )

    private fun record(score: Int, complete: Boolean?) = CompatibilityRecord(
        jobTitle = "Developer",
        company = "Acme",
        jobDescription = "Developer",
        score = score,
        roleSummary = "",
        eligibilitySummary = "",
        profileStats = ProfileStats(
            yearsExperience = 0,
            certificationCount = 0,
            skillCount = 0,
            matchedCount = 0,
            gapCount = 0,
            dataIntegrityNote = ""
        ),
        relevancyItems = emptyList(),
        gaps = emptyList(),
        criticalGapCount = 0,
        evaluationComplete = complete
    )

    @Test
    fun `complete record presents the score`() {
        val r = record(score = 85, complete = true)

        assertTrue(ScorePresentation.isAuthoritative(r))
        assertTrue(ScorePresentation.eligibilityClaimAllowed(r))
        assertEquals("85%", ScorePresentation.scoreText(r))
        assertEquals("Strong match", ScorePresentation.scoreLabel(r))
        assertEquals(229.5f, ScorePresentation.sweepDegrees(r), 1e-6f)
    }

    @Test
    fun `incomplete live record hides the score`() {
        val r = record(score = 85, complete = false)

        assertFalse(ScorePresentation.isAuthoritative(r))
        assertFalse(ScorePresentation.eligibilityClaimAllowed(r))
        assertEquals(SkillsMatchEmptyState.INCOMPLETE, ScorePresentation.skillsMatchEmptyState(r))
        assertEquals("—", ScorePresentation.scoreText(r))
        assertEquals("Incomplete evaluation", ScorePresentation.scoreLabel(r))
        assertEquals(0f, ScorePresentation.sweepDegrees(r), 1e-6f)
    }

    @Test
    fun `legacy record with null completeness hides the score`() {
        val r = record(score = 85, complete = null)

        assertFalse(ScorePresentation.isAuthoritative(r))
        assertFalse(ScorePresentation.eligibilityClaimAllowed(r))
        assertEquals(SkillsMatchEmptyState.INCOMPLETE, ScorePresentation.skillsMatchEmptyState(r))
        assertEquals("—", ScorePresentation.scoreText(r))
        assertEquals("Incomplete evaluation", ScorePresentation.scoreLabel(r))
        assertEquals(0f, ScorePresentation.sweepDegrees(r), 1e-6f)
    }

    @Test
    fun `complete record presents the eligibility summary`() {
        val r = record(score = 85, complete = true).copy(
            eligibilitySummary = "You meet the listed requirements."
        )

        assertTrue(ScorePresentation.eligibilityClaimAllowed(r))
        assertEquals("How you fit", ScorePresentation.eligibilityHeading(r))
        assertEquals("You meet the listed requirements.", ScorePresentation.eligibilityBody(r))
        assertEquals(SkillsMatchEmptyState.EXCEED, ScorePresentation.skillsMatchEmptyState(r))
        assertEquals(SkillsMatchEmptyState.MEET, ScorePresentation.skillsMatchEmptyState(r.copy(score = 75)))
    }

    @Test
    fun `incomplete record does not present eligibility as definitive`() {
        val r = record(score = 85, complete = false).copy(
            eligibilitySummary = "You meet all requirements."
        )

        assertFalse(ScorePresentation.eligibilityClaimAllowed(r))
        assertEquals("How you fit — not fully assessed", ScorePresentation.eligibilityHeading(r))
        assertFalse(ScorePresentation.eligibilityBody(r).contains("You meet all requirements."))
        assertTrue(ScorePresentation.eligibilityBody(r).contains("not definitive"))
        assertEquals(SkillsMatchEmptyState.INCOMPLETE, ScorePresentation.skillsMatchEmptyState(r))
    }

    @Test
    fun `legacy null completeness does not present eligibility as definitive`() {
        val r = record(score = 85, complete = null).copy(
            eligibilitySummary = "You meet all requirements."
        )

        assertFalse(ScorePresentation.eligibilityClaimAllowed(r))
        assertFalse(ScorePresentation.eligibilityBody(r).contains("You meet all requirements."))
        assertTrue(ScorePresentation.eligibilityBody(r).contains("not definitive"))
        assertEquals(SkillsMatchEmptyState.INCOMPLETE, ScorePresentation.skillsMatchEmptyState(r))
    }

    @Test
    fun `unassessed requirements do not allow an authoritative positive claim`() {
        val r = record(score = 85, complete = false).copy(
            eligibilitySummary = "You meet all requirements."
        )

        assertFalse(ScorePresentation.eligibilityClaimAllowed(r))
        assertFalse(ScorePresentation.eligibilityBody(r).contains("You meet all requirements."))
        assertTrue(ScorePresentation.eligibilityBody(r).contains("not definitive"))
        assertEquals(SkillsMatchEmptyState.INCOMPLETE, ScorePresentation.skillsMatchEmptyState(r))
    }

    @Test
    fun `complete record with confirmed gaps does not allow an eligibility claim`() {
        val r = record(score = 85, complete = true).copy(
            gaps = listOf(gap("Kubernetes")),
            eligibilitySummary = "You meet all requirements."
        )

        assertFalse(ScorePresentation.eligibilityClaimAllowed(r))
        assertEquals("How you fit — confirmed gaps", ScorePresentation.eligibilityHeading(r))
        assertTrue(ScorePresentation.eligibilityBody(r).contains("Confirmed gaps"))
        assertFalse(ScorePresentation.eligibilityBody(r).contains("You meet all requirements."))
        assertEquals(SkillsMatchEmptyState.CONFIRMED_GAPS, ScorePresentation.skillsMatchEmptyState(r))
    }

    @Test
    fun `unsupported AI claim is suppressed when gaps remain`() {
        val r = record(score = 85, complete = true).copy(
            gaps = listOf(gap("Kubernetes")),
            eligibilitySummary = "You meet all requirements."
        )

        assertFalse(ScorePresentation.eligibilityBody(r).contains("You meet all requirements."))
        assertTrue(ScorePresentation.eligibilityBody(r).contains("Confirmed gaps"))
    }

    @Test
    fun `alternative positive AI claim is suppressed when gaps remain`() {
        val r = record(score = 85, complete = true).copy(
            gaps = listOf(gap("Kubernetes")),
            eligibilitySummary = "You are fully qualified for this role."
        )

        assertFalse(ScorePresentation.eligibilityBody(r).contains("fully qualified"))
        assertTrue(ScorePresentation.eligibilityBody(r).contains("Confirmed gaps"))
    }

    @Test
    fun `confirmed gaps suppress both Skills Match positive branches`() {
        val highScore = record(score = 85, complete = true).copy(gaps = listOf(gap("Kubernetes")))
        val lowScore = record(score = 75, complete = true).copy(gaps = listOf(gap("Kubernetes")))

        assertEquals(SkillsMatchEmptyState.CONFIRMED_GAPS, ScorePresentation.skillsMatchEmptyState(highScore))
        assertEquals(SkillsMatchEmptyState.CONFIRMED_GAPS, ScorePresentation.skillsMatchEmptyState(lowScore))
    }

    @Test
    fun `history score text hides null and incomplete records`() {
        assertEquals("Incomplete evaluation", ScorePresentation.historyScoreText(null))
        assertEquals("Incomplete evaluation", ScorePresentation.historyScoreText(record(85, false)))
        assertEquals("Incomplete evaluation", ScorePresentation.historyScoreText(record(85, null)))
        assertEquals("85%", ScorePresentation.historyScoreText(record(85, true)))
    }
    @Test
    fun `coverage helpers distinguish legacy from recorded inventories`() {
        val legacy = record(score = 85, complete = false)
        assertFalse(ScorePresentation.hasRecordedCoverage(legacy))
        assertEquals(null, ScorePresentation.unassessedCount(legacy))

        val recorded = legacy.copy(
            requirementAssessments = listOf(
                RequirementAssessment(
                    text = "Kotlin",
                    state = RequirementAssessmentState.UNASSESSED,
                    reason = "TAXONOMY_MISS"
                )
            )
        )
        assertTrue(ScorePresentation.hasRecordedCoverage(recorded))
        assertEquals(1, ScorePresentation.unassessedCount(recorded))
        assertEquals(listOf("Kotlin"), ScorePresentation.unassessedRequirements(recorded).map { it.text })
    }

}
