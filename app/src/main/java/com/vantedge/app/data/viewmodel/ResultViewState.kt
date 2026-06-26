package com.vantedge.app.data.viewmodel

data class ResultViewState(
    val cycleId: String? = null,
    val jobTitle: String = "",
    val company: String = "",
    val workflow: WorkflowState = WorkflowState.Loading,
    val isImprovementMode: Boolean = false,
    val actions: Set<ActionType> = emptySet(),
    val content: ContentState? = null,
    val isLoading: Boolean = false,
    val error: ResultError? = null
)
