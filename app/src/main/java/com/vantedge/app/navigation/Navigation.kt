package com.vantedge.app.navigation

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vantedge.app.data.engine.CompatibilityEngine
import com.vantedge.app.data.engine.GeneratorEngine
import com.vantedge.app.data.engine.ProfileExtractionEngine
import com.vantedge.app.data.model.AcquisitionMode
import com.vantedge.app.data.model.AiRawResponseArtifact
import com.vantedge.app.data.model.GenerationMode
import com.vantedge.app.data.model.OnboardingDraft
import com.vantedge.app.data.model.OnboardingStage
import com.vantedge.app.data.network.AiGateway
import com.vantedge.app.data.network.GeminiService
import com.vantedge.app.data.storage.CompatibilityStore
import com.vantedge.app.data.storage.HistoryStore
import com.vantedge.app.data.storage.OnboardingDraftStore
import com.vantedge.app.data.storage.UserPreferences
import com.vantedge.app.data.storage.VantEdgeDatabase
import com.vantedge.app.data.viewmodel.CompatibilityViewModel
import com.vantedge.app.data.viewmodel.CycleViewModel
import com.vantedge.app.data.viewmodel.GeneratorViewModel
import com.vantedge.app.data.viewmodel.OnboardingViewModel
import com.vantedge.app.data.viewmodel.OnboardingViewModelFactory
import com.vantedge.app.data.domain.OnboardingCommitService
import com.vantedge.app.data.domain.CompatibilityOrchestrator
import com.vantedge.app.data.domain.OptimizationOrchestrator
import com.vantedge.pipeline.contract.ContractValidator
import com.vantedge.app.domain.extraction.JobExtractionOrchestrator
import com.vantedge.app.ui.screens.ChoosePathScreen
import com.vantedge.app.ui.screens.CompatibilityInputScreen
import com.vantedge.app.ui.screens.CVDesignScreen
import com.vantedge.app.ui.screens.CVGeneratorScreen
import com.vantedge.app.ui.screens.CycleHistoryScreen
import com.vantedge.app.ui.screens.DashboardScreen
import com.vantedge.app.ui.screens.EditProfileScreen
import com.vantedge.app.ui.screens.ForensicDebugScreen
import com.vantedge.app.ui.screens.ExtractingScreen
import com.vantedge.app.ui.screens.FinalReviewScreen
import com.vantedge.app.ui.screens.JobInputScreen
import com.vantedge.app.ui.screens.ResultScreen
import com.vantedge.app.ui.screens.ResultScreenMode
import com.vantedge.app.ui.screens.ReviewExtractionScreen
import com.vantedge.app.ui.screens.WelcomeScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import com.vantedge.app.util.TelemetryCollector
import kotlinx.coroutines.launch

private fun androidx.navigation.NavHostController.navigateOnboarding(route: String) {
    this.navigate(route) {
        popUpTo("onboarding_extracting") { inclusive = true }
        launchSingleTop = true
    }
}

private fun canEnter(
    stage: OnboardingStage,
    draft: OnboardingDraft
): Boolean {
    return when (stage) {
        OnboardingStage.Welcome -> true
        OnboardingStage.ChoosePath -> true
        OnboardingStage.UploadingCv -> true
        OnboardingStage.Extracting -> true
        OnboardingStage.ReviewingExtraction -> draft.extraction != null
        OnboardingStage.EditingProfile -> draft.editedProfile != null
        OnboardingStage.FinalReview ->
            draft.editedProfile != null &&
            draft.editedProfile.name.isNotBlank() &&
            draft.editedProfile.email.isNotBlank() &&
            draft.editedProfile.skills.isNotEmpty()
        OnboardingStage.Completed -> true
    }
}

@Composable
fun AppNavigation(userPreferences: UserPreferences) {

    val navController = rememberNavController()
    val context = LocalContext.current

    val db = remember { VantEdgeDatabase.getInstance(context) }
    val persistenceScope = remember { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
    val geminiService = remember { GeminiService(
        artifactDao = db.aiRawResponseArtifactDao(),
        persistenceScope = persistenceScope
    ) }
    val aiGateway = remember { AiGateway(geminiService) }

    val historyStore = remember { HistoryStore(db.cycleDao()) }
    val compatibilityStore = remember { CompatibilityStore() }
    val onboardingDraftStore = remember { OnboardingDraftStore(context) }

    val compatibilityEngine = remember { CompatibilityEngine() }
    val contractValidator = remember { ContractValidator() }
    val generatorEngine = remember { GeneratorEngine(aiGateway) }

    val orchestrator = remember {
        OptimizationOrchestrator(
            aiGateway = aiGateway,
            contractValidator = contractValidator,
            compatibilityEngine = compatibilityEngine,
            generatorEngine = generatorEngine,
            historyStore = historyStore
        )
    }

    // Single instance per composition lifetime.
    // Persists telemetry to cacheDir/telemetry.log via async file append.
    val telemetryCollector = remember { TelemetryCollector(context) }

    val extractionEngine = remember {
        ProfileExtractionEngine(context, aiGateway, telemetryCollector)
    }

    val jobExtractionOrchestrator = remember {
        JobExtractionOrchestrator(aiGateway)
    }

    val onboardingCommitService = remember {
        OnboardingCommitService(
            userPreferences = userPreferences,
            draftStore = onboardingDraftStore
        )
    }

    val onboardingViewModel: OnboardingViewModel = viewModel(
        factory = remember {
            OnboardingViewModelFactory(
                extractionEngine = extractionEngine,
                commitService = onboardingCommitService,
                draftStore = onboardingDraftStore,
                telemetryCollector = telemetryCollector
            )
        }
    )

    val cycleViewModel: CycleViewModel = viewModel(
        factory = remember {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return CycleViewModel(
                        orchestrator = orchestrator,
                        historyStore = historyStore
                    ) as T
                }
            }
        }
    )

    val compatibilityViewModel: CompatibilityViewModel = viewModel(
        factory = remember {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return CompatibilityViewModel(
                        store = compatibilityStore,
                        userPreferences = userPreferences,
                        optimizationOrchestrator = orchestrator as CompatibilityOrchestrator,
                        extractionOrchestrator = jobExtractionOrchestrator
                    ) as T
                }
            }
        }
    )

    val profile by userPreferences.userProfileFlow.collectAsState(initial = null)
    val draft by onboardingViewModel.draft.collectAsState()
    val extractionState by onboardingViewModel.extractionState.collectAsState()

    val cvFileLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            try {
                val takeFlags: Int =
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(
                    selectedUri,
                    takeFlags
                )
                onboardingViewModel.startExtraction(selectedUri)
            } catch (e: SecurityException) {
                onboardingViewModel.clearError()
            }
        }
    }

    if (profile == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0D0D0D))
        )
        return
    }

    val isOnboarded = profile!!.name.isNotBlank()
    val startDestination = if (isOnboarded) "dashboard" else "onboarding_welcome"

    LaunchedEffect(draft.stage) {
        if (!canEnter(draft.stage, draft)) {
            onboardingViewModel.resetToChoosePath()
            return@LaunchedEffect
        }
        when (draft.stage) {
            OnboardingStage.Welcome -> Unit
            OnboardingStage.ChoosePath -> {
                navController.navigate("onboarding_choose_path") {
                    launchSingleTop = true
                    popUpTo("onboarding_extracting") { inclusive = true }
                }
            }
            OnboardingStage.UploadingCv -> Unit
            OnboardingStage.Extracting -> {
                navController.navigate("onboarding_extracting") {
                    launchSingleTop = true
                    popUpTo("onboarding_extracting") { inclusive = true }
                }
            }
            OnboardingStage.ReviewingExtraction -> {
                navController.navigateOnboarding("onboarding_review")
            }
            OnboardingStage.EditingProfile -> {
                navController.navigateOnboarding("onboarding_edit_profile")
            }
            OnboardingStage.FinalReview -> {
                navController.navigateOnboarding("onboarding_final_review")
            }
            OnboardingStage.Completed -> Unit
        }
    }

    LaunchedEffect(Unit) {
        onboardingViewModel.commitComplete.collect {
            navController.navigate("dashboard") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable("onboarding_welcome") {
            WelcomeScreen(
                onContinue = {
                    onboardingViewModel.advanceToChoosePath()
                }
            )
        }

        composable("onboarding_choose_path") {
            ChoosePathScreen(
                onSelectMode = { mode ->
                    onboardingViewModel.selectAcquisitionMode(mode)
                    if (mode == AcquisitionMode.CV_UPLOAD) {
                        cvFileLauncher.launch(
                            arrayOf(
                                "application/pdf",
                                "application/msword",
                                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                            )
                        )
                    }
                }
            )
        }

        composable("onboarding_extracting") {
            ExtractingScreen(
                extractionState = extractionState,
                onRetry = {
                    onboardingViewModel.clearError()
                    onboardingViewModel.resetToChoosePath()
                }
            )
        }

        composable("onboarding_review") {
            val extraction = draft.extraction
            if (extraction != null) {
                ReviewExtractionScreen(
                    extraction = extraction,
                    onConfirm = {
                        onboardingViewModel.updateEditedProfile(it)
                        onboardingViewModel.advanceToFinalReview()
                    },
                    onBack = {
                        onboardingViewModel.resetToChoosePath()
                    }
                )
            }
        }

        composable("onboarding_edit_profile") {
            EditProfileScreen(
                initialProfile = draft.editedProfile ?: profile!!,
                onContinue = {
                    onboardingViewModel.updateEditedProfile(it)
                    onboardingViewModel.advanceToFinalReview()
                }
            )
        }

        composable("onboarding_final_review") {
            val edited = draft.editedProfile
            if (edited != null) {
                FinalReviewScreen(
                    profile = edited,
                    errorMessage = onboardingViewModel.error.collectAsState().value,
                    onConfirm = {
                        onboardingViewModel.commitProfile()
                    },
                    onBack = {
                        navController.popBackStack()
                    },
                    onClearError = {
                        onboardingViewModel.clearError()
                    }
                )
            }
        }

        composable("dashboard") {
            DashboardScreen(
                cycleViewModel = cycleViewModel,
                onNavigateToCompatibility = {
                    navController.navigate("compatibility_input")
                },
                onNavigateToCvGenerator = {
                    navController.navigate("cv_generator")
                },
                onNavigateToHistory = {
                    navController.navigate("history")
                },
                onNavigateToEditProfile = {
                    navController.navigate("edit_profile")
                },
                onNavigateToNewApplication = {
                    navController.navigate("new_application")
                },
                onNavigateToQuickAnalysis = {
                    navController.navigate("quick_analysis")
                },
                onNavigateToQuickGenerate = {
                    navController.navigate("quick_generate")
                },
                onNavigateToForensicDebug = {
                    navController.navigate("forensic_debug")
                }
            )
        }

        composable("compatibility_input") {
            CompatibilityInputScreen(
                viewModel = compatibilityViewModel,
                profile = profile!!,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToResult = {
                    navController.navigate("compatibility_result")
                }
            )
        }

        composable("compatibility_result") {
            val cycle = cycleViewModel.currentCycle
            if (cycle != null) {
                ResultScreen(
                    navController = navController,
                    viewModel = cycleViewModel,
                    mode = ResultScreenMode.Live(cycle)
                )
            }
        }

        composable("cv_generator") {
            val generatorViewModel: GeneratorViewModel = viewModel(
                factory = remember {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            @Suppress("UNCHECKED_CAST")
                            return GeneratorViewModel(
                                historyStore = historyStore,
                                orchestrator = orchestrator
                            ) as T
                        }
                    }
                }
            )
            CVGeneratorScreen(
                viewModel = generatorViewModel,
                profile = profile!!,
                designId = "modern",
                schemeId = "navy",
                extractionOrchestrator = jobExtractionOrchestrator,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHistory = {
                    navController.navigate("history")
                },
                onNavigateToPreview = {
                    navController.navigate("cv_preview")
                },
                onDocxSaved = {
                    navController.popBackStack()
                },
                onNavigateToDashboard = {
                    navController.navigate("dashboard") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onNavigateToCompatibility = {
                    navController.navigate("compatibility_input")
                },
                onNavigateToEditProfile = {
                    navController.navigate("edit_profile")
                }
            )
        }

        composable("cv_design") {
            CVDesignScreen(
                onDesignSelected = { designId ->
                    navController.navigate("cv_preview")
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("cv_preview") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                androidx.compose.material3.Text(
                    text = "Preview (stub)",
                    style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
                )
            }
        }

        composable("history") {
            val cycles by cycleViewModel.cyclesFlow.collectAsState()
            CycleHistoryScreen(
                cycles = cycles,
                viewModel = cycleViewModel,
                onOpen = { cycle ->
                    cycleViewModel.setCurrentCycle(cycle)
                    // FIX: was "result" (Live mode) — historical cycles must render
                    // with ResultScreenMode.Historical to show the correct bottom bar
                    // and block live-cycle actions (dismiss/save) on committed records.
                    navController.navigate("result_historical")
                },
                onDelete = { id ->
                    cycleViewModel.deleteCycle(id)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("edit_profile") {
            val scope = rememberCoroutineScope()
            EditProfileScreen(
                initialProfile = profile!!,
                onContinue = { updatedProfile ->
                    scope.launch {
                        userPreferences.saveProfile(updatedProfile)
                    }
                    navController.popBackStack()
                }
            )
        }

        composable("new_application") {
            JobInputScreen(
                viewModel = cycleViewModel,
                profile = profile!!,
                mode = GenerationMode.NEW_APPLICATION,
                extractionOrchestrator = jobExtractionOrchestrator,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToResult = {
                    navController.navigate("result")
                },
                onNavigateToDesign = {
                    navController.navigate("cv_design")
                }
            )
        }

        composable("quick_analysis") {
            JobInputScreen(
                viewModel = cycleViewModel,
                profile = profile!!,
                mode = GenerationMode.QUICK_ANALYSIS,
                extractionOrchestrator = jobExtractionOrchestrator,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToResult = {
                    navController.navigate("result")
                },
                onNavigateToDesign = {
                    navController.navigate("cv_design")
                }
            )
        }

        composable("quick_generate") {
            JobInputScreen(
                viewModel = cycleViewModel,
                profile = profile!!,
                mode = GenerationMode.QUICK_GENERATE,
                extractionOrchestrator = jobExtractionOrchestrator,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToResult = {
                    navController.navigate("result")
                },
                onNavigateToDesign = {
                    navController.navigate("cv_design")
                }
            )
        }

        // Live result: used by new_application, quick_analysis, quick_generate,
        // and compatibility_result flows. Always renders ResultScreenMode.Live.
        composable("result") {
            val cycle = cycleViewModel.currentCycle
            if (cycle != null) {
                ResultScreen(
                    navController = navController,
                    viewModel = cycleViewModel,
                    mode = ResultScreenMode.Live(cycle)
                )
            }
        }

        // Historical result: used exclusively by the history screen (CycleHistoryScreen.onOpen).
        // Renders ResultScreenMode.Historical so the correct bottom bar is shown
        // ("Improve This Application") and live-cycle actions are suppressed.
        composable("result_historical") {
            val cycle = cycleViewModel.currentCycle
            if (cycle != null) {
                ResultScreen(
                    navController = navController,
                    viewModel = cycleViewModel,
                    mode = ResultScreenMode.Historical(cycle)
                )
            }
        }

        composable("forensic_debug") {
            val dao = remember { db.aiRawResponseArtifactDao() }
            val artifacts = remember { mutableStateOf<List<AiRawResponseArtifact>>(emptyList()) }
            LaunchedEffect(Unit) {
                artifacts.value = dao.getAll()
            }
            ForensicDebugScreen(
                artifacts = artifacts.value,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
