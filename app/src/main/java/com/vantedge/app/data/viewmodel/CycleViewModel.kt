package com.vantedge.app.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vantedge.app.data.model.CycleState
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

    private val _uiState = MutableStateFlow<CycleUiState>(CycleUiState.Idle)
    val uiState: StateFlow<CycleUiState> = _uiState

    private val _navEvent = MutableSharedFlow<CycleNavEvent>(extraBufferCapacity = 1)
    val navEvent: SharedFlow<CycleNavEvent> = _navEvent.asSharedFlow()

    val cyclesFlow: StateFlow<List<GenerationCycle>> = historyStore.cyclesFlow

    var currentCycle: GenerationCycle? = null
        private set

    var currentMode: GenerationMode = GenerationMode.NEW_APPLICATION
        private set

    var savedJobTitle: String = ""
    var savedCompany: String = ""
    var savedJobDescription: String = ""

    var improvementContext: String? = null
        private set

    private var pipelineJob: Job? = null

    fun resetState() {
        _uiState.value = CycleUiState.Idle
    }

    fun cancelPipeline() {
        pipelineJob?.cancel()
        pipelineJob = null
        _uiState.value = CycleUiState.Idle
    }

    fun setCurrentCycle(cycle: GenerationCycle) {
        currentCycle = cycle
    }

    fun deleteCycle(id: String) {
        historyStore.deleteCycle(id)
    }

    fun restoreCycle(cycle: GenerationCycle) {
        currentCycle = cycle
        val stage = when (cycle.state) {
            is CycleState.AnalysisOnly -> CycleStage.ANALYZED
            is CycleState.GenerationReady -> CycleStage.READY_FOR_GENERATION
            is CycleState.FullCycle -> CycleStage.DOCUMENTS_GENERATED
        }
        viewModelScope.launch {
            _navEvent.emit(CycleNavEvent.ToCycleRestored(cycle.id, stage))
        }
    }

    fun improveFromCycle(cycle: GenerationCycle) {
        currentCycle = cycle
        savedJobTitle = cycle.jobTitle
        savedCompany = cycle.company
        savedJobDescription = cycle.jobDescription

        val gaps = when (val s = cycle.state) {
            is CycleState.FullCycle -> s.compatibility.gaps.joinToString(", ") { it.skill }
            is CycleState.AnalysisOnly -> s.compatibility.gaps.joinToString(", ") { it.skill }
            is CycleState.GenerationReady -> s.compatibility.gaps.map { it.skill }.joinToString(", ")
        }

        improvementContext = if (gaps.isNotBlank())
            "Previous version skill gaps to address: $gaps"
        else
            "Improve and strengthen the previous version."

        currentMode = GenerationMode.IMPROVE
    }

    fun startImproveFromCycle(cycle: GenerationCycle) {
        improveFromCycle(cycle)
        _uiState.value = CycleUiState.Loading(PipelineStep.GENERATING_CV)

        pipelineJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val readyCycle = orchestrator.runGenerationFromCycle(
                    cycle = cycle,
                    improvementContext = improvementContext
                )
                currentCycle = readyCycle
                _uiState.value = CycleUiState.GenerationReady(readyCycle)
                _navEvent.emit(CycleNavEvent.ToDesignPicker)
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) return@launch
                _uiState.value = CycleUiState.Error(e.message ?: "Generation failed.")
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
            .mapNotNull { other ->
                when (val s = other.state) {
                    is CycleState.FullCycle -> s.compatibility.score
                    is CycleState.AnalysisOnly -> s.compatibility.score
                    is CycleState.GenerationReady -> s.compatibility.score
                }
            }
            .firstOrNull()
    }

    fun getScoreForCycle(cycle: GenerationCycle): Int? {
        return when (val s = cycle.state) {
            is CycleState.FullCycle -> s.compatibility.score
            is CycleState.AnalysisOnly -> s.compatibility.score
            is CycleState.GenerationReady -> s.compatibility.score
        }
    }

    fun getGapsForCycle(cycle: GenerationCycle): List<String> {
        return when (val s = cycle.state) {
            is CycleState.FullCycle -> s.compatibility.gaps.map { it.skill }
            is CycleState.AnalysisOnly -> s.compatibility.gaps.map { it.skill }
            is CycleState.GenerationReady -> s.compatibility.gaps.map { it.skill }
        }
    }

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
        _uiState.value = CycleUiState.Loading(PipelineStep.ANALYSING)

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
                        currentCycle = cycle
                        _uiState.value = CycleUiState.AnalysisDone(cycle)
                        _navEvent.emit(CycleNavEvent.ToAnalysisResult)
                    }

                    GenerationMode.IMPROVE -> {
                        val existingCycle = currentCycle
                            ?: throw Exception("No cycle to improve from.")
                        val readyCycle = orchestrator.runGenerationFromCycle(
                            cycle = existingCycle,
                            improvementContext = improvementContext
                        )
                        currentCycle = readyCycle
                        _uiState.value = CycleUiState.GenerationReady(readyCycle)
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
                                _uiState.value = CycleUiState.Loading(step)
                            }
                        )
                        currentCycle = cycle
                        _uiState.value = CycleUiState.GenerationReady(cycle)
                        _navEvent.emit(CycleNavEvent.ToDesignPicker)
                    }
                }
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) return@launch
                _uiState.value = CycleUiState.Error(e.message ?: "Something went wrong.")
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
        _uiState.value = CycleUiState.Loading(PipelineStep.APPLYING_DESIGN)

        pipelineJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val fullCycle = orchestrator.applyDesign(
                    cycleId = cycle.id,
                    design = design
                )
                currentCycle = fullCycle
                _uiState.value = CycleUiState.Success(fullCycle)
                _navEvent.emit(CycleNavEvent.ToFinalResult)
            } catch (e: Exception) {
                _uiState.value = CycleUiState.Error(e.message ?: "Failed to apply design.")
            }
        }
    }

    fun continueToGeneration(improvementContext: String? = null) {
        val cycle = currentCycle ?: return
        _uiState.value = CycleUiState.Loading(PipelineStep.GENERATING_CV)

        pipelineJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val readyCycle = orchestrator.runGenerationFromCycle(
                    cycle = cycle,
                    improvementContext = improvementContext
                )
                currentCycle = readyCycle
                _uiState.value = CycleUiState.GenerationReady(readyCycle)
                _navEvent.emit(CycleNavEvent.ToDesignPicker)
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) return@launch
                _uiState.value = CycleUiState.Error(e.message ?: "Generation failed.")
            }
        }
    }

    fun dismissCurrentCycle() {
        val cycle = currentCycle ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val updated = cycle.copy(isVisibleInHistory = false)
            currentCycle = updated
            historyStore.saveCycle(updated)
        }
    }

    fun commitCurrentCycle() {
        val cycle = currentCycle ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val updated = cycle.copy(isVisibleInHistory = true)
            currentCycle = updated
            historyStore.saveCycle(updated)
        }
    }

    suspend fun commitCurrentCycleAndWait() {
        val cycle = currentCycle ?: return
        val updated = cycle.copy(isVisibleInHistory = true)
        currentCycle = updated
        historyStore.saveCycle(updated)
    }
}