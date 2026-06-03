package com.vantedge.app.data.model

/**
 * Immutable snapshot emitted exactly once at Gate 0 completion.
 *
 * Architectural contracts:
 * - Created by ProfileExtractionEngine; never mutated after construction.
 * - Has no setters, update methods, or copy-with-mutation helpers.
 * - Emitted for BOTH accepted and rejected documents (calibration requires both classes).
 * - [documentHash]: SHA-256(rawText, UTF-8, lowercase hex) — deduplication key.
 * - [sessionId]: ViewModel-scoped UUID; shared with UserDecisionEvent for
 *   cross-event correlation within a single onboarding session.
 * - [gate0Reason]: Gate0Reason.name — stored as String to avoid cross-module
 *   enum coupling in future persistence layers.
 * - [extractionMode]: ExtractionMode.name — provenance metadata only; never
 *   used in scoring or threshold evaluation.
 */
data class TelemetryRecord(
    val documentHash: String,
    val sessionId: String,
    val timestampMs: Long,
    val gate0Score: Int,
    val gate0Threshold: Int,
    val gate0Accepted: Boolean,
    val gate0Reason: String,
    val extractionMode: String
)
