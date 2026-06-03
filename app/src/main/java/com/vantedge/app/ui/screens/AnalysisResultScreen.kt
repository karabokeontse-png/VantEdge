package com.vantedge.app.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.CycleState
import com.vantedge.app.data.model.GapItem
import com.vantedge.app.data.model.GenerationCycle
import com.vantedge.app.data.model.RelevancyItem
import com.vantedge.app.ui.theme.AppColors

enum class AnalysisScreenMode {
    Live,
    HistoricalReadOnly,
    HistoricalContinuation
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisResultScreen(
    cycle: GenerationCycle,
    mode: AnalysisScreenMode = AnalysisScreenMode.Live,
    onGenerateCv: () -> Unit,
    onAdvancedAnalysis: () -> Unit,
    onDismiss: () -> Unit,
    onBack: () -> Unit
) {
    val compatibility: CompatibilityRecord? = when (val s = cycle.state) {
        is CycleState.FullCycle -> s.compatibility
        is CycleState.AnalysisOnly -> s.compatibility
        is CycleState.GenerationReady -> s.compatibility
    }

    var selectedTab by remember { mutableStateOf(0) }
    val context = LocalContext.current

    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            cycle.jobTitle,
                            color = AppColors.OnBackground,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                        Text(cycle.company, color = AppColors.Subtle, fontSize = 11.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = AppColors.OnBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.Card)
            )
        },
        bottomBar = {
            Surface(color = AppColors.Card, tonalElevation = 4.dp) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (mode == AnalysisScreenMode.HistoricalReadOnly) {
                        Surface(
                            color = AppColors.Subtle.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "This analysis was saved without generating documents",
                                color = AppColors.Subtle,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    if (mode == AnalysisScreenMode.Live ||
                        mode == AnalysisScreenMode.HistoricalContinuation
                    ) {
                        val generateLabel =
                            if (mode == AnalysisScreenMode.HistoricalContinuation)
                                "Resume — Generate My CV & Cover Letter"
                            else
                                "Generate My CV & Cover Letter"

                        Button(
                            onClick = onGenerateCv,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppColors.Generation,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(generateLabel, fontWeight = FontWeight.Bold)
                        }
                    }

                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = AppColors.Destructive
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp, AppColors.Destructive
                        )
                    ) {
                        Text(
                            if (mode == AnalysisScreenMode.Live) "Dismiss" else "Back",
                            color = AppColors.Destructive
                        )
                    }
                }
            }
        }
    ) { padding ->

        if (compatibility == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No analysis data available.", color = AppColors.Subtle)
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {

            // Score circle
            item { ScoreCircle(compatibility) }

            // Role summary
            if (compatibility.roleSummary.isNotBlank()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = AppColors.Card),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "About this role",
                                color = AppColors.Analysis,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                compatibility.roleSummary,
                                color = AppColors.OnBackground,
                                fontSize = 13.sp,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }

            // How you fit
            if (compatibility.eligibilitySummary.isNotBlank()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = AppColors.Card),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "How you fit",
                                color = AppColors.Analysis,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                compatibility.eligibilitySummary,
                                color = AppColors.OnBackground,
                                fontSize = 13.sp,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }

            // Gap signal banner
            item {
                val gapMessage = when {
                    compatibility.gaps.isEmpty() && compatibility.score >= 80 ->
                        "You exceed the requirements for this role — you're overqualified."
                    compatibility.gaps.isEmpty() ->
                        "You meet all the listed requirements for this role."
                    compatibility.criticalGapCount > 0 ->
                        "You have ${compatibility.criticalGapCount} critical gap${if (compatibility.criticalGapCount > 1) "s" else ""} to address before applying."
                    else ->
                        "You have ${compatibility.gaps.size} gap${if (compatibility.gaps.size > 1) "s" else ""} worth reviewing before applying."
                }
                val gapColor = when {
                    compatibility.gaps.isEmpty() && compatibility.score >= 80 -> AppColors.Warning
                    compatibility.gaps.isEmpty() -> AppColors.Success
                    compatibility.criticalGapCount > 0 -> AppColors.Destructive
                    else -> AppColors.Warning
                }
                val icon = when {
                    compatibility.gaps.isEmpty() && compatibility.score >= 80 -> "⚡"
                    compatibility.gaps.isEmpty() -> "✓"
                    compatibility.criticalGapCount > 0 -> "⚠"
                    else -> "ℹ"
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = gapColor.copy(alpha = 0.08f)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(icon, fontSize = 18.sp)
                        Text(
                            gapMessage,
                            color = gapColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        )
                    }
                }
            }

            // Tab row
            item {
                Spacer(modifier = Modifier.height(8.dp))
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = AppColors.Card,
                    contentColor = AppColors.Analysis,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = AppColors.Analysis
                        )
                    }
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = {
                            Text(
                                "Skills Match",
                                fontSize = 13.sp,
                                color = if (selectedTab == 0) AppColors.Analysis else AppColors.Subtle
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = {
                            Text(
                                "Gap Analysis",
                                fontSize = 13.sp,
                                color = if (selectedTab == 1) AppColors.Analysis else AppColors.Subtle
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Tab content — Skills Match
            if (selectedTab == 0) {
                if (compatibility.relevancyItems.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No skills data available for this analysis.",
                                color = AppColors.Subtle,
                                fontSize = 13.sp
                            )
                        }
                    }
                } else {
                    items(compatibility.relevancyItems) { item ->
                        RelevancyCard(item)
                    }
                }
            }

            // Tab content — Gap Analysis
            if (selectedTab == 1) {
                if (compatibility.gaps.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No gaps identified. You meet all listed requirements.",
                                color = AppColors.Success,
                                fontSize = 13.sp
                            )
                        }
                    }
                } else {
                    items(compatibility.gaps) { gap ->
                        GapCard(gap, context)
                    }
                }
            }
        }
    }
}

@Composable
private fun RelevancyCard(item: RelevancyItem) {
    val groupColor = when (item.relevancyGroup) {
        "HIGH" -> AppColors.Success
        "MEDIUM" -> AppColors.Warning
        "LOW" -> AppColors.Subtle
        "PROFESSIONAL_MISMATCH" -> AppColors.Destructive
        else -> AppColors.Subtle
    }
    val groupLabel = when (item.relevancyGroup) {
        "HIGH" -> "Strong match"
        "MEDIUM" -> "Partial match"
        "LOW" -> "Weak match"
        "PROFESSIONAL_MISMATCH" -> "Not relevant"
        else -> item.relevancyGroup
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.Card),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    item.name,
                    color = AppColors.OnBackground,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "${item.matchPercent}%",
                    color = groupColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                groupLabel,
                color = groupColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            if (item.aiDescription.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    item.aiDescription,
                    color = AppColors.Subtle,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun GapCard(gap: GapItem, context: android.content.Context) {
    val importanceColor = when (gap.importance) {
        "MANDATORY" -> AppColors.Destructive
        "IMPORTANT" -> AppColors.Warning
        else -> AppColors.Subtle
    }
    val importanceLabel = when (gap.importance) {
        "MANDATORY" -> "Mandatory"
        "IMPORTANT" -> "Important"
        else -> "Nice to have"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = importanceColor.copy(alpha = 0.06f)
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    gap.skill,
                    color = AppColors.OnBackground,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    color = importanceColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        importanceLabel,
                        color = importanceColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                gap.description,
                color = AppColors.Subtle,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
            if (gap.courses.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "Recommended courses",
                    color = AppColors.OnBackground,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                gap.courses.forEach { course ->
                    val courseColor = when (course.category) {
                        "free" -> AppColors.Success
                        "discounted" -> AppColors.Warning
                        else -> AppColors.Generation
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                context.startActivity(
                                    Intent(Intent.ACTION_VIEW, Uri.parse(course.url))
                                )
                            }
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text("•", color = courseColor, fontSize = 12.sp)
                        Column {
                            Text(
                                course.title,
                                color = courseColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                buildString {
                                    append(course.provider)
                                    append(" · ")
                                    append(course.category.replaceFirstChar { it.uppercase() })
                                    if (course.hasCertificate) append(" · Certificate")
                                    append(" · ${course.estimatedDuration}")
                                },
                                color = AppColors.Subtle,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ScoreCircle(compatibility: CompatibilityRecord) {
    val scoreColor = when {
        compatibility.score >= 75 -> AppColors.Success
        compatibility.score >= 50 -> AppColors.Warning
        else -> AppColors.Destructive
    }
    val scoreLabel = when {
        compatibility.score >= 80 -> "Strong match"
        compatibility.score >= 60 -> "Decent match"
        compatibility.score >= 40 -> "Partial match"
        else -> "Weak match"
    }
    val sweep by animateFloatAsState(
        targetValue = (compatibility.score / 100f) * 270f,
        animationSpec = tween(1000),
        label = "score"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(130.dp), contentAlignment = Alignment.Center) {
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "${compatibility.score}%",
                    color = scoreColor,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("Match", color = AppColors.Subtle, fontSize = 11.sp)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            scoreLabel,
            color = scoreColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}