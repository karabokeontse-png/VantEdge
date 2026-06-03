package com.vantedge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PostDownloadScreen(
    onGoToDashboard: () -> Unit,
    onBackToGenerator: () -> Unit,
    onViewHistory: () -> Unit
) {
    val teal = Color(0xFF00BFA5)
    val amber = Color(0xFFFFB830)
    val bg = Color(0xFF0D0D0D)
    val card = Color(0xFF1A1A2E)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = card)
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "✅ CV Saved!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = teal
                )
                Text(
                    "Your editable Word document has been saved to your Downloads folder. Open it in Microsoft Word or Google Docs.",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Divider(color = Color.DarkGray, thickness = 1.dp)

                Button(
                    onClick = onGoToDashboard,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = teal,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Go to Dashboard", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onBackToGenerator,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = amber,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Back to CV Generator", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onViewHistory,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = teal)
                ) {
                    Text("View History", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}