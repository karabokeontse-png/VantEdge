package com.vantedge.app.data.model

data class OnboardingDraft(
    val stage: OnboardingStage = OnboardingStage.Welcome,
    val acquisitionMode: AcquisitionMode? = null,
    val uploadedCvUri: String? = null,
    val rawExtractedText: String? = null,
    @Transient val extraction: StructuredProfileExtraction? = null,
    val editedProfile: UserProfile? = null
)