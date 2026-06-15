package com.vantedge.app.data.viewmodel

import com.vantedge.app.data.model.JobExtractionResult

sealed class JobExtractionState {
    object Idle : JobExtractionState()
    data class Extracting(val statusText: String) : JobExtractionState()
    data class Success(val result: JobExtractionResult) : JobExtractionState()
    data class Failure(val message: String) : JobExtractionState()
}
