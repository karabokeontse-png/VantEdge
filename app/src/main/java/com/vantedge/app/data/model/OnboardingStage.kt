package com.vantedge.app.data.model

sealed class OnboardingStage {
    object Welcome : OnboardingStage()
    object ChoosePath : OnboardingStage()
    object UploadingCv : OnboardingStage()
    object Extracting : OnboardingStage()
    object ReviewingExtraction : OnboardingStage()
    object EditingProfile : OnboardingStage()
    object FinalReview : OnboardingStage()
    object Completed : OnboardingStage()
}