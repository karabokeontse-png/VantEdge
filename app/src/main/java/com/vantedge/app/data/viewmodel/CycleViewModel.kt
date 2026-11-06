package com.vantedge.app.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vantedge.app.data.model.DesignConfig
import com.vantedge.app.data.model.GenerationCycle
import com.vantedge.app.data.model.GenerationMode
import com.vantedge.app.data.model.GenerationRequest
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.domain.extraction.ExtractionOutcome
import com.vantedge.app.domain.extraction.ExtractionReview
import com.vantedge.app.data.storage.HistoryStore
import com.vantedge.app.data.domain.GenerationOrchestrator
import com.vantedge.app.data.domain.GenerationOutcome
import com.vantedge.app.data.domain.PipelineStep
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.UUID

private const val UNASSIGNED_OPERATION = -1L

/** Kind of transient generation feedback routed to a destination. */
enum class GenerationFeedbackKind { PARTIAL, FAILED }

/**
 * Bounded, in-memory feedback record. Identified by the owning [handoffId],
 * the generating [operationId], and a unique [feedbackId] used for acknowledgement.
 */
data class GenerationFeedback(
    val handoffId: String,
    val operationId: Long,
    val feedbackId: String,
    val kind: GenerationFeedbackKind,
    val message: String
)

/** A pending historical-Improve handoff, claimed by the destination that owns it. */
data class ImproveHandoff(
    val handoffId: String,
    val operationId: Long
)

class CycleViewModel(
    private val orchestrator: GenerationOrchestrator,
    private val historyStore: HistoryStore,
    // Test seam: defaults to the production IO path so production scheduling is unchanged.
    private val workerDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    private val _navEvent = MutableSharedFlow<CycleNavEvent>(extraBufferCapacity = 1)
    val navEvent: SharedFlow<CycleNavEvent> = _navEvent.asSharedFlow()

    val cyclesFlow: StateFlow<List<GenerationCycle>> = historyStore.cyclesFlow

    private val _currentCycle = MutableStateFlow<GenerationCycle?>(null)
    val currentCycleFlow: StateFlow<GenerationCycle?> = _currentCycle
    val currentCycle: GenerationCycle?
        get() = _currentCycle.value

    // -- Operation ownership --------------------------------------------------
    // Monotonically advancing owner token. Only the operation holding the current
    // identity may publish UI state. All mutations are Main-confined.
    private var operationCounter: Long = 0L
    private var currentOperationId: Long = 0L

    // -- Historical Improve handoff (pending slot + active owner) -------------
    private var pendingHandoff: ImproveHandoff? = null
    private var activeHandoff: ImproveHandoff? = null

    // -- Generation feedback (bounded, in-memory) -----------------------------
    private val _generationFeedback = MutableStateFlow<GenerationFeedback?>(null)
    val generationFeedback: StateFlow<GenerationFeedback?> = _generationFeedback

    init {
        historyStore.cyclesFlow.value
            .lastOrNull { it.isCommitted }
            ?.let { _currentCycle.value = it }
    }

    var currentMode: GenerationMode = GenerationMode.NEW_APPLICATION
        private set

    var currentRequest: GenerationRequest = GenerationRequest.BOTH
        private set

    var savedJobTitle: String = ""
    var savedCompany: String = ""
    var savedJobDescription: String = ""
    var savedRequiredSkills: List<String> = emptyList()
    var savedPreferredSkills: List<String> = emptyList()
    var savedEducationRequired: String? = null

    private val extractionReviewController = ExtractionReviewController()
    val pendingExtractionReview: StateFlow<ExtractionReview?> = extractionReviewController.review

    fun presentExtractionOutcome(outcome: ExtractionOutcome) {
        extractionReviewController.present(outcome)
    }

    fun acknowledgeExtractionReview(corrected: com.vantedge.app.data.model.JobExtractionResult) {
        extractionReviewController.acknowledge(corrected)
    }

    fun clearExtractionReview() {
        extractionReviewController.clear()
    }

    var improvementContext: String? = null
        private set

    private var pipelineJob: Job? = null

    // -- Ownership primitives (Main-confined) ---------------------------------

    private fun beginOperation(): Long {
        val id = ++operationCounter
        currentOperationId = id
        // A newer operation supersedes the previous operation's handoff and feedback.
        pendingHandoff?.let { if (it.operationId != id) pendingHandoff = null }
        activeHandoff?.let { if (it.operationId != id) activeHandoff = null }
        _generationFeedback.value?.let { if (it.operationId != id) _generationFeedback.value = null }
        return id
    }

    private fun ownsUi(operationId: Long): Boolean = operationId == currentOperationId

    private fun publishProgress(operationId: Long, step: PipelineStep) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            if (ownsUi(operationId)) _uiState.value = UiState.Loading(step)
        }
    }

    // -- Historical Improve handoff & feedback (Main-confined) ----------------

    fun registerImproveHandoff(handoffId: String) {
        pendingHandoff = ImproveHandoff(handoffId = handoffId, operationId = UNASSIGNED_OPERATION)
    }

    fun claimImproveHandoff(): ImproveHandoff? {
        val pending = pendingHandoff ?: return null
        // Claim confirms the destination against the reservation already established
        // by the originating operation; it does not establish ownership.
        if (pending != activeHandoff) return null
        if (pending.operationId == UNASSIGNED_OPERATION) return null
        pendingHandoff = null
        return pending
    }

    fun acknowledgeGenerationFeedback(handoff: ImproveHandoff, feedbackId: String) {
        val active = activeHandoff ?: return
        val current = _generationFeedback.value ?: return
        if (active == handoff &&
            current.handoffId == handoff.handoffId &&
            current.operationId == handoff.operationId &&
            current.feedbackId == feedbackId
        ) {
            _generationFeedback.value = null
        }
    }

    fun releaseImproveSession(handoff: ImproveHandoff?) {
        if (handoff == null) return
        // Release invalidates only the exact matching reservation/active owner, so a
        // stale destination can never release a newer owner.
        if (pendingHandoff == handoff) pendingHandoff = null
        if (activeHandoff == handoff) activeHandoff = null
        // Clear feedback only when it belongs to the same handoff and operation.
        val current = _generationFeedback.value
        if (current != null &&
            current.handoffId == handoff.handoffId &&
            current.operationId == handoff.operationId
        ) {
            _generationFeedback.value = null
        }
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }

    fun cancelPipeline() {
        pipelineJob?.cancel()
        pipelineJob = null
        _uiState.value = UiState.Idle
    }

    fun setCurrentCycle(cycle: GenerationCycle) {
        _currentCycle.value = cycle
    }

    fun deleteCycle(id: String) {
        historyStore.deleteCycle(id)
    }

    fun restoreCycle(cycle: GenerationCycle) {
        _currentCycle.value = cycle
        val stage = when {
            cycle.design != null -> CycleStage.DOCUMENTS_GENERATED
            cycle.cvContent != null || cycle.coverLetterContent != null -> CycleStage.READY_FOR_GENERATION
            else -> CycleStage.ANALYZED
        }
        viewModelScope.launch {
            _navEvent.emit(CycleNavEvent.ToCycleRestored(cycle.id, stage))
        }
    }

    fun improveFromCycle(cycle: GenerationCycle) {
        _currentCycle.value = cycle
        savedJobTitle = cycle.jobTitle
        savedCompany = cycle.company
        savedJobDescription = cycle.jobDescription

        val gaps = cycle.compatibility?.gaps?.map { it.skill }?.joinToString(", ") ?: ""

        improvementContext = if (gaps.isNotBlank())
            "Previous version skill gaps to address: $gaps"
        else
            "Improve and strengthen the previous version."

        currentMode = GenerationMode.IMPROVE
    }

    fun startImproveFromCycle(cycle: GenerationCycle, handoffId: String) {
        val operationId = beginOperation()
        val handoff = ImproveHandoff(handoffId = handoffId, operationId = operationId)
        // Reservation is established during association, before generation can complete.
        pendingHandoff = handoff
        activeHandoff = handoff
        improveFromCycle(cycle)
        val context = improvementContext
        _uiState.value = UiState.Loading(PipelineStep.GENERATING_CV)

        pipelineJob = viewModelScope.launch(workerDispatcher) {
            try {
                val readyCycle = orchestrator.runGenerationFromCycle(
                    cycle = cycle,
                    improvementContext = context
                )
                withContext(Dispatchers.Main.immediate) {
                    if (!ownsUi(operationId)) return@withContext
                    val cycleUiState = deriveUiState(readyCycle, GenerationRequest.BOTH)
                    _currentCycle.value = readyCycle
                    _uiState.value = UiState.Content(cycleUiState)
                    // Historical Improve feedback goes to the handoff-owned slot,
                    // not the replay-zero event flow, and never drives artifact nav.
                    storeImproveFeedback(handoffId, operationId, cycleUiState, readyCycle)
                }
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.TimeoutCancellationException) {
                    withContext(Dispatchers.Main.immediate) {
                        if (ownsUi(operationId)) _uiState.value = UiState.Error("Pipeline timed out")
                    }
                    return@launch
                }
                if (e is kotlinx.coroutines.CancellationException) return@launch
                withContext(Dispatchers.Main.immediate) {
                    if (ownsUi(operationId)) _uiState.value = UiState.Error(e.message ?: "Generation failed.")
                }
            }
        }
    }

    fun getPreviousScore(cycle: GenerationCycle): Int? =
        CycleScoreAggregation.previousScore(historyStore.cyclesFlow.value, cycle)

    fun getScoreForCycle(cycle: GenerationCycle): Int? =
        CycleScoreAggregation.completeScore(cycle)

    fun getGapsForCycle(cycle: GenerationCycle): List<String> =
        cycle.compatibility?.gaps?.map { it.skill } ?: emptyList()

    fun totalSavedApplications(): Int =
        historyStore.cyclesFlow.value.count { it.isVisibleInHistory }

    fun averageScore(): Int? =
        CycleScoreAggregation.averageScore(historyStore.cyclesFlow.value)

    fun bestScoringRole(): String? =
        CycleScoreAggregation.bestScoringRole(historyStore.cyclesFlow.value)

    fun incompleteScoreCount(): Int =
        CycleScoreAggregation.incompleteScoreCount(historyStore.cyclesFlow.value)

    fun improvedApplicationsCount(): Int =
        CycleScoreAggregation.improvedApplicationsCount(historyStore.cyclesFlow.value)

    fun findExistingCycleForJob(
        jobTitle: String,
        company: String,
        jobDescription: String
    ): GenerationCycle? {
        return historyStore.cyclesFlow.value.firstOrNull { cycle ->
            cycle.isVisibleInHistory &&
            cycle.jobTitle.equals(jobTitle, ignoreCase = true) &&
            cycle.company.equals(company, ignoreCase = true) &&
            cycle.jobDescription.trim() == jobDescription.trim()
        }
    }

    fun runPipeline(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        jobDescription: String,
        mode: GenerationMode,
        improvementContext: String? = null,
        requiredSkills: List<String> = emptyList(),
        preferredSkills: List<String> = emptyList(),
        educationRequired: String? = null,
        request: GenerationRequest = GenerationRequest.BOTH
    ) {
        pipelineJob?.cancel()
        currentMode = mode
        currentRequest = request
        val operationId = beginOperation()
        _uiState.value = UiState.Loading(PipelineStep.ANALYSING)

        pipelineJob = viewModelScope.launch(workerDispatcher) {
            try {
                when (mode) {
                    GenerationMode.NEW_APPLICATION,
                    GenerationMode.QUICK_ANALYSIS -> {
                        val cycle = orchestrator.runAnalysisOnly(
                            profile = profile,
                            jobTitle = jobTitle,
                            company = company,
                            jobDescription = jobDescription,
                            improvementContext = improvementContext,
                            requiredSkills = requiredSkills,
                            preferredSkills = preferredSkills,
                            educationRequired = educationRequired
                        )
                        withContext(Dispatchers.Main.immediate) {
                            if (!ownsUi(operationId)) return@withContext
                            _currentCycle.value = cycle
                            _uiState.value = UiState.Idle
                            _navEvent.emit(CycleNavEvent.ToAnalysisResult)
                        }
                    }

                    GenerationMode.IMPROVE -> {
                        val existingCycle = currentCycle
                            ?: throw Exception("No cycle to improve from.")
                        val readyCycle = orchestrator.runGenerationFromCycle(
                            cycle = existingCycle,
                            improvementContext = improvementContext
                        )
                        withContext(Dispatchers.Main.immediate) {
                            if (!ownsUi(operationId)) return@withContext
                            val cycleUiState = deriveUiState(readyCycle, GenerationRequest.BOTH)
                            _currentCycle.value = readyCycle
                            _uiState.value = UiState.Content(cycleUiState)
                            emitGenerationEvents(cycleUiState, readyCycle)
                            emitArtifactNavigation(readyCycle, GenerationRequest.BOTH)
                        }
                    }

                    GenerationMode.QUICK_GENERATE -> {
                        val cycle = orchestrator.runFullPipeline(
                            profile = profile,
                            jobTitle = jobTitle,
                            company = company,
                            jobDescription = jobDescription,
                            mode = mode,
                            improvementContext = improvementContext,
                            requiredSkills = requiredSkills,
                            preferredSkills = preferredSkills,
                            educationRequired = educationRequired,
                            request = request,
                            onProgress = { step ->
                                publishProgress(operationId, step)
                            }
                        )
                        withContext(Dispatchers.Main.immediate) {
                            if (!ownsUi(operationId)) return@withContext
                            val cycleUiState = deriveUiState(cycle, request)
                            _currentCycle.value = cycle
                            _uiState.value = UiState.Content(cycleUiState)
                            emitGenerationEvents(cycleUiState, cycle)
                            emitArtifactNavigation(cycle, request)
                        }
                    }
                }
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.TimeoutCancellationException) {
                    withContext(Dispatchers.Main.immediate) {
                        if (ownsUi(operationId)) _uiState.value = UiState.Error("Pipeline timed out")
                    }
                    return@launch
                }
                if (e is kotlinx.coroutines.CancellationException) return@launch
                withContext(Dispatchers.Main.immediate) {
                    if (ownsUi(operationId)) _uiState.value = UiState.Error(e.message ?: "Something went wrong.")
                }
            }
        }
    }

    fun runAnalysis(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        jobDescription: String,
        mode: GenerationMode,
        improvementContext: String? = null,
        requiredSkills: List<String> = emptyList(),
        preferredSkills: List<String> = emptyList(),
        educationRequired: String? = null,
        request: GenerationRequest = GenerationRequest.BOTH
    ) {
        runPipeline(
            profile = profile,
            jobTitle = jobTitle,
            company = company,
            jobDescription = jobDescription,
            mode = mode,
            improvementContext = improvementContext,
            requiredSkills = requiredSkills,
            preferredSkills = preferredSkills,
            educationRequired = educationRequired,
            request = request
        )
    }

    fun applyDesign(design: DesignConfig) {
        val cycle = currentCycle ?: return
        val request = currentRequest
        val operationId = beginOperation()
        _uiState.value = UiState.Loading(PipelineStep.APPLYING_DESIGN)

        pipelineJob = viewModelScope.launch(workerDispatcher) {
            try {
                val fullCycle = orchestrator.applyDesign(
                    cycleId = cycle.id,
                    design = design
                )
                withContext(Dispatchers.Main.immediate) {
                    if (!ownsUi(operationId)) return@withContext
                    val cycleUiState = cycleUiStateOf(fullCycle, request)
                    _currentCycle.value = fullCycle
                    _uiState.value = UiState.Content(cycleUiState)
                    _navEvent.emit(CycleNavEvent.ToFinalResult)
                }
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) return@launch
                withContext(Dispatchers.Main.immediate) {
                    if (ownsUi(operationId)) _uiState.value = UiState.Error(e.message ?: "Failed to apply design.")
                }
            }
        }
    }

    fun continueToGeneration(improvementContext: String? = null) {
        val cycle = currentCycle ?: return
        val operationId = beginOperation()
        _uiState.value = UiState.Loading(PipelineStep.GENERATING_CV)

        pipelineJob = viewModelScope.launch(workerDispatcher) {
            try {
                val readyCycle = orchestrator.runGenerationFromCycle(
                    cycle = cycle,
                    improvementContext = improvementContext
                )
                withContext(Dispatchers.Main.immediate) {
                    if (!ownsUi(operationId)) return@withContext
                    val cycleUiState = deriveUiState(readyCycle, GenerationRequest.BOTH)
                    _currentCycle.value = readyCycle
                    _uiState.value = UiState.Content(cycleUiState)
                    emitGenerationEvents(cycleUiState, readyCycle)
                    emitArtifactNavigation(readyCycle, GenerationRequest.BOTH)
                }
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.TimeoutCancellationException) {
                    withContext(Dispatchers.Main.immediate) {
                        if (ownsUi(operationId)) _uiState.value = UiState.Error("Pipeline timed out")
                    }
                    return@launch
                }
                if (e is kotlinx.coroutines.CancellationException) return@launch
                withContext(Dispatchers.Main.immediate) {
                    if (ownsUi(operationId)) _uiState.value = UiState.Error(e.message ?: "Generation failed.")
                }
            }
        }
    }

    fun dismissCurrentCycle() {
        val cycle = currentCycle ?: return
        // Main-confined: invalidate any older worker before mutating UI state.
        beginOperation()
        val updated = cycle.copy(isVisibleInHistory = false)
        _currentCycle.value = updated
        // Persistence stays on IO and is independent of the UI token.
        viewModelScope.launch(workerDispatcher) {
            historyStore.saveCycle(updated)
        }
    }

    fun commitCurrentCycle() {
        val cycle = currentCycle ?: return
        // Main-confined: invalidate any older worker before mutating UI state.
        beginOperation()
        val updated = cycle.copy(isVisibleInHistory = true)
        _currentCycle.value = updated
        // Persistence stays on IO and is independent of the UI token.
        viewModelScope.launch(workerDispatcher) {
            historyStore.saveCycle(updated)
        }
    }

    suspend fun commitCurrentCycleAndWait() {
        val cycle = currentCycle ?: return
        val updated = cycle.copy(isVisibleInHistory = true)
        withContext(Dispatchers.Main.immediate) {
            // Main-confined: invalidate any older worker before mutating UI state.
            beginOperation()
            _currentCycle.value = updated
        }
        // Persistence stays on IO and is independent of the UI token.
        withContext(workerDispatcher) {
            historyStore.saveCycle(updated)
        }
    }

    // ── Truth Matrix ─────────────────────────────────────────────────────

    private fun deriveUiState(cycle: GenerationCycle, request: GenerationRequest): CycleUiState = cycleUiStateOf(cycle, request)

    private suspend fun emitArtifactNavigation(cycle: GenerationCycle, request: GenerationRequest) {
        val destinations = GenerationOutcome.routingFor(cycle, request)
        when {
            GenerationOutcome.Destination.CV_DESIGN in destinations -> _navEvent.emit(CycleNavEvent.ToDesignPicker)
            GenerationOutcome.Destination.COVER_LETTER_DESIGN in destinations -> _navEvent.emit(CycleNavEvent.ToCoverLetterDesign)
            else -> { }
        }
    }

    /** Transient feedback text for a non-success outcome; null for SUCCESS/INDETERMINATE. */
    private fun feedbackOf(uiState: CycleUiState, cycle: GenerationCycle): Pair<GenerationFeedbackKind, String>? =
        when (uiState.status) {
            GenerationStatus.PARTIAL -> {
                val reason = if (cycle.cvErrorMessage != null && cycle.coverLetterErrorMessage == null)
                    "CV generation encountered issues"
                else if (cycle.cvErrorMessage == null && cycle.coverLetterErrorMessage != null)
                    "Cover letter generation encountered issues"
                else
                    "Some documents could not be generated"
                GenerationFeedbackKind.PARTIAL to reason
            }
            GenerationStatus.FAILURE -> {
                val reasons = listOfNotNull(
                    cycle.cvErrorMessage?.let { "CV: $it" },
                    cycle.coverLetterErrorMessage?.let { "Cover letter: $it" }
                )
                GenerationFeedbackKind.FAILED to reasons.joinToString("; ")
            }
            else -> null
        }

    /**
     * Main-confined publication gate for historical Improve feedback. All four
     * conditions are evaluated together immediately before publication:
     *  1. the originating operation still owns the UI;
     *  2. the active handoff's handoffId matches the originating handoff;
     *  3. the active handoff's operationId matches the originating operation;
     *  4. the active handoff has not been released, superseded or invalidated.
     */
    private fun ownsHistoricalFeedback(handoffId: String, operationId: Long): Boolean {
        val active = activeHandoff ?: return false
        return ownsUi(operationId) &&
            active.handoffId == handoffId &&
            active.operationId == operationId
    }

    /** Stores handoff-owned feedback for the historical Improve destination. Main-confined. */
    private fun storeImproveFeedback(
        handoffId: String,
        operationId: Long,
        uiState: CycleUiState,
        cycle: GenerationCycle
    ) {
        // Gate: validate ownership immediately before the (non-suspending) publication.
        if (!ownsHistoricalFeedback(handoffId, operationId)) return
        val feedback = feedbackOf(uiState, cycle) ?: return
        _generationFeedback.value = GenerationFeedback(
            handoffId = handoffId,
            operationId = operationId,
            feedbackId = UUID.randomUUID().toString(),
            kind = feedback.first,
            message = feedback.second
        )
    }

    private suspend fun emitGenerationEvents(uiState: CycleUiState, cycle: GenerationCycle) {
        val feedback = feedbackOf(uiState, cycle) ?: return
        when (feedback.first) {
            GenerationFeedbackKind.PARTIAL -> _navEvent.emit(CycleNavEvent.GenerationPartial(feedback.second))
            GenerationFeedbackKind.FAILED -> _navEvent.emit(CycleNavEvent.GenerationFailed(feedback.second))
        }
    }
}
