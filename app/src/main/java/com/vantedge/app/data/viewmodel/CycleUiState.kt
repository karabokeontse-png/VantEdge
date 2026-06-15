package com.vantedge.app.data.viewmodel

import com.vantedge.app.data.domain.PipelineStep
import com.vantedge.app.data.model.GenerationCycle

sealed class CycleUiState {

    data object Idle : CycleUiState()

    data class Loading(val step: PipelineStep = PipelineStep.ANALYSING) : CycleUiState()

    data class AnalysisDone(val cycle: GenerationCycle) : CycleUiState()

    data class GenerationReady(val cycle: GenerationCycle) : CycleUiState()

    data class Success(val cycle: GenerationCycle) : CycleUiState()

    data class Error(val message: String) : CycleUiState()
}
