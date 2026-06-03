package com.vantedge.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ColorScheme(
    val id: String,
    val name: String,
    val vibe: String,
    val primary: Color,
    val secondary: Color,
    val accent: Color
)

val colorSchemes = listOf(
    ColorScheme(
        id = "navy",
        name = "Navy & Slate",
        vibe = "Trustworthy, authoritative, stable. Best for finance, law, executive roles.",
        primary = Color(0xFF1A237E),
        secondary = Color(0xFF607D8B),
        accent = Color(0xFFECEFF1)
    ),
    ColorScheme(
        id = "emerald",
        name = "Emerald & Charcoal",
        vibe = "Fresh, growth-oriented, tech-forward. Great for tech and modern roles.",
        primary = Color(0xFF263238),
        secondary = Color(0xFF2E7D32),
        accent = Color(0xFFEEEEEE)
    ),
    ColorScheme(
        id = "monochrome",
        name = "Minimalist Monochrome",
        vibe = "Clean, sophisticated, timeless. 100% readability guaranteed.",
        primary = Color(0xFF000000),
        secondary = Color(0xFF616161),
        accent = Color(0xFFFAFAFA)
    ),
    ColorScheme(
        id = "burgundy",
        name = "Burgundy & Sand",
        vibe = "Elegant, unique, high-end. For design, architecture, luxury marketing.",
        primary = Color(0xFF4A0E2B),
        secondary = Color(0xFFC4A882),
        accent = Color(0xFF2C1810)
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorSchemeScreen(
    onSchemeSelected: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        containerColor = DarkBg,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Choose Color Scheme",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Pick a color palette that reflects your professional identity.",
                color = Color.Gray,
                fontSize = 14.sp
            )

            colorSchemes.forEach { scheme ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSchemeSelected(scheme.id) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF111111)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                scheme.name,
                                color = Teal,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(scheme.primary)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(scheme.secondary)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(scheme.accent)
                                        .border(1.dp, Color.Gray, CircleShape)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(scheme.vibe, color = Color.Gray, fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                )
            ) {
                Text("Back")
            }
        }
    }
}