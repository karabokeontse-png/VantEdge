package com.vantedge.app.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vantedge.app.data.engine.ProfileExtractionEngine
import com.vantedge.app.data.storage.OnboardingDraftStore
import com.vantedge.app.domain.OnboardingCommitService
import com.vantedge.app.util.TelemetryCollector

class OnboardingViewModelFactory(
    private val extractionEngine: ProfileExtractionEngine,
    private val commitService: OnboardingCommitService,
    private val draftStore: OnboardingDraftStore,
    private val telemetryCollector: TelemetryCollector
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnboardingViewModel::class.java)) {
            return OnboardingViewModel(
                extractionEngine = extractionEngine,
                commitService = commitService,
                draftStore = draftStore,
                telemetryCollector = telemetryCollector
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}