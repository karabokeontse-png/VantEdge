package com.vantedge.app.data.viewmodel

sealed class OnboardingExtractionState {
    object Idle : OnboardingExtractionState()
    data class Extracting(val statusText: String) : OnboardingExtractionState()
    data class Retrying(val statusText: String) : OnboardingExtractionState()
    object Success : OnboardingExtractionState()
    data class Failure(val message: String) : OnboardingExtractionState()
}
