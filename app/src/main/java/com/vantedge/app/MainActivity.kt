package com.vantedge.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.vantedge.app.data.storage.UserPreferences
import com.vantedge.app.navigation.AppNavigation
import com.vantedge.app.ui.theme.VantEdgeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Required: initializes PDFBox internal resources before any PDF operation
        PDFBoxResourceLoader.init(applicationContext)

        val userPreferences = UserPreferences(this)

        setContent {
            VantEdgeTheme {
                AppNavigation(
                    userPreferences = userPreferences
                )
            }
        }
    }
}