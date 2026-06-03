package com.vantedge.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vantedge.app.data.model.AcquisitionMode
import com.vantedge.app.util.LogDumper
import kotlinx.coroutines.delay

@Composable
fun ChoosePathScreen(
    onSelectMode: (AcquisitionMode) -> Unit
) {
    var showContent by remember { mutableStateOf(false) }
    var showCards by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(200)
        showContent = true
        delay(400)
        showCards = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
        ) {

            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(tween(600)) +
                        slideInVertically(
                            tween(600, easing = FastOutSlowInEasing),
                            initialOffsetY = { it / 3 }
                        )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "How would you like to begin?",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "We'll build your career profile either way.",
                        fontSize = 14.sp,
                        color = Color(0xFF9E9E9E),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                visible = showCards,
                enter = fadeIn(tween(500)) +
                        slideInVertically(
                            tween(500, easing = FastOutSlowInEasing),
                            initialOffsetY = { it / 2 }
                        )
            ) {
                PathCard(
                    icon = "📄",
                    title = "Upload your CV",
                    subtitle = "We'll read it and build your profile automatically.",
                    isPrimary = true,
                    isDisabled = false,
                    onClick = { onSelectMode(AcquisitionMode.CV_UPLOAD) }
                )
            }

            AnimatedVisibility(
                visible = showCards,
                enter = fadeIn(tween(600)) +
                        slideInVertically(
                            tween(600, easing = FastOutSlowInEasing),
                            initialOffsetY = { it / 2 }
                        )
            ) {
                PathCard(
                    icon = "✏️",
                    title = "Build manually",
                    subtitle = "Tell us about yourself step by step.",
                    isPrimary = false,
                    isDisabled = false,
                    onClick = { onSelectMode(AcquisitionMode.MANUAL) }
                )
            }

            AnimatedVisibility(
                visible = showCards,
                enter = fadeIn(tween(700)) +
                        slideInVertically(
                            tween(700, easing = FastOutSlowInEasing),
                            initialOffsetY = { it / 2 }
                        )
            ) {
                PathCard(
                    icon = "🔗",
                    title = "Import from LinkedIn",
                    subtitle = "Coming soon.",
                    isPrimary = false,
                    isDisabled = true,
                    onClick = {}
                )
            }

            AnimatedVisibility(visible = showCards) {
                TextButton(
                    onClick = { LogDumper.dumpAndShare(context) }
                ) {
                    Text(
                        text = "Export debug logs",
                        color = Color(0xFF444444),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun PathCard(
    icon: String,
    title: String,
    subtitle: String,
    isPrimary: Boolean,
    isDisabled: Boolean,
    onClick: () -> Unit
) {
    val bg = if (isPrimary) Color(0xFF1F1F1F) else Color(0xFF141414)
    val border = if (isPrimary) Color(0xFF00BFA5) else Color(0xFF2A2A2A)
    val textColor = if (isDisabled) Color(0xFF666666) else Color.White

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(bg)
            .then(
                if (!isDisabled) Modifier.clickable { onClick() }
                else Modifier
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text = icon, fontSize = 20.sp)
        Text(text = title, color = textColor, fontWeight = FontWeight.Bold)
        Text(text = subtitle, color = Color(0xFF9E9E9E), fontSize = 13.sp)
    }
}