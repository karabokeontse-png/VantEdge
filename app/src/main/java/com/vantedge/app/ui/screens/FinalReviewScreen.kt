package com.vantedge.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.ui.theme.AppColors
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FinalReviewScreen(
    profile: UserProfile,
    errorMessage: String? = null,
    onConfirm: () -> Unit,
    onBack: () -> Unit,
    onClearError: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showContent by remember { mutableStateOf(false) }
    val teal = Color(0xFF00BFA5)
    val cardDark = Color(0xFF1A1A2E)
    val subtle = Color(0xFF9E9E9E)

    LaunchedEffect(Unit) { showContent = true }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(message = it, duration = SnackbarDuration.Short)
            onClearError()
        }
    }

    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Almost there",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.Background
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = AppColors.Destructive,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(AppColors.Background)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "This is what we'll use to power your career tools.",
                    fontSize = 12.sp,
                    color = subtle
                )
                Button(
                    onClick = onConfirm,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = teal,
                        contentColor = AppColors.Background
                    )
                ) {
                    Text(
                        "Activate VantEdge",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    ) { padding ->

        AnimatedVisibility(
            visible = showContent,
            enter = fadeIn(tween(500))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                // ── Header ────────────────────────────────────────────────
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Here's your profile",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Go back to change anything before we lock it in.",
                        fontSize = 14.sp,
                        color = subtle,
                        lineHeight = 20.sp
                    )
                }

                // ── Identity card ─────────────────────────────────────────
                SummaryCard(title = "Identity", teal = teal, cardDark = cardDark) {
                    SummaryRow(label = "Name", value = profile.name.ifBlank { "—" })
                    SummaryRow(label = "Email", value = profile.email.ifBlank { "—" })
                    if (profile.phone.isNotBlank())
                        SummaryRow(label = "Phone", value = profile.phone)
                    if (profile.location.isNotBlank())
                        SummaryRow(label = "Location", value = profile.location)
                    if (profile.linkedIn.isNotBlank())
                        SummaryRow(label = "LinkedIn", value = profile.linkedIn)
                }

                // ── Summary ───────────────────────────────────────────────
                if (profile.summary.isNotBlank()) {
                    SummaryCard(title = "Summary", teal = teal, cardDark = cardDark) {
                        Text(
                            text = profile.summary,
                            fontSize = 13.sp,
                            color = Color(0xFFCCCCCC),
                            lineHeight = 20.sp
                        )
                    }
                }

                // ── Skills ────────────────────────────────────────────────
                SummaryCard(
                    title = "Skills",
                    teal = teal,
                    cardDark = cardDark,
                    badge = profile.skills.size
                ) {
                    if (profile.skills.isEmpty()) {
                        Text("No skills added.", fontSize = 13.sp, color = subtle)
                    } else {
                        // Wrap chips in a flowing row layout
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            profile.skills.forEach { skill ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(teal.copy(alpha = 0.1f))
                                        .border(
                                            1.dp,
                                            teal.copy(alpha = 0.25f),
                                            RoundedCornerShape(6.dp)
                                        )
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = skill,
                                        fontSize = 12.sp,
                                        color = teal
                                    )
                                }
                            }
                        }
                    }
                }

                // ── Work experience ───────────────────────────────────────
                SummaryCard(
                    title = "Work experience",
                    teal = teal,
                    cardDark = cardDark,
                    badge = profile.workHistory.size
                ) {
                    if (profile.workHistory.isEmpty()) {
                        Text("No experience added.", fontSize = 13.sp, color = subtle)
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            profile.workHistory.forEach { exp ->
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text(
                                        text = exp.role.ifBlank { "Untitled role" },
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = buildString {
                                            append(exp.company.ifBlank { "Unknown company" })
                                            if (exp.startDate.isNotBlank()) {
                                                append("  ·  ${exp.startDate}")
                                                if (exp.endDate.isNotBlank()) append(" – ${exp.endDate}")
                                            }
                                        },
                                        fontSize = 12.sp,
                                        color = subtle
                                    )
                                    if (exp.description.isNotBlank()) {
                                        Text(
                                            text = exp.description,
                                            fontSize = 12.sp,
                                            color = Color(0xFFAAAAAA),
                                            lineHeight = 18.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // ── Education ─────────────────────────────────────────────
                if (profile.education.isNotEmpty()) {
                    SummaryCard(
                        title = "Education",
                        teal = teal,
                        cardDark = cardDark,
                        badge = profile.education.size
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            profile.education.forEach { entry ->
                                Text(
                                    text = entry,
                                    fontSize = 13.sp,
                                    color = Color(0xFFCCCCCC),
                                    lineHeight = 19.sp
                                )
                            }
                        }
                    }
                }

                // ── Certifications ────────────────────────────────────────
                if (profile.certifications.isNotEmpty()) {
                    SummaryCard(
                        title = "Certifications",
                        teal = teal,
                        cardDark = cardDark,
                        badge = profile.certifications.size
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            profile.certifications.forEach { cert ->
                                Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                                    Text(
                                        text = cert.name.ifBlank { "Unnamed" },
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White
                                    )
                                    if (cert.issuer.isNotBlank()) {
                                        Text(
                                            text = cert.issuer,
                                            fontSize = 12.sp,
                                            color = subtle
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // ── Languages ─────────────────────────────────────────────
                if (profile.languages.isNotEmpty()) {
                    SummaryCard(
                        title = "Languages",
                        teal = teal,
                        cardDark = cardDark,
                        badge = profile.languages.size
                    ) {
                        Text(
                            text = profile.languages.joinToString("  ·  "),
                            fontSize = 13.sp,
                            color = Color(0xFFCCCCCC)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

// ── Sub-components ────────────────────────────────────────────────────────

@Composable
private fun SummaryCard(
    title: String,
    teal: Color,
    cardDark: Color,
    badge: Int? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardDark),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = teal,
                    letterSpacing = 0.5.sp
                )
                if (badge != null && badge > 0) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(teal.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = badge.toString(),
                            fontSize = 11.sp,
                            color = teal,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            content()
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF9E9E9E),
            modifier = Modifier.weight(0.35f)
        )
        Text(
            text = value,
            fontSize = 13.sp,
            color = Color.White,
            modifier = Modifier.weight(0.65f),
            lineHeight = 19.sp
        )
    }
}
