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
import androidx.navigation.NavController
import com.vantedge.app.data.model.*
import com.vantedge.app.data.viewmodel.CompatibilityViewModel

private val CBg = Color(0xFF0D0D0D)
private val CCard = Color(0xFF1A1A2E)
private val CTeal = Color(0xFF00BFA5)
private val CAmber = Color(0xFFFFB830)
private val COnBg = Color(0xFFE0E0E0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompatibilityResultScreen(
    navController: NavController,
    viewModel: CompatibilityViewModel,
    profile: UserProfile,
    onCreateCvForThisJob: () -> Unit
) {
    val record = viewModel.lastRecord ?: return
    val context = LocalContext.current

    val addedSkills by viewModel.addedSkills.collectAsState()
    val addedCerts by viewModel.addedCerts.collectAsState()

    var selectedTab by remember { mutableStateOf(1) }

    val scoreColor = when {
        record.score >= 75 -> Color(0xFF4CAF50)
        record.score >= 50 -> CAmber
        else -> Color(0xFFE53935)
    }

    Scaffold(
        containerColor = CBg,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(record.jobTitle, color = COnBg, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text(record.company, color = Color.Gray, fontSize = 12.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.resetState()
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = COnBg)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CCard)
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {

            item {
                ScoreHeader(record, scoreColor)
            }

            items(record.gaps) { gap ->

                val gapName = gap.skill

                val hasSkill = profile.skills.any {
                    it.trim().lowercase() == gapName.trim().lowercase()
                }

                val hasCert = profile.certifications.any {
                    it.name.trim().lowercase() == gapName.trim().lowercase()
                }

                val skillAdded = hasSkill || addedSkills.contains(gapName)
                val certAdded = hasCert || addedCerts.contains(gapName)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CCard),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = gapName,
                            color = COnBg,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = gap.description,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                            Button(
                                onClick = {
                                    viewModel.addSkillToProfile(gapName, profile)
                                },
                                enabled = !skillAdded,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (skillAdded) Color.Gray else CTeal
                                )
                            ) {
                                Text(
                                    if (skillAdded) "✓ Skill Added" else "+ Add Skill",
                                    fontSize = 12.sp
                                )
                            }

                            Button(
                                onClick = {
                                    viewModel.addCertToProfile(gapName, profile)
                                },
                                enabled = !certAdded,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (certAdded) Color.Gray else CAmber
                                )
                            ) {
                                Text(
                                    if (certAdded) "✓ Cert Added" else "+ Add Cert",
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        gap.courses.forEach { course ->
                            Text(
                                text = "• ${course.title} (${course.provider})",
                                color = CTeal,
                                fontSize = 12.sp,
                                modifier = Modifier.clickable {
                                    context.startActivity(
                                        Intent(Intent.ACTION_VIEW, Uri.parse(course.url))
                                    )
                                }
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onCreateCvForThisJob,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CTeal)
                ) {
                    Text("Create CV for This Job", color = Color.Black)
                }
            }
        }
    }
}

@Composable
private fun ScoreHeader(record: CompatibilityRecord, color: Color) {

    val sweep by animateFloatAsState(
        targetValue = (record.score / 100f) * 270f,
        animationSpec = tween(1000),
        label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = CCard)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.size(120.dp),
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
                        color = color,
                        startAngle = 135f,
                        sweepAngle = sweep,
                        useCenter = false,
                        topLeft = Offset(pad, pad),
                        size = Size(size.width - stroke, size.height - stroke),
                        style = Stroke(stroke, cap = StrokeCap.Round)
                    )
                }

                Text(
                    text = "${record.score}%",
                    color = color,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = record.eligibilitySummary,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}