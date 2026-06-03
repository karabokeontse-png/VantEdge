package com.vantedge.app.ui.screens

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.CycleState
import com.vantedge.app.data.model.GapItem
import com.vantedge.app.data.model.GenerationCycle
import com.vantedge.app.data.viewmodel.CycleViewModel
import kotlinx.coroutines.launch
import java.io.File

private val RBg = Color(0xFF0D0D0D)
private val RCard = Color(0xFF1A1A2E)
private val RTeal = Color(0xFF00BFA5)
private val RAmber = Color(0xFFFFB830)
private val ROnBg = Color(0xFFE0E0E0)

sealed class ResultScreenMode {
    data class Live(val cycle: GenerationCycle) : ResultScreenMode()
    data class Historical(val cycle: GenerationCycle) : ResultScreenMode()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    navController: NavController,
    viewModel: CycleViewModel,
    mode: ResultScreenMode
) {
    val cycle = when (mode) {
        is ResultScreenMode.Live -> mode.cycle
        is ResultScreenMode.Historical -> mode.cycle
    }
    val isHistorical = mode is ResultScreenMode.Historical
    val fullCycle = cycle.state as? CycleState.FullCycle
    val compatibility: CompatibilityRecord? = when (val s = cycle.state) {
        is CycleState.FullCycle -> s.compatibility
        is CycleState.AnalysisOnly -> s.compatibility
        is CycleState.GenerationReady -> s.compatibility
    }
    val cvContent = fullCycle?.cvContent ?: ""
    val coverLetterContent = fullCycle?.coverLetterContent ?: ""
    
    // Analysis-only cycles only show the Analysis tab
    val tabs = if (fullCycle != null)
        listOf("CV", "Cover Letter", "Analysis")
    else
        listOf("Analysis")
        
    var selectedTab by remember { mutableStateOf(0) }
    val clipboard = LocalClipboardManager.current
    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }
    val context = LocalContext.current
    var webViewRef by remember { mutableStateOf<WebView?>(null) }
    val currentDocContent = if (selectedTab == 0) cvContent else coverLetterContent
    val score = compatibility?.score ?: 0
    val scoreColor = when {
        score >= 75 -> Color(0xFF4CAF50)
        score >= 50 -> RAmber
        else -> Color(0xFFE53935)
    }
    val scoreLabel = when {
        score >= 80 -> "Strong match"
        score >= 60 -> "Decent match"
        score >= 40 -> "Partial match"
        else -> "Weak match"
    }
    val previousScore = viewModel.getPreviousScore(cycle)
    val delta = if (previousScore != null) score - previousScore else null

    fun savePdf(label: String) {
        val webView = webViewRef ?: run {
            scope.launch { snackbar.showSnackbar("Document not ready yet") }
            return
        }
        try {
            val fileName = "VantEdge_${label}_${System.currentTimeMillis()}"
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

    fun saveDocx(content: String, label: String) {
        scope.launch {
            try {
                val fileName = "VantEdge_${label}_${System.currentTimeMillis()}.txt"
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
                        context.contentResolver.openOutputStream(it)?.use { s ->
                            s.write(content.toByteArray())
                        }
                    }
                } else {
                    val downloads = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS
                    )
                    File(downloads, fileName).writeText(content)
                }
                snackbar.showSnackbar("Saved to Downloads")
            } catch (e: Exception) {
                snackbar.showSnackbar("Save failed: ${e.message}")
            }
        }
    }

    Scaffold(
        containerColor = RBg,
        snackbarHost = { SnackbarHost(snackbar) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            cycle.jobTitle,
                            color = ROnBg,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                        // Company only — no version number shown to user
                        Text(
                            cycle.company,
                            color = Color.Gray,
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.resetState()
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = ROnBg)
                    }
                },
                actions = {
                    if (fullCycle != null && selectedTab < 2) {
                        IconButton(onClick = {
                            clipboard.setText(AnnotatedString(currentDocContent))
                            scope.launch { snackbar.showSnackbar("Copied to clipboard") }
                        }) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = RTeal)
                        }
                        IconButton(onClick = {
                            val label = if (selectedTab == 0) "CV" else "CoverLetter"
                            savePdf(label)
                        }) {
                            Icon(Icons.Default.PictureAsPdf, contentDescription = "Save PDF", tint = RTeal)
                        }
                    }
                    IconButton(onClick = {
                        viewModel.resetState()
                        navController.navigate("dashboard") {
                            popUpTo("dashboard") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.Home, contentDescription = "Home", tint = RTeal)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = RCard)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ── Tab row ────────────────────────────────────────────────
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
                            containerColor = if (isSelected) RTeal else RCard,
                            contentColor = if (isSelected) Color.Black else Color.White
                        )
                    ) {
                        Text(
                            title,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    }
                }
            }
            // ── Content ────────────────────────────────────────────────
            Box(modifier = Modifier.weight(1f)) {
                when (selectedTab) {
                    0, 1 -> {
                        val content = if (selectedTab == 0) cvContent else coverLetterContent
                        if (content.isBlank()) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("No content available.", color = Color.Gray)
                            }
                        } else {
                            AndroidView(
                                factory = { ctx ->
                                    WebView(ctx).also { wv ->
                                        wv.webViewClient = object : WebViewClient() {
                                            override fun onPageFinished(view: WebView?, url: String?) {
                                                webViewRef = view
                                            }
                                        }
                                        wv.settings.javaScriptEnabled = false
                                        wv.settings.builtInZoomControls = true
                                        wv.settings.displayZoomControls = false
                                        wv.settings.useWideViewPort = true
                                        wv.settings.loadWithOverviewMode = true
                                        wv.setBackgroundColor(android.graphics.Color.WHITE)
                                    }
                                },
                                update = { wv ->
                                    wv.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null)
                                },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    // Analysis tab — always last tab index
                    else -> {
                        if (compatibility != null) {
                            AnalysisTab(
                                compatibility = compatibility,
                                scoreColor = scoreColor,
                                scoreLabel = scoreLabel,
                                delta = delta
                            )
                        } else {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("No analysis data available.", color = Color.Gray)
                            }
                        }
                    }
                }
            }
            // ── Bottom bar (live) ──────────────────────────────────────
            if (!isHistorical) {
                Surface(color = RCard, tonalElevation = 4.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                scope.launch {
                                    viewModel.dismissCurrentCycle()
                                    viewModel.resetState()
                                    navController.navigate("dashboard") {
                                        popUpTo("dashboard") { inclusive = true }
                                    }
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                            border = BorderStroke(1.dp, Color.Gray)
                        ) {
                            Text("Dismiss", fontSize = 14.sp)
                        }
                        Button(
                            onClick = {
                                scope.launch {
                                    viewModel.commitCurrentCycle()
                                    viewModel.resetState()
                                    navController.navigate("dashboard") {
                                        popUpTo("dashboard") { inclusive = true }
                                    }
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RTeal,
                                contentColor = Color.Black
                            )
                        ) {
                            Text("Save Application", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
            // ── Bottom bar (historical) ────────────────────────────────
            if (isHistorical) {
                Surface(color = RCard, tonalElevation = 4.dp) {
                    Button(
                        onClick = {
                            viewModel.startImproveFromCycle(cycle)
                            // FIX: was navigate("loading") — "loading" does not exist in NavHost.
                            // "new_application" is the correct destination: startImproveFromCycle
                            // pre-loads the job context into CycleViewModel, then JobInputScreen
                            // picks it up via GenerationMode.NEW_APPLICATION.
                            //
                            // FIX: popUpTo was "cyclehistory" — route does not exist.
                            // Correct route name is "history". inclusive=false keeps the
                            // history screen in the back stack so Back from new_application
                            // returns to history.
                            navController.navigate("new_application") {
                                popUpTo("history") { inclusive = false }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RTeal,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            "⚡ Improve This Application",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ANALYSIS TAB
// Default: score + "About this role" + "How you fit"
// Advanced (collapsed): gaps, relevancy, courses
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun AnalysisTab(
    compatibility: CompatibilityRecord,
    scoreColor: Color,
    scoreLabel: String,
    delta: Int?
) {
    val sweep by animateFloatAsState(
        targetValue = (compatibility.score / 100f) * 270f,
        animationSpec = tween(1000),
        label = ""
    )
    var showAdvanced by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 40.dp)
    ) {
        // ── Score circle ──────────────────────────────────────────────
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(130.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val stroke = 14.dp.toPx()
                        val pad = stroke / 2
                        drawArc(
                            color = Color.DarkGray,
                            startAngle = 135f,
                            sweepAngle = 270f,
                            useCenter = false,
                            topLeft = Offset(pad, pad),
                            size = Size(size.width - stroke, size.height - stroke),
                            style = Stroke(stroke, cap = StrokeCap.Round)
                        )
                        drawArc(
                            color = scoreColor,
                            startAngle = 135f,
                            sweepAngle = sweep,
                            useCenter = false,
                            topLeft = Offset(pad, pad),
                            size = Size(size.width - stroke, size.height - stroke),
                            style = Stroke(stroke, cap = StrokeCap.Round)
                        )
                    }
                    Text(
                        "${compatibility.score}%",
                        color = scoreColor,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    scoreLabel,
                    color = scoreColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                if (delta != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    val deltaColor = when {
                        delta > 0 -> Color(0xFF4CAF50)
                        delta < 0 -> Color(0xFFE53935)
                        else -> Color.Gray
                    }
                    val deltaText = when {
                        delta > 0 -> "↑ Up $delta% from your last attempt"
                        delta < 0 -> "↓ Down ${kotlin.math.abs(delta)}% from your last attempt"
                        else -> "→ Same score as your last attempt"
                    }
                    Surface(
                        color = deltaColor.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            deltaText,
                            color = deltaColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        // ── About this role ───────────────────────────────────────────
        if (compatibility.roleSummary.isNotBlank()) {
            item {
                AnalysisSectionCard(
                    heading = "About this role",
                    body = compatibility.roleSummary
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        // ── How you fit ───────────────────────────────────────────────
        if (compatibility.eligibilitySummary.isNotBlank()) {
            item {
                AnalysisSectionCard(
                    heading = "How you fit",
                    body = compatibility.eligibilitySummary
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        // ── Advanced toggle ───────────────────────────────────────────
        item {
            Divider(
                color = Color(0xFF2A2A3E),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showAdvanced = !showAdvanced }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "Advanced analysis",
                        color = RTeal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        "Gaps, skill relevancy and course recommendations",
                        color = Color.Gray,
                        fontSize = 11.sp
                    )
                }
                Icon(
                    imageVector = if (showAdvanced) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = RTeal
                )
            }
            Divider(
                color = Color(0xFF2A2A3E),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        // ── Advanced content ──────────────────────────────────────────
        if (showAdvanced) {
            // Critical gap callout
            if (compatibility.criticalGapCount > 0) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        color = Color(0xFFE53935).copy(alpha = 0.10f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "You have ${compatibility.criticalGapCount} critical " +
                            "${if (compatibility.criticalGapCount == 1) "gap" else "gaps"} " +
                            "that this role specifically asks for.",
                            color = Color(0xFFE53935),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
            // Gaps
            if (compatibility.gaps.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "What you're missing",
                        color = Color.Gray,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
                    )
                }
                items(compatibility.gaps) { gap ->
                    GapCard(gap)
                }
            }
            // Relevancy
            if (compatibility.relevancyItems.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "What's working for you",
                        color = Color.Gray,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
                    )
                }
                items(compatibility.relevancyItems) { relevancyItem ->
                    val chipColor = when (relevancyItem.relevancyGroup) {
                        "HIGH" -> Color(0xFF4CAF50)
                        "MEDIUM" -> RAmber
                        else -> Color.Gray
                    }
                    val groupLabel = when (relevancyItem.relevancyGroup) {
                        "HIGH" -> "Strong match"
                        "MEDIUM" -> "Moderate match"
                        else -> "Weak match"
                    }
                    RelevancyCard(
                        name = relevancyItem.name,
                        matchPct = relevancyItem.matchPercent,
                        description = relevancyItem.aiDescription,
                        groupLabel = groupLabel,
                        color = chipColor
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Sub-composables
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun AnalysisSectionCard(heading: String, body: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            heading,
            color = RTeal,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = RCard),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = body,
                color = ROnBg,
                fontSize = 14.sp,
                lineHeight = 22.sp,
                modifier = Modifier.padding(14.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GapCard(gap: GapItem) {
    val context = LocalContext.current
    val importanceColor = when (gap.importance) {
        "CRITICAL" -> Color(0xFFE53935)
        "IMPORTANT" -> RAmber
        else -> Color.Gray
    }
    val importanceLabel = when (gap.importance) {
        "CRITICAL" -> "Critical gap"
        "IMPORTANT" -> "Worth addressing"
        else -> "Nice to have"
    }
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = RCard),
        shape = RoundedCornerShape(10.dp),
        onClick = { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        gap.skill,
                        color = ROnBg,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        importanceLabel,
                        color = importanceColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    gap.description,
                    color = ROnBg,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
                if (gap.courses.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Where to learn this:",
                        color = Color.Gray,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    gap.courses.forEach { course ->
                        val categoryColor = when (course.category) {
                            "free" -> Color(0xFF4CAF50)
                            "discounted" -> RAmber
                            else -> Color.Gray
                        }
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clickable {
                                    runCatching {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(course.url))
                                        context.startActivity(intent)
                                    }
                                },
                            color = Color(0xFF111122),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        course.title,
                                        color = ROnBg,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        "${course.provider} · ${course.estimatedDuration}",
                                        color = Color.Gray,
                                        fontSize = 11.sp
                                    )
                                }
                                Text(
                                    if (course.category == "free") "Free"
                                    else course.category.replaceFirstChar { it.uppercase() },
                                    color = categoryColor,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RelevancyCard(
    name: String,
    matchPct: Int,
    description: String,
    groupLabel: String,
    color: Color
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 3.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.08f)),
        shape = RoundedCornerShape(8.dp),
        onClick = { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        name,
                        color = ROnBg,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        groupLabel,
                        color = color,
                        fontSize = 11.sp
                    )
                }
                Text(
                    "$matchPct%",
                    color = color,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            if (expanded && description.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    description,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}
/* ============================================================
   APPEND-ONLY EXTENSION BLOCK (SAFE - NO EXISTING MODIFICATION)
   ============================================================ */

/**
 * Cycle analysis hook (non-invasive)
 * Purpose: future integration point for Application History System (Phase 3)
 */
fun CycleViewModel.captureCycleSnapshot(cycle: GenerationCycle) {
    // No-op placeholder
    // Will be wired to HistoryStore in Phase 3 persistence layer
}

/**
 * Safe debug tracer for ResultScreen state (does not affect UI)
 */
fun logResultScreenState(
    cycleId: String?,
    selectedTab: Int,
    isHistorical: Boolean
) {
    // No-op placeholder for Logcat integration later
}

/**
 * Utility: safe HTML check (used for WebView validation extensions later)
 */
fun String.isValidHtmlContent(): Boolean {
    return this.contains("<html", ignoreCase = true) ||
           this.contains("<body", ignoreCase = true) ||
           this.contains("<div", ignoreCase = true)
}

/**
 * Future hook: score trend computation (History System dependency)
 */
fun calculateScoreDelta(current: Int, previous: Int?): Int? {
    return previous?.let { current - it }
}

/**
 * Future hook: application status tagging for History System
 */
fun resolveApplicationStatus(score: Int): String {
    return when {
        score >= 75 -> "STRONG"
        score >= 50 -> "MODERATE"
        score >= 30 -> "WEAK"
        else -> "CRITICAL"
    }
}
