package com.vantedge.app.data.domain

import com.vantedge.app.data.model.UserProfile

/**
 * Single source of truth for onboarding profile eligibility.
 *
 * A profile is eligible (able to be reviewed, continued, committed, and to progress
 * to the dashboard) when it exists and has a nonblank name and a nonblank email.
 * Optional fields (skills, education, work history, certifications, summary, ...) never
 * affect eligibility.
 *
 * Deterministic and self-contained: no UI, navigation, Android, AI, or network
 * dependencies, so it can be used from both UI and ViewModel layers without a
 * domain-to-UI dependency.
 */
object ProfileEligibility {

    enum class Rejection { ABSENT_PROFILE, BLANK_NAME, BLANK_EMAIL }

    /** Returns null when [profile] is eligible, otherwise the first failing requirement. */
    fun rejectionReason(profile: UserProfile?): Rejection? = when {
        profile == null -> Rejection.ABSENT_PROFILE
        profile.name.isBlank() -> Rejection.BLANK_NAME
        profile.email.isBlank() -> Rejection.BLANK_EMAIL
        else -> null
    }

    fun isEligible(profile: UserProfile?): Boolean = rejectionReason(profile) == null
}
