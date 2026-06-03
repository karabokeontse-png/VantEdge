package com.vantedge.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vantedge.app.domain.PipelineStep
import com.vantedge.app.ui.theme.AppColors
import kotlinx.coroutines.delay

enum class LoadingMode {
    FULL_PIPELINE,
    IMPROVE_ONLY
}

@Composable
fun PipelineLoadingScreen(
    step: PipelineStep,
    loadingMode: LoadingMode = LoadingMode.FULL_PIPELINE
) {
    when (loadingMode) {
        LoadingMode.IMPROVE_ONLY -> ImproveLoadingScreen()
        LoadingMode.FULL_PIPELINE -> FullPipelineLoadingScreen(step)
    }
}

@Composable
private fun ImproveLoadingScreen() {
    val pulse = rememberInfiniteTransition(label = "pulse")
    val alpha by pulse.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(800), RepeatMode.Reverse),
        label = "alpha"
    )

    val improveMessages = listOf(
        "Reviewing your previous version...",
        "Targeting your skill gaps...",
        "Strengthening weak sections...",
        "Refining your language...",
        "Finalising your improved documents..."
    )
    var messageIndex by remember { mutableStateOf(0) }
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(2200)
            visible = false
            delay(300)
            messageIndex = (messageIndex + 1) % improveMessages.size
            visible = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            CircularProgressIndicator(
                color = AppColors.Generation,
                strokeWidth = 3.dp,
                modifier = Modifier.size(52.dp)
            )

            Text(
                "Improving your application",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(300)),
                exit = fadeOut(tween(300))
            ) {
                Text(
                    improveMessages[messageIndex],
                    color = AppColors.Generation,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun FullPipelineLoadingScreen(step: PipelineStep) {
    val pulse = rememberInfiniteTransition(label = "pulse")
    val alpha by pulse.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(700), RepeatMode.Reverse),
        label = "alpha"
    )

    var visualStep by remember { mutableStateOf(0) }

    LaunchedEffect(step) {
        visualStep = when (step) {
            PipelineStep.ANALYSING -> 0
            PipelineStep.GENERATING_CV -> 1
            PipelineStep.GENERATING_COVER_LETTER -> 2
            PipelineStep.APPLYING_DESIGN -> 3
        }
    }

    // Animated stage messages for the active step
    val stageMessages = mapOf(
        0 to listOf(
            "Reading your profile...",
            "Matching your skills to the role...",
            "Calculating your fit score...",
            "Identifying your gaps..."
        ),
        1 to listOf(
            "Structuring your CV...",
            "Highlighting your strongest skills...",
            "Tailoring your experience section...",
            "Optimising for ATS..."
        ),
        2 to listOf(
            "Drafting your opening...",
            "Framing your value proposition...",
            "Closing strong..."
        ),
        3 to listOf(
            "Applying your chosen design...",
            "Formatting for download..."
        )
    )

    var subMessageIndex by remember { mutableStateOf(0) }
    var subVisible by remember { mutableStateOf(true) }

    LaunchedEffect(visualStep) {
        subMessageIndex = 0
        val messages = stageMessages[visualStep] ?: return@LaunchedEffect
        while (true) {
            delay(2000)
            subVisible = false
            delay(250)
            subMessageIndex = (subMessageIndex + 1) % messages.size
            subVisible = true
        }
    }

    val steps = listOf(
        PipelineStep.ANALYSING to "Analysing your profile",
        PipelineStep.GENERATING_CV to "Tailoring your CV",
        PipelineStep.GENERATING_COVER_LETTER to "Writing your cover letter",
        PipelineStep.APPLYING_DESIGN to "Applying design"
    )

    val stepColors = listOf(
        AppColors.Analysis,
        AppColors.Generation,
        AppColors.Generation,
        AppColors.Generation
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Working on it",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Animated sub-message
            AnimatedVisibility(
                visible = subVisible,
                enter = fadeIn(tween(250)),
                exit = fadeOut(tween(250))
            ) {
                val messages = stageMessages[visualStep] ?: emptyList()
                if (messages.isNotEmpty()) {
                    Text(
                        text = messages[subMessageIndex],
                        color = stepColors.getOrElse(visualStep) { AppColors.Generation },
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            steps.forEachIndexed { index, (_, label) ->
                val isDone = index < visualStep
                val isActive = index == visualStep
                val color = when {
                    isDone -> AppColors.Generation
                    isActive -> stepColors.getOrElse(index) { AppColors.Generation }
                    else -> AppColors.Subtle
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    // Step indicator
                    Box(
                        modifier = Modifier.size(28.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isActive) {
                            CircularProgressIndicator(
                                color = color,
                                strokeWidth = 2.dp,
                                modifier = Modifier
                                    .size(28.dp)
                                    .alpha(alpha)
                            )
                        } else {
                            Text(
                                text = if (isDone) "✓" else "○",
                                color = color,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = label,
                        color = color,
                        fontSize = 14.sp,
                        fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
                        modifier = if (isActive) Modifier.alpha(alpha) else Modifier
                    )
                }

                if (index < steps.size - 1) {
                    Box(
                        modifier = Modifier
                            .padding(start = 14.dp)
                            .width(2.dp)
                            .height(8.dp)
                            .background(
                                color = if (isDone) AppColors.Generation.copy(alpha = 0.4f)
                                else AppColors.Subtle.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(1.dp)
                            )
                    )
                }
            }
        }
    }
}