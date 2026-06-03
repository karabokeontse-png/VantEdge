package com.vantedge.app.data.viewmodel

sealed class GeneratorUiState {
    object Idle : GeneratorUiState()
    object Loading : GeneratorUiState()
    data class Success(val cv: String, val coverLetter: String) : GeneratorUiState()
    data class Error(val message: String) : GeneratorUiState()
}