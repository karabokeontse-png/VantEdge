package com.vantedge.app.ui.theme

import androidx.compose.ui.graphics.Color

object AppColors {
    // Pipeline stage colors — use these everywhere, no inline hex
    val Analysis = Color(0xFF7C4DFF)      // Purple — analysis, scoring
    val Generation = Color(0xFF00BFA5)    // Teal — CV, cover letter, generation
    val Destructive = Color(0xFFE53935)   // Red — dismiss, delete, warnings
    val Warning = Color(0xFFFFB830)       // Amber — partial match, caution
    val Success = Color(0xFF4CAF50)       // Green — strong match, complete

    // Surface colors
    val Background = Color(0xFF0D0D0D)
    val Card = Color(0xFF1A1A2E)
    val OnBackground = Color(0xFFE0E0E0)
    val Subtle = Color(0xFF9E9E9E)
}