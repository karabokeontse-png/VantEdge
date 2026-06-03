package com.vantedge.app.ui.screens

import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.vantedge.app.data.viewmodel.GeneratorViewModel
import kotlinx.coroutines.launch
import java.io.File

private val PBg = Color(0xFF0D0D0D)
private val PCard = Color(0xFF1A1A2E)
private val PTeal = Color(0xFF00BFA5)
private val PAmber = Color(0xFFFFB830)
private val POnBg = Color(0xFFE0E0E0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentPreviewScreen(
    navController: NavController,
    viewModel: GeneratorViewModel
) {
    val cvHtml = viewModel.lastCv
    val coverLetterHtml = viewModel.lastCoverLetter
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("CV", "Cover Letter")
    val clipboard = LocalClipboardManager.current
    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }
    val context = LocalContext.current

    val currentContent = if (selectedTab == 0) cvHtml else coverLetterHtml
    val currentLabel = if (selectedTab == 0) "CV" else "CoverLetter"
    val isHtml = currentContent.trimStart().startsWith("<")

    var webViewRef by remember { mutableStateOf<WebView?>(null) }

    fun savePdf() {
        val webView = webViewRef
        if (webView == null) {
            scope.launch { snackbar.showSnackbar("Document not ready yet") }
            return
        }
        try {
            val fileName = "VantEdge_${currentLabel}_${System.currentTimeMillis()}"
            val printManager = context.getSystemService(
                android.content.Context.PRINT_SERVICE
            ) as android.print.PrintManager
            val printAdapter = webView.createPrintDocumentAdapter(fileName)
            val attributes = android.print.PrintAttributes.Builder()
                .setMediaSize(android.print.PrintAttributes.MediaSize.ISO_A4)
                .setResolution(android.print.PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(android.print.PrintAttributes.Margins.NO_MARGINS)
                .build()
            printManager.print(fileName, printAdapter, attributes)
        } catch (e: Exception) {
            scope.launch { snackbar.showSnackbar("Error: ${e.message}") }
        }
    }

    fun saveDocx() {
        scope.launch {
            try {
                val fileName = "VantEdge_${currentLabel}_${System.currentTimeMillis()}.txt"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val values = ContentValues().apply {
                        put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                        put(MediaStore.Downloads.MIME_TYPE, "text/plain")
                        put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                    }
                    val uri = context.contentResolver.insert(
                        MediaStore.Downloads.EXTERNAL_CONTENT_URI, values
                    )
                    uri?.let {
                        context.contentResolver.openOutputStream(it)?.use { stream ->
                            stream.write(currentContent.toByteArray())
                        }
                    }
                } else {
                    val downloads = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS
                    )
                    File(downloads, fileName).writeText(currentContent)
                }
                snackbar.showSnackbar("Saved to Downloads — open in Word or Google Docs")
            } catch (e: Exception) {
                snackbar.showSnackbar("Save failed: ${e.message}")
            }
        }
    }

    Scaffold(
        containerColor = PBg,
        snackbarHost = { SnackbarHost(snackbar) },
        topBar = {
            TopAppBar(
                title = {
                    Text("Documents Preview", color = POnBg, fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.resetState()
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = POnBg)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        clipboard.setText(AnnotatedString(currentContent))
                        scope.launch { snackbar.showSnackbar("Copied to clipboard") }
                    }) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = PTeal)
                    }
                    IconButton(onClick = {
                        if (isHtml) savePdf() else saveDocx()
                    }) {
                        Icon(Icons.Default.PictureAsPdf, contentDescription = "Save PDF", tint = PTeal)
                    }
                    IconButton(onClick = {
                        viewModel.resetState()
                        navController.navigate("dashboard") {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.Home, contentDescription = "Dashboard", tint = PTeal)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PCard)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = selectedTab == index
                    Button(
                        onClick = { selectedTab = index },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) PTeal else PCard,
                            contentColor = if (isSelected) Color.Black else Color.White
                        )
                    ) {
                        Text(
                            title,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            if (currentContent.isBlank()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No content available.", color = Color.Gray)
                }
            } else if (isHtml) {
                AndroidView(
                    factory = { ctx ->
                        val webView = WebView(ctx)
                        webView.webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                webViewRef = view
                            }
                        }
                        webView.settings.javaScriptEnabled = false
                        webView.settings.builtInZoomControls = true
                        webView.settings.displayZoomControls = false
                        webView.settings.useWideViewPort = true
                        webView.settings.loadWithOverviewMode = true
                        webView.setBackgroundColor(android.graphics.Color.WHITE)
                        webView
                    },
                    update = { webView ->
                        webView.loadDataWithBaseURL(
                            null, currentContent, "text/html", "UTF-8", null
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(PCard)
                        .border(1.dp, PTeal.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        Text(
                            text = currentContent,
                            color = POnBg,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            lineHeight = 22.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = PAmber.copy(alpha = 0.15f)
                            )
                        ) {
                            Text(
                                text = "This is your editable CV. Tap the copy icon above and paste into Microsoft Word or Google Docs.",
                                color = PAmber,
                                modifier = Modifier.padding(12.dp),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}