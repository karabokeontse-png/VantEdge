package com.vantedge.app.data.viewmodel

import com.vantedge.app.data.domain.PipelineStep

data class CycleUiState(
    val cvContent: String? = null,
    val coverLetterContent: String? = null,
    val status: GenerationStatus = GenerationStatus.SUCCESS
)

enum class GenerationStatus {
    SUCCESS,
    PARTIAL,
    FAILURE
}

sealed class UiState {
    data object Idle : UiState()
    data class Loading(val step: PipelineStep = PipelineStep.ANALYSING) : UiState()
    data class Content(val data: CycleUiState) : UiState()
    data class Error(val message: String) : UiState()
}
