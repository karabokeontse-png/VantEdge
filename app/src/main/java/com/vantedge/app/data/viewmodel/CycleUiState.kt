package com.vantedge.app.data.viewmodel

import com.vantedge.app.data.domain.GenerationOutcome
import com.vantedge.app.data.domain.PipelineStep
import com.vantedge.app.data.model.GenerationCycle
import com.vantedge.app.data.model.GenerationRequest

data class CycleUiState(
    val cvContent: String? = null,
    val coverLetterContent: String? = null,
    val status: GenerationStatus = GenerationStatus.SUCCESS
)

enum class GenerationStatus {
    SUCCESS,
    PARTIAL,
    FAILURE,
    INDETERMINATE
}

/** Mechanical translation of the authoritative generation outcome into UI status. */
internal fun GenerationOutcome.Status.toGenerationStatus(): GenerationStatus = when (this) {
    GenerationOutcome.Status.SUCCESS -> GenerationStatus.SUCCESS
    GenerationOutcome.Status.PARTIAL -> GenerationStatus.PARTIAL
    GenerationOutcome.Status.FAILURE -> GenerationStatus.FAILURE
    GenerationOutcome.Status.INDETERMINATE -> GenerationStatus.INDETERMINATE
}

/** Pure UI-state derivation; consumes the single-source outcome, never re-derives it. */
internal fun cycleUiStateOf(
    cycle: GenerationCycle,
    request: GenerationRequest = GenerationRequest.BOTH
): CycleUiState = CycleUiState(
    cvContent = cycle.cvContent,
    coverLetterContent = cycle.coverLetterContent,
    status = GenerationOutcome.of(cycle, request).toGenerationStatus()
)

sealed class UiState {
    data object Idle : UiState()
    data class Loading(val step: PipelineStep = PipelineStep.ANALYSING) : UiState()
    data class Content(val data: CycleUiState) : UiState()
    data class Error(val message: String) : UiState()
}
