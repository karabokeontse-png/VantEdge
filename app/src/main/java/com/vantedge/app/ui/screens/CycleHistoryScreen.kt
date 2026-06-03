package com.vantedge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.CycleState
import com.vantedge.app.data.model.GenerationCycle
import com.vantedge.app.data.viewmodel.CycleViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val CHBg = Color(0xFF0D0D0D)
private val CHCard = Color(0xFF1A1A2E)
private val CHTeal = Color(0xFF00BFA5)
private val CHAmber = Color(0xFFFFB830)
private val CHOnBg = Color(0xFFE0E0E0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CycleHistoryScreen(
    cycles: List<GenerationCycle>,
    viewModel: CycleViewModel,
    onOpen: (GenerationCycle) -> Unit,
    onDelete: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        containerColor = CHBg,
        topBar = {
            TopAppBar(
                title = { Text("Application History", color = CHOnBg) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = CHOnBg)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CHCard)
            )
        }
    ) { padding ->
        if (cycles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No applications yet.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cycles, key = { it.id }) { cycle ->

                    val compatibility: CompatibilityRecord? = when (val s = cycle.state) {
    is CycleState.FullCycle -> s.compatibility
    is CycleState.AnalysisOnly -> s.compatibility
    is CycleState.GenerationReady -> s.compatibility
}

                    val score = compatibility?.score ?: 0
                    val scoreColor = when {
                        score >= 75 -> Color(0xFF4CAF50)
                        score >= 50 -> CHAmber
                        else -> Color(0xFFE53935)
                    }

                    val previousScore = viewModel.getPreviousScore(cycle)
                    val delta = if (previousScore != null) score - previousScore else null

                    val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                        .format(Date(cycle.createdAt))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = CHCard),
                        shape = RoundedCornerShape(10.dp),
                        onClick = { onOpen(cycle) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    cycle.jobTitle,
                                    color = CHOnBg,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    cycle.company,
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "Score: $score%",
                                        color = scoreColor,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    // Score delta badge
                                    if (delta != null) {
                                        val deltaColor = when {
                                            delta > 0 -> Color(0xFF4CAF50)
                                            delta < 0 -> Color(0xFFE53935)
                                            else -> Color.Gray
                                        }
                                        val deltaText = when {
                                            delta > 0 -> "↑$delta%"
                                            delta < 0 -> "↓${kotlin.math.abs(delta)}%"
                                            else -> "→0%"
                                        }
                                        Surface(
                                            color = deltaColor.copy(alpha = 0.15f),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                deltaText,
                                                color = deltaColor,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(
                                                    horizontal = 6.dp,
                                                    vertical = 2.dp
                                                )
                                            )
                                        }
                                    }

                                    if (cycle.version != null) {
                                        Text(
                                            "v${cycle.version}",
                                            color = CHTeal,
                                            fontSize = 12.sp
                                        )
                                    } else {
                                        Text(
                                            "Analysis",
                                            color = Color.Gray,
                                            fontSize = 12.sp
                                        )
                                    }
                                    Text(
                                        date,
                                        color = Color.Gray,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                            IconButton(onClick = { onDelete(cycle.id) }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}