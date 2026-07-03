package com.vantedge.app.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vantedge.app.data.domain.CompatibilityOrchestrator
import com.vantedge.app.data.engine.CompatibilityResult
import com.vantedge.app.domain.extraction.JobExtractionOrchestrator
import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.JobSourceType
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.model.Certification
import com.vantedge.app.data.storage.CompatibilityStore
import com.vantedge.app.data.storage.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class CompatibilityUiState {
    object Idle : CompatibilityUiState()
    object Loading : CompatibilityUiState()
    data class Success(val record: CompatibilityRecord) : CompatibilityUiState()
    data class Error(val message: String) : CompatibilityUiState()
}

class CompatibilityViewModel(
    private val store: CompatibilityStore,
    private val userPreferences: UserPreferences,
    private val optimizationOrchestrator: CompatibilityOrchestrator,
    private val extractionOrchestrator: JobExtractionOrchestrator
) : ViewModel() {

    private val _uiState = MutableStateFlow<CompatibilityUiState>(CompatibilityUiState.Idle)
    val uiState: StateFlow<CompatibilityUiState> = _uiState

    val historyFlow: StateFlow<List<CompatibilityRecord>> = store.historyFlow

    var lastRecord: CompatibilityRecord? = null

    var savedJobTitle: String = ""
    var savedCompany: String = ""
    var savedJobDescription: String = ""

    private val _addedSkills = MutableStateFlow<Set<String>>(emptySet())
    val addedSkills: StateFlow<Set<String>> = _addedSkills

    private val _addedCerts = MutableStateFlow<Set<String>>(emptySet())
    val addedCerts: StateFlow<Set<String>> = _addedCerts

    fun resetState() {
        _uiState.value = CompatibilityUiState.Idle
    }

    fun analyze(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        jobDescription: String
    ) {
        _uiState.value = CompatibilityUiState.Loading
        _addedSkills.value = emptySet()
        _addedCerts.value = emptySet()

        viewModelScope.launch(Dispatchers.IO) {
            when (val result = optimizationOrchestrator.runAnalysisFresh(
                profile = profile,
                jobTitle = jobTitle,
                company = company,
                jobDescription = jobDescription
            )) {
                is CompatibilityResult.Failure -> {
                    viewModelScope.launch(Dispatchers.Main) {
                        _uiState.value =
                            CompatibilityUiState.Error("Analysis failed. Check your connection.")
                    }
                }
                is CompatibilityResult.Success -> {
                    val record = result.data
                    lastRecord = record

                    viewModelScope.launch(Dispatchers.IO) {
                        store.addRecord(record)
                    }

                    viewModelScope.launch(Dispatchers.Main) {
                        _uiState.value = CompatibilityUiState.Success(record)
                    }
                }
            }
        }
    }

    fun addSkillToProfile(skill: String, currentProfile: UserProfile) {
        viewModelScope.launch(Dispatchers.IO) {
            val trimmed = skill.trim()
            if (trimmed.isBlank()) return@launch

            val exists = currentProfile.skills.any { it.equals(trimmed, true) }

            if (exists) {
                withContext(Dispatchers.Main) {
                    _addedSkills.value = _addedSkills.value + trimmed
                }
                return@launch
            }

            val updated = currentProfile.copy(
                skills = currentProfile.skills + trimmed
            )

            userPreferences.saveProfile(updated)

            withContext(Dispatchers.Main) {
                _addedSkills.value = _addedSkills.value + trimmed
            }
        }
    }

    fun addCertToProfile(certName: String, currentProfile: UserProfile) {
        viewModelScope.launch(Dispatchers.IO) {
            val trimmed = certName.trim()
            if (trimmed.isBlank()) return@launch

            val exists = currentProfile.certifications.any {
                it.name.equals(trimmed, true)
            }

            if (exists) {
                withContext(Dispatchers.Main) {
                    _addedCerts.value = _addedCerts.value + trimmed
                }
                return@launch
            }

            val newCert = Certification(
                name = trimmed
            )

            val updated = currentProfile.copy(
                certifications = currentProfile.certifications + newCert
            )

            userPreferences.saveProfile(updated)

            withContext(Dispatchers.Main) {
                _addedCerts.value = _addedCerts.value + trimmed
            }
        }
    }

    fun delete(id: Long) {
        store.deleteRecord(id)
    }

    fun extractFieldsFromText(
        rawText: String,
        onResult: (jobTitle: String?, company: String?, jobDescription: String?) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = extractionOrchestrator.extractJob(rawText, JobSourceType.USER_INPUT)
            result.onSuccess { extraction ->
                viewModelScope.launch(Dispatchers.Main) {
                    onResult(extraction.jobTitle, extraction.company, extraction.description)
                }
            }.onFailure {
                viewModelScope.launch(Dispatchers.Main) {
                    onResult(null, null, rawText.take(3000))
                }
            }
        }
    }
}
