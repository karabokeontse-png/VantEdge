package com.vantedge.app.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vantedge.app.data.model.DesignConfig
import com.vantedge.app.data.model.GenerationCycle
import com.vantedge.app.data.model.GenerationMode
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.storage.HistoryStore
import com.vantedge.app.data.domain.OptimizationOrchestrator
import com.vantedge.app.data.domain.PipelineStep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CycleViewModel(
    private val orchestrator: OptimizationOrchestrator,
    private val historyStore: HistoryStore
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

    init {
        historyStore.cyclesFlow.value
            .lastOrNull { it.isCommitted }
            ?.let { _currentCycle.value = it }
    }

    var currentMode: GenerationMode = GenerationMode.NEW_APPLICATION
        private set

    var savedJobTitle: String = ""
    var savedCompany: String = ""
    var savedJobDescription: String = ""

    var improvementContext: String? = null
        private set

    private var pipelineJob: Job? = null

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

    fun startImproveFromCycle(cycle: GenerationCycle) {
        improveFromCycle(cycle)
        _uiState.value = UiState.Loading(PipelineStep.GENERATING_CV)

        pipelineJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val readyCycle = orchestrator.runGenerationFromCycle(
                    cycle = cycle,
                    improvementContext = improvementContext
                )
                _currentCycle.value = readyCycle
                val cycleUiState = deriveUiState(readyCycle)
                _uiState.value = UiState.Content(cycleUiState)
                emitGenerationEvents(cycleUiState, readyCycle)
                _navEvent.emit(CycleNavEvent.ToDesignPicker)
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.TimeoutCancellationException) {
                    _uiState.value = UiState.Error("Pipeline timed out")
                    return@launch
                }
                if (e is kotlinx.coroutines.CancellationException) return@launch
                _uiState.value = UiState.Error(e.message ?: "Generation failed.")
            }
        }
    }

    fun getPreviousScore(cycle: GenerationCycle): Int? {
        val currentVersion = cycle.version ?: return null
        if (currentVersion <= 1) return null
        return historyStore.cyclesFlow.value
            .filter { other ->
                other.id != cycle.id &&
                other.jobTitle.equals(cycle.jobTitle, ignoreCase = true) &&
                other.company.equals(cycle.company, ignoreCase = true) &&
                other.version == currentVersion - 1
            }
            .mapNotNull { other -> other.compatibility?.score }
            .firstOrNull()
    }

    fun getScoreForCycle(cycle: GenerationCycle): Int? =
        cycle.compatibility?.score

    fun getGapsForCycle(cycle: GenerationCycle): List<String> =
        cycle.compatibility?.gaps?.map { it.skill } ?: emptyList()

    fun totalSavedApplications(): Int =
        historyStore.cyclesFlow.value.count { it.isVisibleInHistory }

    fun averageScore(): Int? {
        val scores = historyStore.cyclesFlow.value
            .filter { it.isVisibleInHistory }
            .mapNotNull { getScoreForCycle(it) }
        return if (scores.isEmpty()) null else scores.average().toInt()
    }

    fun bestScoringRole(): String? =
        historyStore.cyclesFlow.value
            .filter { it.isVisibleInHistory }
            .maxByOrNull { getScoreForCycle(it) ?: 0 }
            ?.jobTitle

    fun improvedApplicationsCount(): Int =
        historyStore.cyclesFlow.value.count {
            it.isVisibleInHistory && (it.version ?: 0) >= 2
        }

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
        improvementContext: String? = null
    ) {
        pipelineJob?.cancel()
        currentMode = mode
        _uiState.value = UiState.Loading(PipelineStep.ANALYSING)

        pipelineJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                when (mode) {
                    GenerationMode.NEW_APPLICATION,
                    GenerationMode.QUICK_ANALYSIS -> {
                        val cycle = orchestrator.runAnalysisOnly(
                            profile = profile,
                            jobTitle = jobTitle,
                            company = company,
                            jobDescription = jobDescription,
                            improvementContext = improvementContext
                        )
                        _currentCycle.value = cycle
                        _uiState.value = UiState.Idle
                        _navEvent.emit(CycleNavEvent.ToAnalysisResult)
                    }

                    GenerationMode.IMPROVE -> {
                        val existingCycle = currentCycle
                            ?: throw Exception("No cycle to improve from.")
                        val readyCycle = orchestrator.runGenerationFromCycle(
                            cycle = existingCycle,
                            improvementContext = improvementContext
                        )
                        _currentCycle.value = readyCycle
                        val cycleUiState = deriveUiState(readyCycle)
                        _uiState.value = UiState.Content(cycleUiState)
                        emitGenerationEvents(cycleUiState, readyCycle)
                        _navEvent.emit(CycleNavEvent.ToDesignPicker)
                    }

                    GenerationMode.QUICK_GENERATE -> {
                        val cycle = orchestrator.runFullPipeline(
                            profile = profile,
                            jobTitle = jobTitle,
                            company = company,
                            jobDescription = jobDescription,
                            mode = mode,
                            improvementContext = improvementContext,
                            onProgress = { step ->
                                _uiState.value = UiState.Loading(step)
                            }
                        )
                        _currentCycle.value = cycle
                        val cycleUiState = deriveUiState(cycle)
                        _uiState.value = UiState.Content(cycleUiState)
                        emitGenerationEvents(cycleUiState, cycle)
                        _navEvent.emit(CycleNavEvent.ToDesignPicker)
                    }
                }
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.TimeoutCancellationException) {
                    _uiState.value = UiState.Error("Pipeline timed out")
                    return@launch
                }
                if (e is kotlinx.coroutines.CancellationException) return@launch
                _uiState.value = UiState.Error(e.message ?: "Something went wrong.")
            }
        }
    }

    fun runAnalysis(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        jobDescription: String,
        mode: GenerationMode,
        improvementContext: String? = null
    ) {
        runPipeline(
            profile = profile,
            jobTitle = jobTitle,
            company = company,
            jobDescription = jobDescription,
            mode = mode,
            improvementContext = improvementContext
        )
    }

    fun applyDesign(design: DesignConfig) {
        val cycle = currentCycle ?: return
        _uiState.value = UiState.Loading(PipelineStep.APPLYING_DESIGN)

        pipelineJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val fullCycle = orchestrator.applyDesign(
                    cycleId = cycle.id,
                    design = design
                )
                _currentCycle.value = fullCycle
                val cycleUiState = deriveUiState(fullCycle)
                _uiState.value = UiState.Content(cycleUiState)
                _navEvent.emit(CycleNavEvent.ToFinalResult)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Failed to apply design.")
            }
        }
    }

    fun continueToGeneration(improvementContext: String? = null) {
        val cycle = currentCycle ?: return
        _uiState.value = UiState.Loading(PipelineStep.GENERATING_CV)

        pipelineJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val readyCycle = orchestrator.runGenerationFromCycle(
                    cycle = cycle,
                    improvementContext = improvementContext
                )
                _currentCycle.value = readyCycle
                val cycleUiState = deriveUiState(readyCycle)
                _uiState.value = UiState.Content(cycleUiState)
                emitGenerationEvents(cycleUiState, readyCycle)
                _navEvent.emit(CycleNavEvent.ToDesignPicker)
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.TimeoutCancellationException) {
                    _uiState.value = UiState.Error("Pipeline timed out")
                    return@launch
                }
                if (e is kotlinx.coroutines.CancellationException) return@launch
                _uiState.value = UiState.Error(e.message ?: "Generation failed.")
            }
        }
    }

    fun dismissCurrentCycle() {
        val cycle = currentCycle ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val updated = cycle.copy(isVisibleInHistory = false)
            _currentCycle.value = updated
            historyStore.saveCycle(updated)
        }
    }

    fun commitCurrentCycle() {
        val cycle = currentCycle ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val updated = cycle.copy(isVisibleInHistory = true)
            _currentCycle.value = updated
            historyStore.saveCycle(updated)
        }
    }

    suspend fun commitCurrentCycleAndWait() {
        val cycle = currentCycle ?: return
        val updated = cycle.copy(isVisibleInHistory = true)
        _currentCycle.value = updated
        historyStore.saveCycle(updated)
    }

    // ── Truth Matrix ─────────────────────────────────────────────────────

    private fun deriveUiState(cycle: GenerationCycle): CycleUiState {
        val isCvSuccess = cycle.cvContent != null && cycle.cvErrorMessage == null
        val isCvFailure = cycle.cvContent == null && cycle.cvErrorMessage != null

        val isClSuccess = cycle.coverLetterContent != null && cycle.coverLetterErrorMessage == null
        val isClFailure = cycle.coverLetterContent == null && cycle.coverLetterErrorMessage != null

        val status = when {
            isCvSuccess && isClSuccess -> GenerationStatus.SUCCESS
            isCvSuccess && isClFailure -> GenerationStatus.PARTIAL
            isCvFailure && isClSuccess -> GenerationStatus.PARTIAL
            isCvFailure && isClFailure -> GenerationStatus.FAILURE
            else -> GenerationStatus.FAILURE
        }

        return CycleUiState(
            cvContent = cycle.cvContent,
            coverLetterContent = cycle.coverLetterContent,
            status = status
        )
    }

    private suspend fun emitGenerationEvents(uiState: CycleUiState, cycle: GenerationCycle) {
        when (uiState.status) {
            GenerationStatus.SUCCESS -> { }
            GenerationStatus.PARTIAL -> {
                val reason = if (cycle.cvErrorMessage != null && cycle.coverLetterErrorMessage == null)
                    "CV generation encountered issues"
                else if (cycle.cvErrorMessage == null && cycle.coverLetterErrorMessage != null)
                    "Cover letter generation encountered issues"
                else
                    "Some documents could not be generated"
                _navEvent.emit(CycleNavEvent.GenerationPartial(reason))
            }
            GenerationStatus.FAILURE -> {
                val reasons = listOfNotNull(
                    cycle.cvErrorMessage?.let { "CV: $it" },
                    cycle.coverLetterErrorMessage?.let { "Cover letter: $it" }
                )
                _navEvent.emit(CycleNavEvent.GenerationFailed(reasons.joinToString("; ")))
            }
        }
    }
}
