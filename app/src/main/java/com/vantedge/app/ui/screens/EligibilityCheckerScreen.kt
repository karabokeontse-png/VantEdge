package com.vantedge.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.vantedge.app.data.storage.UserPreferences
import com.vantedge.app.data.model.UserProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EligibilityCheckerScreen(
    userPreferences: UserPreferences,
    onBack: () -> Unit
) {
    val profile by userPreferences.userProfileFlow.collectAsState(initial = UserProfile())
    var jobDescriptionText by remember { mutableStateOf("") }
    var isChecking by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eligibility Checker") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Paste Job Description", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = jobDescriptionText,
                onValueChange = { jobDescriptionText = it },
                modifier = Modifier.fillMaxWidth().height(200.dp),
                placeholder = { Text("Paste the requirements here...") }
            )

            Button(
                onClick = { isChecking = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Check Eligibility")
            }
        }
    }
}