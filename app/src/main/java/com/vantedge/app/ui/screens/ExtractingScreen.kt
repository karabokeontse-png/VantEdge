package com.vantedge.app.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vantedge.app.data.viewmodel.OnboardingExtractionState

@Composable
fun ExtractingScreen(
    extractionState: OnboardingExtractionState,
    onRetry: () -> Unit
) {
    val teal = Color(0xFF00BFA5)
    val bg = Color(0xFF0D0D0D)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg),
        contentAlignment = Alignment.Center
    ) {
        when (val state = extractionState) {

            is OnboardingExtractionState.Failure -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 32.dp)
                ) {
                    Text(
                        "Something went wrong",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        state.message,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = onRetry,
                        colors = ButtonDefaults.buttonColors(containerColor = teal)
                    ) {
                        Text("Try again", color = Color.Black)
                    }
                }
            }

            else -> {
                val statusText = when (state) {
                    is OnboardingExtractionState.Extracting -> state.statusText
                    is OnboardingExtractionState.Retrying -> state.statusText
                    else -> "Analyzing your document..."
                }

                val infinite = rememberInfiniteTransition(label = "pulse")

                val scale by infinite.animateFloat(
                    initialValue = 0.85f,
                    targetValue = 1.1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(900),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "scale"
                )

                val alpha by infinite.animateFloat(
                    initialValue = 0.4f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(900),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "alpha"
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 32.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .scale(scale)
                            .alpha(alpha)
                            .background(teal.copy(alpha = 0.2f))
                    )

                    Spacer(Modifier.height(24.dp))

                    Text(
                        "Building your profile",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(Modifier.height(8.dp))

                    AnimatedContent(
                        targetState = statusText,
                        transitionSpec = {
                            fadeIn(tween(400)) togetherWith fadeOut(tween(400))
                        },
                        label = "status"
                    ) { text ->
                        Text(
                            text = text,
                            color = Color.Gray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    LinearProgressIndicator(
                        color = teal,
                        trackColor = teal.copy(alpha = 0.15f),
                        modifier = Modifier.fillMaxWidth(0.6f)
                    )
                }
            }
        }
    }
}