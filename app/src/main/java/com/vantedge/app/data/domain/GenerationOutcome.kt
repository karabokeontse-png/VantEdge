package com.vantedge.app.data.domain

import com.vantedge.app.data.model.GenerationCycle
import com.vantedge.app.data.model.GenerationRequest

/**
 * Single source of truth for generation outcome.
 *
 * Derived deterministically from existing persisted fields only
 * ([GenerationCycle.cvContent], [GenerationCycle.coverLetterContent] and the matching
 * error fields). No schema change and no new persisted field.
 *
 * Artifact validity:
 *  - content present and non-blank                 -> VALID
 *  - content missing/blank with a recorded error    -> INVALID (known failure)
 *  - content missing/blank with no recorded error   -> INDETERMINATE (historical or
 *    not-attempted; never treated as success)
 *
 * The aggregate [Status] is request-aware: only the requested artifacts participate.
 * An artifact that was not requested (blank content, no error) is ignored rather than
 * being treated as INDETERMINATE, so a live CV-only or cover-letter-only run is never
 * concealed as historical uncertainty.
 */
object GenerationOutcome {

    enum class Artifact { VALID, INVALID, INDETERMINATE }

    enum class Status { SUCCESS, PARTIAL, FAILURE, INDETERMINATE }

    fun cvArtifact(cycle: GenerationCycle): Artifact =
        classify(cycle.cvContent, cycle.cvErrorMessage)

    fun coverLetterArtifact(cycle: GenerationCycle): Artifact =
        classify(cycle.coverLetterContent, cycle.coverLetterErrorMessage)

    fun of(cycle: GenerationCycle): Status = of(cycle, GenerationRequest.BOTH)

    fun of(cycle: GenerationCycle, request: GenerationRequest): Status =
        of(cycle.cvContent, cycle.coverLetterContent, cycle.cvErrorMessage, cycle.coverLetterErrorMessage, request)

    fun of(
        cvContent: String?,
        coverLetterContent: String?,
        cvError: String?,
        coverLetterError: String?
    ): Status = of(cvContent, coverLetterContent, cvError, coverLetterError, GenerationRequest.BOTH)

    fun of(
        cvContent: String?,
        coverLetterContent: String?,
        cvError: String?,
        coverLetterError: String?,
        request: GenerationRequest
    ): Status = of(classify(cvContent, cvError), classify(coverLetterContent, coverLetterError), request)

    fun of(cv: Artifact, coverLetter: Artifact): Status = of(cv, coverLetter, GenerationRequest.BOTH)

    fun of(cv: Artifact, coverLetter: Artifact, request: GenerationRequest): Status {
        val requested = when (request) {
            GenerationRequest.CV_ONLY -> listOf(cv)
            GenerationRequest.COVER_LETTER_ONLY -> listOf(coverLetter)
            GenerationRequest.BOTH -> listOf(cv, coverLetter)
        }
        if (requested.any { it == Artifact.INDETERMINATE }) return Status.INDETERMINATE
        val valid = requested.count { it == Artifact.VALID }
        return when {
            valid == requested.size -> Status.SUCCESS
            valid == 0 -> Status.FAILURE
            else -> Status.PARTIAL
        }
    }

    private fun classify(content: String?, error: String?): Artifact = when {
        !content.isNullOrBlank() -> Artifact.VALID
        !error.isNullOrBlank() -> Artifact.INVALID
        else -> Artifact.INDETERMINATE
    }

    /** Stable, lowercase trace label for [status]. */
    fun traceStatus(status: Status): String = status.name.lowercase()

    // ── Per-artifact accessibility (independent of the aggregate status) ─────────
    // A valid artifact may be accessed at its existing destination even when the
    // aggregate outcome is PARTIAL/INDETERMINATE. Accessibility never erases another
    // requested artifact's failure.

    fun cvAccessible(cycle: GenerationCycle): Boolean = cvArtifact(cycle) == Artifact.VALID

    fun coverLetterAccessible(cycle: GenerationCycle): Boolean =
        coverLetterArtifact(cycle) == Artifact.VALID

    /**
     * Historical convenience gate retained for callers that must know whether the whole
     * requested operation completed. Navigation must use the per-artifact accessors above,
     * never this aggregate gate.
     */
    fun canNavigateToDesign(status: Status): Boolean = status == Status.SUCCESS

    // ── Request-aware routing decision ──────────────────────────────────────────

    enum class Destination { CV_DESIGN, COVER_LETTER_DESIGN }

    /**
     * Pure, deterministic, request-aware routing decision. Returns the set of
     * destinations that must remain accessible for the requested artifacts.
     * Empty means no valid requested artifact (no success navigation).
     */
    fun routingFor(cycle: GenerationCycle, request: GenerationRequest): Set<Destination> = when (request) {
        GenerationRequest.CV_ONLY ->
            if (cvAccessible(cycle)) setOf(Destination.CV_DESIGN) else emptySet()
        GenerationRequest.COVER_LETTER_ONLY ->
            if (coverLetterAccessible(cycle)) setOf(Destination.COVER_LETTER_DESIGN) else emptySet()
        GenerationRequest.BOTH -> buildSet {
            if (cvAccessible(cycle)) add(Destination.CV_DESIGN)
            if (coverLetterAccessible(cycle)) add(Destination.COVER_LETTER_DESIGN)
        }
    }
}
