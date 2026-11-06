package com.vantedge.app.ui.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import com.vantedge.app.domain.extraction.ExtractionOutcome
import com.vantedge.app.domain.extraction.JobExtractionOrchestrator
import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.app.data.model.JobSourceType
import com.vantedge.app.data.model.GenerationMode
import com.vantedge.app.data.model.GenerationRequest
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.viewmodel.CycleNavEvent
import com.vantedge.app.data.viewmodel.ImproveHandoff
import com.vantedge.app.data.viewmodel.UiState
import com.vantedge.app.data.viewmodel.CycleViewModel
import com.vantedge.app.ui.components.ExtractionReviewSurface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobInputScreen(
    viewModel: CycleViewModel,
    profile: UserProfile,
    mode: GenerationMode,
    extractionOrchestrator: JobExtractionOrchestrator,
    onNavigateBack: () -> Unit,
    onNavigateToResult: () -> Unit,
    onNavigateToDesign: () -> Unit,
    onNavigateToCoverLetterDesign: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val pendingReview by viewModel.pendingExtractionReview.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }

    val teal = Color(0xFF00BFA5)
    val bgDark = Color(0xFF0D0D0D)
    val cardDark = Color(0xFF1A1A2E)

    var jobTitle by remember { mutableStateOf(viewModel.savedJobTitle) }
    var company by remember { mutableStateOf(viewModel.savedCompany) }
    var jobDescription by remember { mutableStateOf(viewModel.savedJobDescription) }
    var urlInput by remember { mutableStateOf("") }
    var showUrlInput by remember { mutableStateOf(false) }
    var isExtracting by remember { mutableStateOf(false) }
    var extractionError by remember { mutableStateOf<String?>(null) }

    val isLoading = uiState is UiState.Loading

    LaunchedEffect(jobTitle) { viewModel.savedJobTitle = jobTitle }
    LaunchedEffect(company) { viewModel.savedCompany = company }
    LaunchedEffect(jobDescription) { viewModel.savedJobDescription = jobDescription }

    LaunchedEffect(Unit) {
        viewModel.navEvent.collect { event ->
            when (event) {
                is CycleNavEvent.ToAnalysisResult -> {
                    when (mode) {
                        GenerationMode.QUICK_GENERATE -> onNavigateToDesign()
                        else -> onNavigateToResult()
                    }
                }
                is CycleNavEvent.ToDesignPicker -> onNavigateToDesign()
                is CycleNavEvent.GenerationPartial -> {
                    // Feedback only: never navigates to the design screen.
                    snackbar.showSnackbar(event.reason)
                }
                is CycleNavEvent.GenerationFailed -> {
                    snackbar.showSnackbar(event.reason)
                }
                is CycleNavEvent.ToCoverLetterDesign -> onNavigateToCoverLetterDesign()
                else -> {}
            }
        }
    }

    // Historical-Improve handoff: claim the pending handoff for this destination
    // instance and retain the complete record (handoffId + operationId). Ownership
    // is taken from the claimed record, never inferred from the route name or the
    // current operation token.
    val claimedHandoff = remember { mutableStateOf<ImproveHandoff?>(null) }
    DisposableEffect(Unit) {
        claimedHandoff.value = viewModel.claimImproveHandoff()
        onDispose {
            // Release only the matching active owner for this destination instance.
            viewModel.releaseImproveSession(claimedHandoff.value)
        }
    }
    LaunchedEffect(claimedHandoff.value?.handoffId, claimedHandoff.value?.operationId) {
        val handoff = claimedHandoff.value ?: return@LaunchedEffect
        viewModel.generationFeedback.collect { feedback ->
            if (feedback != null &&
                feedback.handoffId == handoff.handoffId &&
                feedback.operationId == handoff.operationId
            ) {
                snackbar.showSnackbar(feedback.message)
                viewModel.acknowledgeGenerationFeedback(handoff, feedback.feedbackId)
            }
        }
    }

    fun fillFromExtraction(extraction: JobExtractionResult) {
        if (extraction.jobTitle != null) jobTitle = extraction.jobTitle
        if (extraction.company != null) company = extraction.company
        jobDescription = extraction.description
        viewModel.savedRequiredSkills = extraction.requiredSkills
        viewModel.savedPreferredSkills = extraction.preferredSkills
        viewModel.savedEducationRequired = extraction.educationRequired
    }

    fun extractAndFill(rawText: String) {
        isExtracting = true
        extractionError = null
        scope.launch(Dispatchers.IO) {
            val result = extractionOrchestrator.extractJob(rawText, JobSourceType.USER_INPUT)
            withContext(Dispatchers.Main) {
                result.onSuccess { outcome ->
                    viewModel.presentExtractionOutcome(outcome)
                    when (outcome) {
                        is ExtractionOutcome.Accepted -> fillFromExtraction(outcome.result)
                        is ExtractionOutcome.ReviewRequired -> fillFromExtraction(outcome.result)
                        is ExtractionOutcome.EmergencyFallback -> fillFromExtraction(outcome.result)
                        is ExtractionOutcome.Rejected -> {
                            viewModel.savedRequiredSkills = emptyList()
                            viewModel.savedPreferredSkills = emptyList()
                            viewModel.savedEducationRequired = null
                            extractionError = "Extraction rejected: ${outcome.reason}"
                            jobDescription = rawText.take(3000)
                        }
                    }
                }.onFailure { error ->
                    viewModel.clearExtractionReview()
                    extractionError = error.message ?: "Extraction failed"
                    jobDescription = rawText.take(3000)
                }
                isExtracting = false
            }
        }
    }

    val fileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri == null) return@rememberLauncherForActivityResult
        isExtracting = true
        scope.launch {
            val mimeType = context.contentResolver.getType(uri) ?: ""

            if (mimeType.startsWith("image/")) {
                try {
                    val image = InputImage.fromFilePath(context, uri)
                    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                    recognizer.process(image)
                        .addOnSuccessListener { result -> extractAndFill(result.text) }
                        .addOnFailureListener { e ->
                            scope.launch { snackbar.showSnackbar("Image read failed: ${e.message}") }
                            isExtracting = false
                        }
                } catch (e: Exception) {
                    snackbar.showSnackbar("Image error: ${e.message}")
                    isExtracting = false
                }
                return@launch
            }

            val extracted = withContext(Dispatchers.IO) {
                try {
                    when (mimeType) {
                        "application/pdf" -> {
                            PDFBoxResourceLoader.init(context)
                            val inputStream = context.contentResolver.openInputStream(uri)
                            val doc = PDDocument.load(inputStream)
                            val text = PDFTextStripper().getText(doc)
                            doc.close()
                            text
                        }
                        "text/plain" -> {
                            context.contentResolver.openInputStream(uri)
                                ?.bufferedReader()?.readText() ?: ""
                        }
                        else -> null
                    }
                } catch (e: Exception) {
                    Log.e("JobInputScreen", "File error: ${e.message}")
                    null
                }
            }

            if (extracted != null) extractAndFill(extracted)
            else {
                snackbar.showSnackbar("Could not read file format")
                isExtracting = false
            }
        }
    }

    fun fetchUrl(url: String) {
        isExtracting = true
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    withTimeout(30_000L) {
                        val client = OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build()
                        val request = Request.Builder()
                            .url(url)
                            .header("User-Agent", "Mozilla/5.0")
                            .build()
                        val response = client.newCall(request).execute()
                        val html = response.body?.string() ?: ""
                        Jsoup.parse(html).text()
                    }
                } catch (e: TimeoutCancellationException) { null }
                catch (e: Exception) { null }
            }
            if (result != null) {
                extractAndFill(result)
                showUrlInput = false
                urlInput = ""
            } else {
                snackbar.showSnackbar("Could not fetch URL.")
                isExtracting = false
            }
        }
    }

    val screenTitle = when (mode) {
        GenerationMode.NEW_APPLICATION -> "New Application"
        GenerationMode.QUICK_ANALYSIS  -> "Quick Analysis"
        GenerationMode.QUICK_GENERATE  -> "Quick Generate"
        GenerationMode.IMPROVE         -> "Improve Application"
    }

    val buttonLabel = when {
        isLoading && mode == GenerationMode.QUICK_GENERATE -> "Generating..."
        isLoading && mode == GenerationMode.IMPROVE        -> "Re-analysing..."
        isLoading -> "Analysing..."
        mode == GenerationMode.QUICK_GENERATE -> "Generate CV & Cover Letter"
        mode == GenerationMode.QUICK_ANALYSIS -> "Analyse"
        mode == GenerationMode.IMPROVE        -> "Re-Analyse & Improve"
        else -> "Analyse & Continue"
    }

    Scaffold(
        containerColor = bgDark,
        topBar = {
            TopAppBar(
                title = { Text(screenTitle, color = Color(0xFFE0E0E0)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFFE0E0E0))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = cardDark)
            )
        },
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = when (mode) {
                    GenerationMode.NEW_APPLICATION -> "Full pipeline — analyse, generate, and log to history."
                    GenerationMode.QUICK_ANALYSIS  -> "Check your compatibility before committing to an application."
                    GenerationMode.QUICK_GENERATE  -> "Go straight to your tailored CV and cover letter."
                    GenerationMode.IMPROVE         -> "Re-analyse and generate an improved version using your ATS gaps."
                },
                color = Color.Gray,
                fontSize = 13.sp
            )

            extractionError?.let { message ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF3A1A1A))
                ) {
                    Text(
                        text = "Extraction failed: $message",
                        color = Color(0xFFFFCDD2),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            pendingReview?.let { review ->
                ExtractionReviewSurface(
                    review = review,
                    onConfirm = { corrected ->
                        fillFromExtraction(corrected)
                        viewModel.acknowledgeExtractionReview(corrected)
                    }
                )
            }

            OutlinedTextField(
                value = jobTitle,
                onValueChange = { jobTitle = it },
                label = { Text("Job Title") },
                enabled = pendingReview == null,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = teal,
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = teal,
                    unfocusedBorderColor = Color.Gray
                )
            )

            OutlinedTextField(
                value = company,
                onValueChange = { company = it },
                label = { Text("Company") },
                enabled = pendingReview == null,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = teal,
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = teal,
                    unfocusedBorderColor = Color.Gray
                )
            )

            OutlinedTextField(
                value = jobDescription,
                onValueChange = { jobDescription = it },
                label = { Text(if (isExtracting) "Extracting..." else "Job Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                maxLines = Int.MAX_VALUE,
                enabled = !isExtracting && pendingReview == null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = teal,
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = teal,
                    unfocusedBorderColor = Color.Gray
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("Import:", color = Color.Gray, fontSize = 12.sp)
                TextButton(
                    onClick = { showUrlInput = !showUrlInput },
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp)
                ) { Text("URL", color = teal, fontSize = 12.sp) }
                TextButton(
                    onClick = { fileLauncher.launch("application/pdf") },
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp)
                ) { Text("PDF", color = teal, fontSize = 12.sp) }
                TextButton(
                    onClick = { fileLauncher.launch("text/plain") },
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp)
                ) { Text("TXT", color = teal, fontSize = 12.sp) }
                TextButton(
                    onClick = { fileLauncher.launch("image/*") },
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp)
                ) { Text("JPEG", color = teal, fontSize = 12.sp) }
            }

            if (showUrlInput) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardDark)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = urlInput,
                            onValueChange = { urlInput = it },
                            label = { Text("Paste job posting URL") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedLabelColor = teal,
                                unfocusedLabelColor = Color.Gray,
                                focusedBorderColor = teal,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        Button(
                            onClick = {
                                if (urlInput.isNotBlank()) {
                                    val url = if (urlInput.startsWith("http")) urlInput
                                    else "https://$urlInput"
                                    fetchUrl(url)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = urlInput.isNotBlank() && !isExtracting,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = teal,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(if (isExtracting) "Fetching..." else "Fetch", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            val canGenerate = !isLoading && !isExtracting && pendingReview == null
                    && jobTitle.isNotBlank()
                    && company.isNotBlank()
                    && jobDescription.isNotBlank()
            val generate: (GenerationRequest) -> Unit = { request ->
                if (pendingReview == null) {
                    viewModel.runAnalysis(
                        profile = profile,
                        jobTitle = jobTitle,
                        company = company,
                        jobDescription = jobDescription,
                        mode = mode,
                        improvementContext = viewModel.improvementContext,
                        requiredSkills = viewModel.savedRequiredSkills,
                        preferredSkills = viewModel.savedPreferredSkills,
                        educationRequired = viewModel.savedEducationRequired,
                        request = request
                    )
                }
            }
            val genButtonColors = ButtonDefaults.buttonColors(
                containerColor = teal,
                contentColor = Color.Black,
                disabledContainerColor = teal.copy(alpha = 0.4f),
                disabledContentColor = Color.Black
            )
            if (mode == GenerationMode.QUICK_GENERATE) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { generate(GenerationRequest.COVER_LETTER_ONLY) },
                        enabled = canGenerate,
                        modifier = Modifier.weight(1f),
                        colors = genButtonColors
                    ) { Text("Cover Letter", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    Button(
                        onClick = { generate(GenerationRequest.BOTH) },
                        enabled = canGenerate,
                        modifier = Modifier.weight(1f),
                        colors = genButtonColors
                    ) { Text("CV & Letter", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    Button(
                        onClick = { generate(GenerationRequest.CV_ONLY) },
                        enabled = canGenerate,
                        modifier = Modifier.weight(1f),
                        colors = genButtonColors
                    ) { Text("CV", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                }
            } else {
                Button(
                    onClick = { generate(GenerationRequest.BOTH) },
                    enabled = canGenerate,
                    modifier = Modifier.fillMaxWidth(),
                    colors = genButtonColors
                ) {
                    Text(buttonLabel, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}