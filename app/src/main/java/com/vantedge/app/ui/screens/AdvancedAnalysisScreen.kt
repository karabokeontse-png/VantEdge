package com.vantedge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.CourseRecommendation
import com.vantedge.app.data.model.CycleState
import com.vantedge.app.data.model.GapItem
import com.vantedge.app.data.model.GenerationCycle
import com.vantedge.app.data.model.RelevancyItem
import com.vantedge.app.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedAnalysisScreen(
    cycle: GenerationCycle,
    onGenerateCv: () -> Unit,
    onBack: () -> Unit
) {
    val compatibility: CompatibilityRecord? = when (val s = cycle.state) {
        is CycleState.FullCycle -> s.compatibility
        is CycleState.AnalysisOnly -> s.compatibility
        is CycleState.GenerationReady -> s.compatibility
    }

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Skills Match", "Gap Analysis")

    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Detailed Analysis",
                            color = AppColors.OnBackground,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                        Text(
                            "${cycle.jobTitle} · ${cycle.company}",
                            color = AppColors.Subtle,
                            fontSize = 11.sp
                        )
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
                Button(
                    onClick = onGenerateCv,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Generation,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        "Generate My CV & Cover Letter",
                        fontWeight = FontWeight.Bold
                    )
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = selectedTab == index
                    Button(
                        onClick = { selectedTab = index },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) AppColors.Analysis else AppColors.Card,
                            contentColor = if (isSelected) Color.White else AppColors.Subtle
                        )
                    ) {
                        Text(
                            title,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Box(modifier = Modifier.weight(1f)) {
                when (selectedTab) {
                    0 -> SkillsMatchContent(compatibility)
                    1 -> GapAnalysisContent(compatibility)
                }
            }
        }
    }
}

@Composable
private fun SkillsMatchContent(compatibility: CompatibilityRecord) {
    val highItems = compatibility.relevancyItems.filter { it.relevancyGroup == "HIGH" }
    val mediumItems = compatibility.relevancyItems.filter { it.relevancyGroup == "MEDIUM" }
    val lowItems = compatibility.relevancyItems.filter { it.relevancyGroup == "LOW" }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        if (compatibility.relevancyItems.isEmpty()) {
            item {
                val (icon, message) = when {
                    compatibility.score >= 80 ->
                        "⚡" to "You exceed what this role requires — every skill you have is relevant."
                    else ->
                        "✓" to "You meet the requirements for this role."
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.Analysis.copy(alpha = 0.08f)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(icon, fontSize = 20.sp)
                        Text(
                            message,
                            color = AppColors.OnBackground,
                            fontSize = 14.sp,
                            lineHeight = 21.sp
                        )
                    }
                }
            }
            return@LazyColumn
        }

        if (highItems.isNotEmpty()) {
            item {
                Text(
                    "STRONG MATCH — direct requirements",
                    color = AppColors.Success,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
            items(highItems) { SkillMatchCard(it, AppColors.Success) }
        }

        if (mediumItems.isNotEmpty()) {
            item {
                Text(
                    "PARTIAL MATCH — transferable skills",
                    color = AppColors.Warning,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
            items(mediumItems) { SkillMatchCard(it, AppColors.Warning) }
        }

        if (lowItems.isNotEmpty()) {
            item {
                Text(
                    "LOW MATCH — peripheral to this role",
                    color = AppColors.Subtle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
            items(lowItems) { SkillMatchCard(it, AppColors.Subtle) }
        }
    }
}

@Composable
private fun SkillMatchCard(item: RelevancyItem, accentColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 3.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.Card),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                color = accentColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.width(48.dp)
            ) {
                Text(
                    "${item.matchPercent}%",
                    color = accentColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        item.name,
                        color = AppColors.OnBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Surface(
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            item.type.uppercase(),
                            color = AppColors.Subtle,
                            fontSize = 9.sp,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    item.aiDescription,
                    color = AppColors.Subtle,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
private fun GapAnalysisContent(compatibility: CompatibilityRecord) {
    val criticalGaps = compatibility.gaps.filter { it.importance == "CRITICAL" }
    val importantGaps = compatibility.gaps.filter { it.importance == "IMPORTANT" }
    val niceToHaveGaps = compatibility.gaps.filter { it.importance == "NICE_TO_HAVE" }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        if (compatibility.gaps.isEmpty()) {
            item {
                val (icon, title, message) = when {
                    compatibility.score >= 80 -> Triple(
                        "⚡",
                        "You're overqualified for this role",
                        "This role doesn't require anything you don't already have. Your skills exceed what's listed in the job description."
                    )
                    else -> Triple(
                        "✓",
                        "No gaps to address",
                        "You meet all the listed requirements for this role. Nothing critical is missing from your profile."
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.Success.copy(alpha = 0.08f)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(icon, fontSize = 20.sp)
                            Text(
                                title,
                                color = AppColors.Success,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            message,
                            color = AppColors.OnBackground,
                            fontSize = 13.sp,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
            return@LazyColumn
        }

        if (compatibility.criticalGapCount > 0) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.Destructive.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "You have ${compatibility.criticalGapCount} critical gap${if (compatibility.criticalGapCount > 1) "s" else ""} to address before applying",
                        color = AppColors.Destructive,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        if (criticalGaps.isNotEmpty()) {
            item {
                Text(
                    "CRITICAL — address before applying",
                    color = AppColors.Destructive,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
            items(criticalGaps) { GapCard(gap = it, priorityColor = AppColors.Destructive) }
        }

        if (importantGaps.isNotEmpty()) {
            item {
                Text(
                    "IMPORTANT — worth closing soon",
                    color = AppColors.Warning,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
            items(importantGaps) { GapCard(gap = it, priorityColor = AppColors.Warning) }
        }

        if (niceToHaveGaps.isNotEmpty()) {
            item {
                Text(
                    "NICE TO HAVE — not blocking",
                    color = AppColors.Subtle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
            items(niceToHaveGaps) { GapCard(gap = it, priorityColor = AppColors.Subtle) }
        }
    }
}

@Composable
private fun GapCard(gap: GapItem, priorityColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
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
                    gap.skill,
                    color = AppColors.OnBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    color = priorityColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        gap.importance.replace("_", " "),
                        color = priorityColor,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(Modifier.height(6.dp))
            Text(
                gap.description,
                color = AppColors.Subtle,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )

            if (gap.courses.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                Text(
                    "How to close this gap",
                    color = AppColors.Generation,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Spacer(Modifier.height(6.dp))
                gap.courses.forEachIndexed { index, course ->
                    CourseRow(course = course, index = index + 1)
                    if (index < gap.courses.size - 1) Spacer(Modifier.height(6.dp))
                }
            }
        }
    }
}

@Composable
private fun CourseRow(course: CourseRecommendation, index: Int) {
    val categoryColor = when (course.category) {
        "free" -> AppColors.Success
        "discounted" -> AppColors.Warning
        else -> AppColors.Generation
    }
    Surface(
        color = Color(0xFF0D1B2A),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                color = AppColors.Generation.copy(alpha = 0.15f),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.size(24.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        "$index",
                        color = AppColors.Generation,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    course.title,
                    color = AppColors.OnBackground,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
                Spacer(Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(course.provider, color = AppColors.Subtle, fontSize = 10.sp)
                    Text("·", color = AppColors.Subtle, fontSize = 10.sp)
                    Text(course.estimatedDuration, color = AppColors.Subtle, fontSize = 10.sp)
                }
                Spacer(Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(
                        color = categoryColor.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            course.category.uppercase(),
                            color = categoryColor,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                        )
                    }
                    if (course.hasCertificate) {
                        Surface(
                            color = AppColors.Analysis.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                "CERTIFICATE",
                                color = AppColors.Analysis,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}