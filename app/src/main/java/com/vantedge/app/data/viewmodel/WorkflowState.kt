package com.vantedge.app.data.viewmodel

sealed class WorkflowState {
    object Loading : WorkflowState()
    object AnalysisOnly : WorkflowState()
    object ReadyForGeneration : WorkflowState()
    object Complete : WorkflowState()
}
