package com.vantedge.app.data.model

/**
 * Append-only record of a user's action after reviewing extraction results.
 *
 * Architectural contracts:
 * - Keyed by ([documentHash], [sessionId]) — correlates to the originating [TelemetryRecord].
 * - Append-only: each decision creates a new event; existing TelemetryRecords are never mutated.
 * - Emitted by OnboardingViewModel post-review; never by ProfileExtractionEngine.
 * - [decisionType]: free-form String for flexibility across onboarding phases.
 *   Reserved values: "REVIEW_COMPLETED", "REJECTED", "EDITED_THEN_ACCEPTED".
 * - [timestampMs]: defaults to System.currentTimeMillis() at construction; callers
 *   may omit it for standard wall-clock stamping.
 */
data class UserDecisionEvent(
    val documentHash: String,
    val sessionId: String,
    val decisionType: String,
    val timestampMs: Long = System.currentTimeMillis()
)
