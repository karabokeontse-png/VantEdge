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
import com.vantedge.app.data.engine.JobExtractionEngine
import com.vantedge.app.data.model.JobSourceType
import com.vantedge.app.data.model.GenerationMode
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.viewmodel.CycleUiState
import com.vantedge.app.data.viewmodel.CycleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    onNavigateBack: () -> Unit,
    onNavigateToResult: () -> Unit,
    onNavigateToDesign: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
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

    val isLoading = uiState is CycleUiState.Loading

    LaunchedEffect(jobTitle) { viewModel.savedJobTitle = jobTitle }
    LaunchedEffect(company) { viewModel.savedCompany = company }
    LaunchedEffect(jobDescription) { viewModel.savedJobDescription = jobDescription }

    LaunchedEffect(uiState) {
        when (uiState) {
            is CycleUiState.AnalysisDone -> {
                val cycle = (uiState as CycleUiState.AnalysisDone).cycle
                when (mode) {
                    GenerationMode.QUICK_GENERATE -> {
                        viewModel.setCurrentCycle(cycle)
                        onNavigateToDesign()
                    }
                    GenerationMode.NEW_APPLICATION,
                    GenerationMode.QUICK_ANALYSIS,
                    GenerationMode.IMPROVE -> {
                        onNavigateToResult()
                    }
                }
            }
            is CycleUiState.Success -> {
                onNavigateToResult()
            }
            else -> Unit
        }
    }

    fun extractAndFill(rawText: String) {
        isExtracting = true
        scope.launch(Dispatchers.IO) {
            val result = JobExtractionEngine().extractJob(rawText, JobSourceType.USER_INPUT)
            result.onSuccess { extraction ->
                scope.launch(Dispatchers.Main) {
                    if (extraction.jobTitle != null) jobTitle = extraction.jobTitle
                    if (extraction.company != null) company = extraction.company
                    jobDescription = extraction.description
                    isExtracting = false
                }
            }.onFailure {
                scope.launch(Dispatchers.Main) {
                    jobDescription = rawText.take(3000)
                    isExtracting = false
                }
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
                } catch (e: Exception) { null }
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

            OutlinedTextField(
                value = jobTitle,
                onValueChange = { jobTitle = it },
                label = { Text("Job Title") },
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
                enabled = !isExtracting,
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

            Button(
                onClick = {
                    viewModel.runAnalysis(
                        profile = profile,
                        jobTitle = jobTitle,
                        company = company,
                        jobDescription = jobDescription,
                        mode = mode,
                        improvementContext = viewModel.improvementContext
                    )
                },
                enabled = !isLoading && !isExtracting
                        && jobTitle.isNotBlank()
                        && company.isNotBlank()
                        && jobDescription.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = teal,
                    contentColor = Color.Black,
                    disabledContainerColor = teal.copy(alpha = 0.4f),
                    disabledContentColor = Color.Black
                )
            ) {
                Text(buttonLabel, fontWeight = FontWeight.Bold)
            }
        }
    }
}