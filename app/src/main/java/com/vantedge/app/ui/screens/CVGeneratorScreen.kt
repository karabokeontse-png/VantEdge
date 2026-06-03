package com.vantedge.app.ui.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import com.vantedge.app.data.engine.GeneratorEngine
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.viewmodel.GeneratorUiState
import com.vantedge.app.data.viewmodel.GeneratorViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CVGeneratorScreen(
    viewModel: GeneratorViewModel,
    profile: UserProfile,
    designId: String,
    schemeId: String,
    onNavigateBack: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToPreview: () -> Unit,
    onDocxSaved: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToCompatibility: () -> Unit,
    onNavigateToEditProfile: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }
    val engine = remember { GeneratorEngine() }

    var jobTitle by remember {
        mutableStateOf(
            if (viewModel.prefilledJobTitle.isNotBlank()) viewModel.prefilledJobTitle
            else viewModel.savedJobTitle
        )
    }
    var company by remember {
        mutableStateOf(
            if (viewModel.prefilledCompany.isNotBlank()) viewModel.prefilledCompany
            else viewModel.savedCompany
        )
    }
    var jobDescription by remember {
        mutableStateOf(
            if (viewModel.prefilledJobDescription.isNotBlank()) viewModel.prefilledJobDescription
            else viewModel.savedJobDescription
        )
    }

    var urlInput by remember { mutableStateOf("") }
    var showUrlInput by remember { mutableStateOf(false) }
    var isExtracting by remember { mutableStateOf(false) }
    var showDocxSuccess by remember { mutableStateOf(false) }

    val teal = Color(0xFF00BFA5)
    val amber = Color(0xFFFFB830)

    LaunchedEffect(jobTitle) { viewModel.savedJobTitle = jobTitle }
    LaunchedEffect(company) { viewModel.savedCompany = company }
    LaunchedEffect(jobDescription) { viewModel.savedJobDescription = jobDescription }

    LaunchedEffect(uiState) {
        when (uiState) {
            is GeneratorUiState.Success -> {
                val success = uiState as GeneratorUiState.Success
                if (success.cv.trimStart().startsWith("<")) {
                    onNavigateToPreview()
                } else {
                    showDocxSuccess = true
                }
            }
            else -> {}
        }
    }

    // DOCX success screen
    if (showDocxSuccess) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = teal,
                modifier = Modifier.size(72.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Your CV has been saved!",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Find it in your Downloads folder. Open with Microsoft Word or Google Docs to edit.",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    showDocxSuccess = false
                    viewModel.resetState()
                    onNavigateToDashboard()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = teal, contentColor = Color.Black)
            ) { Text("Go to Dashboard", fontWeight = FontWeight.Bold) }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    showDocxSuccess = false
                    viewModel.resetState()
                    onNavigateToHistory()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A2E), contentColor = Color.White)
            ) { Text("View History") }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    showDocxSuccess = false
                    viewModel.resetState()
                    onNavigateToCompatibility()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A2E), contentColor = Color.White)
            ) { Text("Check Compatibility for Another Job") }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    showDocxSuccess = false
                    viewModel.resetState()
                    jobTitle = ""
                    company = ""
                    jobDescription = ""
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A2E), contentColor = Color.White)
            ) { Text("Generate Another Document") }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    showDocxSuccess = false
                    viewModel.resetState()
                    onNavigateToEditProfile()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A2E), contentColor = Color.White)
            ) { Text("Edit Profile") }
        }
        return
    }

    fun extractAndFill(rawText: String) {
        isExtracting = true
        scope.launch(Dispatchers.IO) {
            engine.extractJobFields(rawText) { title, comp, desc ->
                scope.launch(Dispatchers.Main) {
                    if (title != null) jobTitle = title
                    if (comp != null) company = comp
                    jobDescription = desc ?: rawText.take(3000)
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
                    Log.e("CVGenerator", "File extraction error: ${e.message}")
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
                } catch (e: Exception) {
                    Log.e("CVGenerator", "URL fetch error: ${e.message}")
                    null
                }
            }
            if (result != null) {
                extractAndFill(result)
                showUrlInput = false
                urlInput = ""
            } else {
                snackbar.showSnackbar("Could not fetch URL. Check the link and try again.")
                isExtracting = false
            }
        }
    }

    val fieldsValid = jobTitle.isNotBlank() && company.isNotBlank() && jobDescription.isNotBlank()
    val isLoading = uiState is GeneratorUiState.Loading

    Scaffold(
        topBar = { TopAppBar(title = { Text("CV Generator") }) },
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
                label = {
                    Text(
                        if (isExtracting) "Extracting content..."
                        else "Paste Job Description Here"
                    )
                },
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
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Import:", color = Color.Gray, fontSize = 12.sp)
                TextButton(onClick = { showUrlInput = !showUrlInput }) {
                    Text("URL", color = teal, fontSize = 12.sp)
                }
                TextButton(onClick = { fileLauncher.launch("application/pdf") }) {
                    Text("PDF", color = teal, fontSize = 12.sp)
                }
                TextButton(onClick = { fileLauncher.launch("text/plain") }) {
                    Text("TXT", color = teal, fontSize = 12.sp)
                }
                TextButton(onClick = { fileLauncher.launch("image/jpeg") }) {
                    Text("JPEG", color = teal, fontSize = 12.sp)
                }
            }

            if (showUrlInput) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E))
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
                            Text(
                                if (isExtracting) "Fetching..." else "Fetch",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (fieldsValid) {
                        viewModel.generate(
                            profile = profile,
                            jobTitle = jobTitle,
                            company = company,
                            jobDescription = jobDescription,
                            mode = "html",
                            designId = designId,
                            schemeId = schemeId,
                            context = context
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = fieldsValid && !isLoading && !isExtracting,
                colors = ButtonDefaults.buttonColors(
                    containerColor = teal,
                    contentColor = Color.Black,
                    disabledContainerColor = teal.copy(alpha = 0.5f),
                    disabledContentColor = Color.Black
                )
            ) {
                Text(
                    if (isLoading && viewModel.currentMode == "html") "Generating PDF..."
                    else "Generate as PDF",
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = {
                    if (fieldsValid) {
                        viewModel.generate(
                            profile = profile,
                            jobTitle = jobTitle,
                            company = company,
                            jobDescription = jobDescription,
                            mode = "docx",
                            designId = designId,
                            schemeId = schemeId,
                            context = context
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = fieldsValid && !isLoading && !isExtracting,
                colors = ButtonDefaults.buttonColors(
                    containerColor = amber,
                    contentColor = Color.Black,
                    disabledContainerColor = amber.copy(alpha = 0.5f),
                    disabledContentColor = Color.Black
                )
            ) {
                Text(
                    if (isLoading && viewModel.currentMode == "docx") "Generating..."
                    else "Generate as Editable Word File",
                    fontWeight = FontWeight.Bold
                )
            }

            if (uiState is GeneratorUiState.Error) {
                val message = (uiState as GeneratorUiState.Error).message
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = "Error: $message",
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                )
            ) {
                Text("Back")
            }
        }
    }
}
