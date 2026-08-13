package com.vantedge.app.data.model

import com.vantedge.app.data.engine.ExtractionMode

data class OnboardingDraft(
    val stage: OnboardingStage = OnboardingStage.Welcome,
    val acquisitionMode: AcquisitionMode? = null,
    val uploadedCvUri: String? = null,
    val rawExtractedText: String? = null,
    val extractionMode: ExtractionMode? = null,
    @Transient val extraction: StructuredProfileExtraction? = null,
    val editedProfile: UserProfile? = null
)