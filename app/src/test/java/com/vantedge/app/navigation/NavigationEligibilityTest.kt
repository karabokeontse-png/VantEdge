package com.vantedge.app.navigation

import com.vantedge.app.data.domain.ProfileEligibility
import com.vantedge.app.data.model.OnboardingDraft
import com.vantedge.app.data.model.OnboardingStage
import com.vantedge.app.data.model.UserProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationEligibilityTest {

    private fun finalReviewDraft(
        name: String,
        email: String,
        skills: List<String>
    ) = OnboardingDraft(
        stage = OnboardingStage.FinalReview,
        editedProfile = UserProfile(name = name, email = email, skills = skills)
    )

    @Test
    fun finalReviewWithEmptySkillsIsEligible() {
        assertTrue(
            canEnter(
                OnboardingStage.FinalReview,
                finalReviewDraft("Jane Doe", "jane@example.com", emptyList())
            )
        )
    }

    @Test
    fun finalReviewWithSkillsIsEligible() {
        assertTrue(
            canEnter(
                OnboardingStage.FinalReview,
                finalReviewDraft("Jane Doe", "jane@example.com", listOf("Kotlin"))
            )
        )
    }

    @Test
    fun finalReviewWithoutProfileIsNotEligible() {
        assertFalse(
            canEnter(
                OnboardingStage.FinalReview,
                OnboardingDraft(stage = OnboardingStage.FinalReview)
            )
        )
    }

    @Test
    fun finalReviewWithBlankNameIsNotEligible() {
        assertFalse(
            canEnter(
                OnboardingStage.FinalReview,
                finalReviewDraft("", "jane@example.com", listOf("Kotlin"))
            )
        )
    }

    @Test
    fun finalReviewWithBlankEmailIsNotEligible() {
        assertFalse(
            canEnter(
                OnboardingStage.FinalReview,
                finalReviewDraft("Jane Doe", "   ", listOf("Kotlin"))
            )
        )
    }

    @Test
    fun finalReviewEligibilityMirrorsSharedPredicate() {
        val drafts = listOf(
            OnboardingDraft(stage = OnboardingStage.FinalReview),
            finalReviewDraft("", "jane@example.com", emptyList()),
            finalReviewDraft("Jane Doe", "", emptyList()),
            finalReviewDraft("Jane Doe", "jane@example.com", emptyList()),
            finalReviewDraft("Jane Doe", "jane@example.com", listOf("Kotlin"))
        )
        drafts.forEach { draft ->
            assertEquals(
                ProfileEligibility.isEligible(draft.editedProfile),
                canEnter(OnboardingStage.FinalReview, draft)
            )
        }
    }
}
