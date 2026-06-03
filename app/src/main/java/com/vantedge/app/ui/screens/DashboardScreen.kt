package com.vantedge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vantedge.app.data.viewmodel.CycleViewModel
import com.vantedge.app.util.LogDumper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    cycleViewModel: CycleViewModel,
    onNavigateToCompatibility: () -> Unit,
    onNavigateToCvGenerator: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToEditProfile: () -> Unit,
    onNavigateToNewApplication: () -> Unit,
    onNavigateToQuickAnalysis: () -> Unit,
    onNavigateToQuickGenerate: () -> Unit
) {

    val context = LocalContext.current

    val teal = Color(0xFF00BFA5)
    val amber = Color(0xFFFFB830)
    val purple = Color(0xFF7C4DFF)
    val green = Color(0xFF00E676)
    val blue = Color(0xFF2979FF)
    val cardDark = Color(0xFF1A1A2E)
    val bg = Color(0xFF0D0D0D)

    val totalSaved = cycleViewModel.totalSavedApplications()
    val bestRole = cycleViewModel.bestScoringRole()
    val avgScore = cycleViewModel.averageScore()
    val improved = cycleViewModel.improvedApplicationsCount()

    Scaffold(
        containerColor = bg,
        topBar = {
            TopAppBar(title = { Text("VantEdge", color = Color.White) })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Your Career Engine",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = teal
            )

            Text(
                text = "Choose how you want to work today",
                fontSize = 14.sp,
                color = Color.Gray
            )

            if (totalSaved > 0) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardDark),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            "📊 Your Progress",
                            color = teal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            InsightStat("Applications", "$totalSaved", teal)

                            if (avgScore != null) {
                                InsightStat(
                                    "Avg Score",
                                    "$avgScore%",
                                    when {
                                        avgScore >= 75 -> Color(0xFF4CAF50)
                                        avgScore >= 50 -> amber
                                        else -> Color(0xFFE53935)
                                    }
                                )
                            }

                            if (improved > 0) {
                                InsightStat("Improved", "$improved", Color(0xFF4CAF50))
                            }
                        }

                        if (bestRole != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("🏆 Best role: $bestRole", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardDark),
                onClick = onNavigateToNewApplication
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("⚡ New Application", color = green, fontWeight = FontWeight.Bold)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardDark),
                onClick = onNavigateToQuickAnalysis
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("🔍 Quick Analysis", color = purple, fontWeight = FontWeight.Bold)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardDark),
                onClick = onNavigateToQuickGenerate
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("⚙ Quick Generate", color = blue, fontWeight = FontWeight.Bold)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardDark),
                onClick = onNavigateToHistory
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("📁 Application History", color = amber, fontWeight = FontWeight.Bold)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardDark),
                onClick = onNavigateToEditProfile
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("👤 Edit Profile", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // DEBUG SECTION
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF222222))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        "🛠 Developer Tools",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            LogDumper.dumpAndShare(context)
                        }
                    ) {
                        Text("Export Logs")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun InsightStat(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = color, fontWeight = FontWeight.Bold, fontSize = 22.sp)
        Text(label, color = Color.Gray, fontSize = 11.sp)
    }
}