package com.vantedge.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    onContinue: () -> Unit
) {
    // Staggered visibility states
    var showTagline by remember { mutableStateOf(false) }
    var showSubtitle by remember { mutableStateOf(false) }
    var showCta by remember { mutableStateOf(false) }

    // Ambient gradient animation
    val infiniteTransition = rememberInfiniteTransition(label = "ambient")
    val gradientShift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gradientShift"
    )

    // Staggered reveal
    LaunchedEffect(Unit) {
        delay(300)
        showTagline = true
        delay(700)
        showSubtitle = true
        delay(600)
        showCta = true
    }

    val backgroundBrush = Brush.radialGradient(
        colors = listOf(
            Color(0xFF1A1A2E).copy(alpha = 0.9f + gradientShift * 0.1f),
            Color(0xFF0D0D0D)
        ),
        radius = 1200f + gradientShift * 300f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onContinue() },
        contentAlignment = Alignment.Center
    ) {
        // Ambient background layer
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {

            // Brand mark
            AnimatedVisibility(
                visible = showTagline,
                enter = fadeIn(tween(800)) + slideInVertically(
                    tween(800, easing = EaseOutCubic),
                    initialOffsetY = { it / 3 }
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "VANTEDGE",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 6.sp,
                        color = Color(0xFF00BFA5).copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Your career shouldn't restart every time you apply.",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 34.sp
                    )
                }
            }

            // Subtitle
            AnimatedVisibility(
                visible = showSubtitle,
                enter = fadeIn(tween(700)) + slideInVertically(
                    tween(700, easing = EaseOutCubic),
                    initialOffsetY = { it / 3 }
                )
            ) {
                Text(
                    text = "VantEdge builds a living career profile\nthat improves with every application.",
                    fontSize = 15.sp,
                    color = Color(0xFF9E9E9E),
                    textAlign = TextAlign.Center,
                    lineHeight = 23.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // CTA
            AnimatedVisibility(
                visible = showCta,
                enter = fadeIn(tween(600)) + slideInVertically(
                    tween(600, easing = EaseOutCubic),
                    initialOffsetY = { it / 2 }
                )
            ) {
                BeginButton(onClick = onContinue)
            }
        }

        // Tap anywhere hint
        AnimatedVisibility(
            visible = showCta,
            enter = fadeIn(tween(1000)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        ) {
            Text(
                text = "tap anywhere to continue",
                fontSize = 12.sp,
                color = Color(0xFF9E9E9E).copy(alpha = 0.5f),
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
private fun BeginButton(onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "buttonScale"
    )

    Box(
        modifier = Modifier
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF00BFA5), Color(0xFF00897B))
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(50)
            )
            .padding(horizontal = 48.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Begin",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF0D0D0D)
        )
    }
}