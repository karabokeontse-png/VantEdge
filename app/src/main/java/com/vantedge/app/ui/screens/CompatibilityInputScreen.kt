package com.vantedge.app.ui.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.viewmodel.CompatibilityUiState
import com.vantedge.app.data.viewmodel.CompatibilityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompatibilityInputScreen(
    viewModel: CompatibilityViewModel,
    profile: UserProfile,
    onNavigateBack: () -> Unit,
    onNavigateToResult: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }

    var jobTitle by remember { mutableStateOf(viewModel.savedJobTitle) }
    var company by remember { mutableStateOf(viewModel.savedCompany) }
    var jobDescription by remember { mutableStateOf(viewModel.savedJobDescription) }
    var urlInput by remember { mutableStateOf("") }
    var showUrlInput by remember { mutableStateOf(false) }
    var isExtracting by remember { mutableStateOf(false) }

    val teal = Color(0xFF00BFA5)
    val isLoading = uiState is CompatibilityUiState.Loading

    LaunchedEffect(jobTitle) { viewModel.savedJobTitle = jobTitle }
    LaunchedEffect(company) { viewModel.savedCompany = company }
    LaunchedEffect(jobDescription) { viewModel.savedJobDescription = jobDescription }

    LaunchedEffect(uiState) {
        if (uiState is CompatibilityUiState.Success) {
            onNavigateToResult()
        }
    }

    val fileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri == null) return@rememberLauncherForActivityResult
        isExtracting = true
        scope.launch {
            val mimeType = context.contentResolver.getType(uri) ?: ""
            val extracted = withContext(Dispatchers.IO) {
                try {
                    when {
                        mimeType == "application/pdf" -> {
                            PDFBoxResourceLoader.init(context)
                            val inputStream = context.contentResolver.openInputStream(uri)
                            val doc = PDDocument.load(inputStream)
                            val text = PDFTextStripper().getText(doc)
                            doc.close()
                            text
                        }
                        mimeType == "text/plain" -> {
                            context.contentResolver.openInputStream(uri)
                                ?.bufferedReader()?.readText() ?: ""
                        }
                        mimeType.startsWith("image/") -> null
                        else -> null
                    }
                } catch (e: Exception) {
                    Log.e("CompatibilityInput", "File error: ${e.message}")
                    null
                }
            }

            if (mimeType.startsWith("image/")) {
                try {
                    val image = InputImage.fromFilePath(context, uri)
                    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                    recognizer.process(image)
                        .addOnSuccessListener { result ->
                            val rawText = result.text
                            scope.launch {
                                viewModel.extractFieldsFromText(rawText) { title, comp, desc ->
                                    if (title != null) jobTitle = title
                                    if (comp != null) company = comp
                                    jobDescription = desc ?: rawText
                                    isExtracting = false
                                }
                            }
                        }
                        .addOnFailureListener { e ->
                            scope.launch { snackbar.showSnackbar("Image read failed: ${e.message}") }
                            isExtracting = false
                        }
                } catch (e: Exception) {
                    snackbar.showSnackbar("Image error: ${e.message}")
                    isExtracting = false
                }
            } else {
                if (extracted != null) {
                    viewModel.extractFieldsFromText(extracted) { title, comp, desc ->
                        if (title != null) jobTitle = title
                        if (comp != null) company = comp
                        jobDescription = desc ?: extracted
                        isExtracting = false
                    }
                } else {
                    snackbar.showSnackbar("Could not read file format")
                    isExtracting = false
                }
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
                    null
                }
            }
            if (result != null) {
                viewModel.extractFieldsFromText(result) { title, comp, desc ->
                    if (title != null) jobTitle = title
                    if (comp != null) company = comp
                    jobDescription = desc ?: result.take(3000)
                    showUrlInput = false
                    urlInput = ""
                    isExtracting = false
                }
            } else {
                snackbar.showSnackbar("Could not fetch URL.")
                isExtracting = false
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Job Compatibility Check") }) },
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
                "Find out how compatible you are with a job before applying.",
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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Import from:", color = Color.Gray, fontSize = 12.sp)
                TextButton(onClick = { showUrlInput = !showUrlInput }) {
                    Text("URL", color = teal, fontSize = 12.sp)
                }
                TextButton(onClick = { fileLauncher.launch("application/pdf") }) {
                    Text("PDF", color = teal, fontSize = 12.sp)
                }
                TextButton(onClick = { fileLauncher.launch("text/plain") }) {
                    Text("TXT", color = teal, fontSize = 12.sp)
                }
                TextButton(onClick = { fileLauncher.launch("image/*") }) {
                    Text("Image", color = teal, fontSize = 12.sp)
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

            val fieldsValid = jobTitle.isNotBlank() &&
                company.isNotBlank() &&
                jobDescription.isNotBlank()

            Button(
                onClick = {
                    if (fieldsValid) {
                        viewModel.analyze(
                            profile = profile,
                            jobTitle = jobTitle,
                            company = company,
                            jobDescription = jobDescription
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
                    if (isLoading) "Analyzing..." else "Analyze My Compatibility",
                    fontWeight = FontWeight.Bold
                )
            }

            if (uiState is CompatibilityUiState.Error) {
                val message = (uiState as CompatibilityUiState.Error).message
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