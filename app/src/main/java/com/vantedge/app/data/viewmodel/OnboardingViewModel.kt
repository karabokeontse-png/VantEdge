package com.vantedge.app.data.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vantedge.app.data.engine.ExtractionMode
import com.vantedge.app.data.engine.ProfileExtractionEngine
import com.vantedge.app.data.model.*
import com.vantedge.app.data.storage.OnboardingDraftStore
import com.vantedge.app.domain.OnboardingCommitService
import com.vantedge.app.util.HashUtils
import com.vantedge.app.util.TelemetryCollector
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID
import java.util.concurrent.atomic.AtomicBoolean

sealed class ExtractionState {
    object Idle : ExtractionState()
    data class Extracting(val statusText: String) : ExtractionState()
    data class Retrying(val statusText: String) : ExtractionState()
    object Success : ExtractionState()
    data class Failure(val message: String) : ExtractionState()
}

class OnboardingViewModel(
    private val extractionEngine: ProfileExtractionEngine,
    private val commitService: OnboardingCommitService,
    private val draftStore: OnboardingDraftStore,
    private val telemetryCollector: TelemetryCollector
) : ViewModel() {

    companion object {
        private const val TAG = "OnboardingViewModel"
    }

    private val extractionMutex = Mutex()
    private val draftMutex = Mutex()

    private val isCommitting = AtomicBoolean(false)
    
    // Unique session ID generated per ViewModel lifecycle instantiation for telemetry segmentation
    private val sessionId: String = UUID.randomUUID().toString()

    private val _draft = MutableStateFlow(OnboardingDraft())
    val draft: StateFlow<OnboardingDraft> = _draft.asStateFlow()

    private val _extractionState = MutableStateFlow<ExtractionState>(ExtractionState.Idle)
    val extractionState: StateFlow<ExtractionState> = _extractionState.asStateFlow()
    
    private val _commitComplete = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val commitComplete: SharedFlow<Unit> = _commitComplete.asSharedFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _extractionToken = MutableStateFlow(0L)
    val extractionToken: StateFlow<Long> = _extractionToken.asStateFlow()

    private var extractionJob: Job? = null

    init {
        loadDraftIfExists()
    }

    private fun loadDraftIfExists() {
        viewModelScope.launch {
            val saved = draftStore.loadDraft() ?: return@launch
            val isBroken = (saved.stage is OnboardingStage.FinalReview && saved.editedProfile == null) ||
                           (saved.stage is OnboardingStage.Completed)
            
            if (isBroken) {
                draftStore.clearDraft()
                _draft.value = OnboardingDraft()
                return@launch
            }
            
            val hasRecoverableText = !saved.rawExtractedText.isNullOrBlank()
            if (saved.stage is OnboardingStage.ReviewingExtraction && saved.extraction == null && hasRecoverableText) {
                _draft.value = saved.copy(stage = OnboardingStage.Extracting)
                _extractionState.value = ExtractionState.Extracting("Recovering your session...")
                
                val extractionMode = when {
                    saved.uploadedCvUri?.endsWith(".pdf", true) == true -> ExtractionMode.PDF_TEXT
                    saved.uploadedCvUri?.endsWith(".docx", true) == true -> ExtractionMode.DOCX
                    else -> ExtractionMode.OCR
                }

                val structureResult = extractionEngine.structureProfile(
                    rawText = saved.rawExtractedText!!,
                    extractionMode = extractionMode,
                    sessionId = sessionId,
                    onProgress = { status -> _extractionState.value = ExtractionState.Extracting(status) }
                )

                if (structureResult.isFailure) {
                    draftStore.clearDraft()
                    _draft.value = OnboardingDraft(stage = OnboardingStage.UploadingCv)
                    _extractionState.value = ExtractionState.Idle
                    return@launch
                }

                val extraction = structureResult.getOrThrow()
                val mappedProfile = commitService.extractionToProfile(extraction)
                
                updateDraft {
                    it.copy(
                        extraction = extraction,
                        editedProfile = mappedProfile,
                        stage = OnboardingStage.ReviewingExtraction
                    )
                }
                _extractionState.value = ExtractionState.Success
                return@launch
            }

            if (saved.stage is OnboardingStage.ReviewingExtraction && !hasRecoverableText) {
                draftStore.clearDraft()
                _draft.value = OnboardingDraft(stage = OnboardingStage.UploadingCv)
                _extractionState.value = ExtractionState.Idle
                return@launch
            }

            _draft.value = saved
        }
    }

    fun selectAcquisitionMode(mode: AcquisitionMode) {
        viewModelScope.launch {
            val nextStage = when (mode) {
                AcquisitionMode.CV_UPLOAD -> OnboardingStage.UploadingCv
                AcquisitionMode.MANUAL -> OnboardingStage.EditingProfile
            }
            updateDraft {
                it.copy(
                    acquisitionMode = mode,
                    stage = nextStage
                )
            }
        }
    }

    fun startExtraction(uri: Uri) {
        Log.i(TAG, "[Extraction] ENTRY: startExtraction called, uri=$uri")
        extractionJob?.cancel()

        extractionJob = viewModelScope.launch {
            extractionMutex.withLock {
                val token = _extractionToken.value + 1
                _extractionToken.value = token

                _error.value = null
                _extractionState.value = ExtractionState.Extracting("Reading document...")

                updateDraft {
                    it.copy(
                        uploadedCvUri = uri.toString(),
                        stage = OnboardingStage.Extracting,
                        rawExtractedText = null,
                        extraction = null,
                        editedProfile = null
                    )
                }

                val rawResult = extractionEngine.extractRawText(uri)

                if (token != _extractionToken.value) {
                    Log.i(TAG, "[Extraction] EXIT: token mismatch — stale job cancelled")
                    return@withLock
                }

                if (rawResult.isFailure) {
                    val msg = rawResult.exceptionOrNull()?.message ?: "FILE_READ_ERROR"
                    Log.i(TAG, "[Extraction] EXIT: extractRawText failed — $msg")
                    _error.value = msg
                    _extractionState.value = ExtractionState.Failure(msg)
                    return@withLock
                }

                val rawText = rawResult.getOrThrow()
                updateDraft { it.copy(rawExtractedText = rawText) }

                runStructuredExtraction(token, rawText)
            }
        }
    }

    private suspend fun runStructuredExtraction(token: Long, rawText: String) {
        Log.i(TAG, "[Extraction] ENTRY: runStructuredExtraction, rawText.length=${rawText.length}")
        _extractionState.value = ExtractionState.Extracting("Analyzing profile...")

        val extractionMode = when {
            _draft.value.uploadedCvUri?.endsWith(".pdf", true) == true -> ExtractionMode.PDF_TEXT
            _draft.value.uploadedCvUri?.endsWith(".docx", true) == true -> ExtractionMode.DOCX
            else -> ExtractionMode.OCR
        }
        
        Log.i(TAG, "[Gate0] ENTRY: structureProfile called, rawText.length=${rawText.length}, mode=$extractionMode")

        val result = extractionEngine.structureProfile(
            rawText = rawText,
            extractionMode = extractionMode,
            sessionId = sessionId,
            onProgress = { status ->
                _extractionState.value = ExtractionState.Extracting(status)
            }
        )

        if (token != _extractionToken.value) {
            Log.i(TAG, "[Extraction] EXIT: token mismatch after structureProfile — stale job")
            return
        }

        if (result.isFailure) {
            val errorMsg = result.exceptionOrNull()?.message ?: "AI_FAILED"
            Log.i(TAG, "[Extraction] structureProfile failed — $errorMsg")

            _extractionState.value = ExtractionState.Retrying("Retrying with safer parsing...")
            
            val retryResult = extractionEngine.structureProfile(
                rawText = rawText.take(8000),
                extractionMode = extractionMode,
                sessionId = sessionId,
                onProgress = { status ->
                    _extractionState.value = ExtractionState.Retrying(status)
                }
            )

            if (retryResult.isFailure) {
                Log.i(TAG, "[Extraction] EXIT: retry failed — $errorMsg")
                _error.value = "Extraction failed after retry"
                _extractionState.value = ExtractionState.Failure("Unable to extract profile")
                return
            }

            handleSuccess(token, retryResult.getOrThrow())
            return
        }

        handleSuccess(token, result.getOrThrow())
    }

    private suspend fun handleSuccess(token: Long, extraction: StructuredProfileExtraction) {
        if (token != _extractionToken.value) {
            Log.i(TAG, "[Extraction] EXIT: token mismatch in handleSuccess — stale job")
            return
        }

        if (extraction.overallConfidence < 0.4f) {
            _error.value = "Low confidence extraction. Please review."
        }

        val mapped = commitService.extractionToProfile(extraction)

        updateDraft {
            it.copy(
                extraction = extraction,
                editedProfile = mapped,
                stage = OnboardingStage.ReviewingExtraction
            )
        }

        Log.i(TAG, "[Extraction] EXIT: success — confidence=${extraction.overallConfidence}")
        _extractionState.value = ExtractionState.Success
    }

    fun updateEditedProfile(profile: UserProfile) {
        viewModelScope.launch {
            updateDraft { it.copy(editedProfile = profile) }
        }
    }

    fun addSkill(skillValue: String) {
        if (skillValue.isBlank()) return

        viewModelScope.launch {
            val current = _draft.value.editedProfile ?: return@launch
            val updated = current.skills + skillValue

            updateDraft {
                it.copy(editedProfile = current.copy(skills = updated))
            }
        }
    }

    fun removeSkill(skillValue: String) {
        viewModelScope.launch {
            val current = _draft.value.editedProfile ?: return@launch
            val updated = current.skills.filterNot { it == skillValue }
            
            updateDraft {
                it.copy(editedProfile = current.copy(skills = updated))
            }
        }
    }

    fun commitProfile() {
        val profile = _draft.value.editedProfile

        if (
            profile == null || profile.name.isBlank() || profile.email.isBlank() || profile.skills.isEmpty()
        ) {
            _error.value = "Complete required fields before continuing"
            return
        }

        if (!isCommitting.compareAndSet(false, true)) return

        viewModelScope.launch {
            try {
                commitService.commit(_draft.value)
                
                val rawText = _draft.value.rawExtractedText
                if (!rawText.isNullOrBlank()) {
                    val hash = HashUtils.sha256(rawText)
                    telemetryCollector.recordDecision(
                        UserDecisionEvent(
                            documentHash = hash,
                            sessionId = sessionId,
                            decisionType = "REVIEW_COMPLETED"
                        )
                    )
                }

                _draft.value = OnboardingDraft(stage = OnboardingStage.Completed)
                _commitComplete.emit(Unit)
            } catch (e: Exception) {
                isCommitting.set(false)
                _error.value = e.message ?: "Commit failed"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun advanceToChoosePath() {
        viewModelScope.launch {
            updateDraft { it.copy(stage = OnboardingStage.ChoosePath) }
        }
    }

    fun advanceToFinalReview() {
        viewModelScope.launch {
            updateDraft { it.copy(stage = OnboardingStage.FinalReview) }
        }
    }

    fun resetToChoosePath() {
        Log.i(TAG, "[Extraction] CANCEL: resetToChoosePath — cancelling extractionJob")
        extractionJob?.cancel()
        _extractionState.value = ExtractionState.Idle

        viewModelScope.launch {
            updateDraft { it.copy(stage = OnboardingStage.ChoosePath) }
        }
    }

    private suspend fun updateDraft(transform: (OnboardingDraft) -> OnboardingDraft) {
        draftMutex.withLock {
            val updated = transform(_draft.value)
            draftStore.saveDraft(updated)
            _draft.value = updated
        }
    }
}
