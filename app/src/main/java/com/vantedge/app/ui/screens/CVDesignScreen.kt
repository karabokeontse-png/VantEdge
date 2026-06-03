package com.vantedge.app.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CVDesign(
    val id: String,
    val name: String,
    val description: String,
    val bestFor: String,
    val atsFriendliness: String
)

val cvDesigns = listOf(
    CVDesign(
        id = "modern",
        name = "The Modern Professional",
        description = "Sans-serif fonts, subtle color accents, and a two-column layout to save space.",
        bestFor = "IT professionals, project managers, mid-to-senior corporate roles.",
        atsFriendliness = "High"
    ),
    CVDesign(
        id = "minimalist",
        name = "The Minimalist",
        description = "Large headings, generous margins, single-column layout. Timeless typography.",
        bestFor = "Law, finance, academia, high-end sophistication.",
        atsFriendliness = "Excellent"
    ),
    CVDesign(
        id = "creative",
        name = "The Creative / Portfolio",
        description = "Bold color palettes, unique grid layouts, designed to show personality.",
        bestFor = "Graphic designers, UX/UI designers, photographers, marketers.",
        atsFriendliness = "Low"
    ),
    CVDesign(
        id = "executive",
        name = "The Executive / Traditional",
        description = "Serif fonts, centered headers, horizontal dividers. High-density information.",
        bestFor = "C-suite executives, government, traditional banking.",
        atsFriendliness = "Very High"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CVDesignScreen(
    onDesignSelected: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        containerColor = DarkBg,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Choose Your CV Style",
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
                "Select the style that best matches your industry and role.",
                color = Color.Gray,
                fontSize = 14.sp
            )

            cvDesigns.forEach { design ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDesignSelected(design.id) },
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
                                design.name,
                                color = Teal,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                "ATS: ${design.atsFriendliness}",
                                color = when (design.atsFriendliness) {
                                    "Excellent", "Very High" -> Color(0xFF4CAF50)
                                    "High" -> Teal
                                    else -> Color(0xFFFFB830)
                                },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(design.description, color = Color.Gray, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Best for: ${design.bestFor}",
                            color = Color(0xFF888888),
                            fontSize = 12.sp
                        )
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