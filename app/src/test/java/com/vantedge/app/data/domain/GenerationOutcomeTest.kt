package com.vantedge.app.data.domain

import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.GenerationCycle
import com.vantedge.app.data.model.GenerationRequest
import com.vantedge.app.data.model.ProfileStats
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.viewmodel.GenerationStatus
import com.vantedge.app.data.viewmodel.cycleUiStateOf
import com.vantedge.app.data.viewmodel.toGenerationStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GenerationOutcomeTest {

    private fun cycle(
        cv: String? = null,
        cl: String? = null,
        cvError: String? = null,
        clError: String? = null,
        compatibility: CompatibilityRecord? = null
    ) = GenerationCycle(
        jobTitle = "Engineer",
        company = "Acme",
        jobDescription = "JD",
        profileSnapshot = UserProfile(name = "Jane", email = "jane@example.com"),
        compatibility = compatibility,
        cvContent = cv,
        coverLetterContent = cl,
        cvErrorMessage = cvError,
        coverLetterErrorMessage = clError
    )

    private fun highScoreRecord() = CompatibilityRecord(
        jobTitle = "Engineer", company = "Acme", jobDescription = "JD",
        score = 95, roleSummary = "role", eligibilitySummary = "fit",
        profileStats = ProfileStats(0, 0, 0, 0, 0, ""),
        relevancyItems = emptyList(), gaps = emptyList(), criticalGapCount = 0
    )

    // ── Both-requested aggregate (historical default) ───────────────────────────

    @Test fun bothArtifactsValidIsSuccess() {
        val c = cycle(cv = "{}", cl = "Dear Hiring Manager")
        assertEquals(GenerationOutcome.Status.SUCCESS, GenerationOutcome.of(c))
        assertTrue(GenerationOutcome.canNavigateToDesign(GenerationOutcome.of(c)))
    }

    @Test fun cvValidCoverLetterInvalidIsPartial() {
        val c = cycle(cv = "{}", cl = null, clError = "provider")
        assertEquals(GenerationOutcome.Status.PARTIAL, GenerationOutcome.of(c))
    }

    @Test fun coverLetterValidCvInvalidIsPartial() {
        val c = cycle(cv = "", cl = "Dear Hiring Manager", cvError = "provider")
        assertEquals(GenerationOutcome.Status.PARTIAL, GenerationOutcome.of(c))
    }

    @Test fun neitherArtifactValidIsFailure() {
        val c = cycle(cv = "", cl = null, cvError = "provider", clError = "provider")
        assertEquals(GenerationOutcome.Status.FAILURE, GenerationOutcome.of(c))
    }

    @Test fun emptyCvAndBlankCoverLetterAreInvalid() {
        val c = cycle(cv = "", cl = "   ", cvError = "p", clError = "p")
        assertEquals(GenerationOutcome.Artifact.INVALID, GenerationOutcome.cvArtifact(c))
        assertEquals(GenerationOutcome.Artifact.INVALID, GenerationOutcome.coverLetterArtifact(c))
        assertEquals(GenerationOutcome.Status.FAILURE, GenerationOutcome.of(c))
    }

    @Test fun successfulScoringCannotOverrideFailedGeneration() {
        val c = cycle(cv = "", cl = null, cvError = "provider", clError = "provider", compatibility = highScoreRecord())
        assertEquals(95, c.compatibility?.score)
        assertEquals(GenerationOutcome.Status.FAILURE, GenerationOutcome.of(c))
    }

    @Test fun historicalInsufficientEvidenceIsNotSuccess() {
        val c = cycle()
        assertEquals(GenerationOutcome.Status.INDETERMINATE, GenerationOutcome.of(c))
        assertFalse(GenerationOutcome.canNavigateToDesign(GenerationOutcome.of(c)))
        assertFalse(cycleUiStateOf(c).status == GenerationStatus.SUCCESS)
    }

    // ── Request-aware aggregate ─────────────────────────────────────────────────

    @Test fun cvOnlyRequestWithCvValidIsSuccessDespiteAbsentCoverLetter() {
        val c = cycle(cv = "{}", cl = null, clError = null)
        assertEquals(GenerationOutcome.Status.SUCCESS, GenerationOutcome.of(c, GenerationRequest.CV_ONLY))
    }

    @Test fun coverLetterOnlyRequestWithCoverLetterValidIsSuccessDespiteAbsentCv() {
        val c = cycle(cv = null, cvError = null, cl = "Dear Hiring Manager")
        assertEquals(GenerationOutcome.Status.SUCCESS, GenerationOutcome.of(c, GenerationRequest.COVER_LETTER_ONLY))
    }

    @Test fun cvOnlyRequestWithCvFailedIsFailure() {
        val c = cycle(cv = "", cvError = "provider", cl = null)
        assertEquals(GenerationOutcome.Status.FAILURE, GenerationOutcome.of(c, GenerationRequest.CV_ONLY))
    }

    @Test fun coverLetterOnlyRequestWithCoverLetterFailedIsFailure() {
        val c = cycle(cv = null, cl = "", clError = "provider")
        assertEquals(GenerationOutcome.Status.FAILURE, GenerationOutcome.of(c, GenerationRequest.COVER_LETTER_ONLY))
    }

    @Test fun bothRequestedWithOneFailureIsPartial() {
        val c = cycle(cv = "{}", cl = "", clError = "provider")
        assertEquals(GenerationOutcome.Status.PARTIAL, GenerationOutcome.of(c, GenerationRequest.BOTH))
    }

    // ── Per-artifact accessibility independent of aggregate ─────────────────────

    @Test fun validCvIsAccessibleWhenAggregateIsPartial() {
        val c = cycle(cv = "{}", cl = "", clError = "provider")
        assertTrue(GenerationOutcome.cvAccessible(c))
        assertFalse(GenerationOutcome.coverLetterAccessible(c))
    }

    @Test fun validCoverLetterIsAccessibleWhenCvFailed() {
        val c = cycle(cv = "", cvError = "provider", cl = "Dear Hiring Manager")
        assertFalse(GenerationOutcome.cvAccessible(c))
        assertTrue(GenerationOutcome.coverLetterAccessible(c))
    }

    @Test fun invalidArtifactsAreNotAccessible() {
        val c = cycle(cv = "", cvError = "p", cl = "", clError = "p")
        assertFalse(GenerationOutcome.cvAccessible(c))
        assertFalse(GenerationOutcome.coverLetterAccessible(c))
    }

    // ── Agreement of outcome, trace, and UI state ───────────────────────────────

    @Test fun outcomeTraceAndUiStateAgree() {
        val c = cycle(cv = "{}", cl = null, clError = "provider")
        val outcome = GenerationOutcome.of(c)
        assertEquals(GenerationOutcome.Status.PARTIAL, outcome)
        assertEquals("partial", GenerationOutcome.traceStatus(outcome))
        val ui = cycleUiStateOf(c)
        assertEquals(GenerationStatus.PARTIAL, ui.status)
        assertEquals(outcome.toGenerationStatus(), ui.status)
    }

    // ── Request-aware routing decision (Amendment B contract) ───────────────────

    @Test fun routingCvOnlyValidCvGoesToCvDesign() {
        val c = cycle(cv = "{}")
        assertEquals(
            setOf(GenerationOutcome.Destination.CV_DESIGN),
            GenerationOutcome.routingFor(c, GenerationRequest.CV_ONLY)
        )
    }

    @Test fun routingCoverLetterOnlyValidLetterGoesToCoverLetterDesign() {
        val c = cycle(cl = "Dear Hiring Manager")
        assertEquals(
            setOf(GenerationOutcome.Destination.COVER_LETTER_DESIGN),
            GenerationOutcome.routingFor(c, GenerationRequest.COVER_LETTER_ONLY)
        )
    }

    @Test fun routingBothValidKeepsBothDestinationsAccessible() {
        val c = cycle(cv = "{}", cl = "Dear Hiring Manager")
        assertEquals(
            setOf(GenerationOutcome.Destination.CV_DESIGN, GenerationOutcome.Destination.COVER_LETTER_DESIGN),
            GenerationOutcome.routingFor(c, GenerationRequest.BOTH)
        )
    }

    @Test fun routingBothWithOnlyValidCvGoesToCvDesign() {
        val c = cycle(cv = "{}", cl = "", clError = "provider")
        assertEquals(
            setOf(GenerationOutcome.Destination.CV_DESIGN),
            GenerationOutcome.routingFor(c, GenerationRequest.BOTH)
        )
    }

    @Test fun routingBothWithOnlyValidLetterGoesToCoverLetterDesign() {
        val c = cycle(cv = "", cvError = "provider", cl = "Dear Hiring Manager")
        assertEquals(
            setOf(GenerationOutcome.Destination.COVER_LETTER_DESIGN),
            GenerationOutcome.routingFor(c, GenerationRequest.BOTH)
        )
    }

    @Test fun routingWithNoValidRequestedArtifactHasNoDestination() {
        val c = cycle(cv = "", cvError = "p", cl = "", clError = "p")
        assertTrue(GenerationOutcome.routingFor(c, GenerationRequest.BOTH).isEmpty())
        assertTrue(GenerationOutcome.routingFor(c, GenerationRequest.CV_ONLY).isEmpty())
        assertTrue(GenerationOutcome.routingFor(c, GenerationRequest.COVER_LETTER_ONLY).isEmpty())
    }

    @Test fun navigationEligibilityIsPureAndBlocksNonSuccess() {
        assertTrue(GenerationOutcome.canNavigateToDesign(GenerationOutcome.Status.SUCCESS))
        assertFalse(GenerationOutcome.canNavigateToDesign(GenerationOutcome.Status.PARTIAL))
        assertFalse(GenerationOutcome.canNavigateToDesign(GenerationOutcome.Status.FAILURE))
        assertFalse(GenerationOutcome.canNavigateToDesign(GenerationOutcome.Status.INDETERMINATE))
    }
}
