package com.vantedge.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vantedge.app.data.model.*

@Composable
fun ReviewExtractionScreen(
    extraction: StructuredProfileExtraction,
    onConfirm: (UserProfile) -> Unit,
    onBack: () -> Unit
) {
    val teal = Color(0xFF00BFA5)
    val cardDark = Color(0xFF1A1A2E)
    val bg = Color(0xFF0D0D0D)

    // Editable state derived from extraction
    var name by remember { mutableStateOf(extraction.personalInfo.name?.value ?: "") }
    var email by remember { mutableStateOf(extraction.personalInfo.email?.value ?: "") }
    var phone by remember { mutableStateOf(extraction.personalInfo.phone?.value ?: "") }
    var location by remember { mutableStateOf(extraction.personalInfo.location?.value ?: "") }
    var linkedIn by remember { mutableStateOf(extraction.personalInfo.linkedIn?.value ?: "") }
    var summary by remember { mutableStateOf(extraction.summary?.value ?: "") }

    var skills by remember {
        mutableStateOf(extraction.skills.map { it.value }.toMutableList())
    }
    var languages by remember {
        mutableStateOf(extraction.languages.map { it.value }.toMutableList())
    }

    var education by remember {
        mutableStateOf(
            extraction.education.map { edu ->
                buildString {
                    append(edu.qualification.value)
                    if (edu.fieldOfStudy != null) append(" in ${edu.fieldOfStudy.value}")
                    append(" — ${edu.institution.value}")
                    if (edu.graduationYear != null) append(" (${edu.graduationYear.value})")
                }
            }.toMutableList()
        )
    }
    var workHistory by remember {
        mutableStateOf(
            extraction.workHistory.map { exp ->
                WorkExperience(
                    role = exp.jobTitle.value,
                    company = exp.company.value,
                    startDate = exp.startDate?.value ?: "",
                    endDate = exp.endDate?.value ?: "",
                    description = exp.description?.value ?: ""
                )
            }.toMutableList()
        )
    }
    var certifications by remember {
        mutableStateOf(
            extraction.certifications.map { cert ->
                Certification(
                    name = cert.name.value,
                    issuer = cert.issuer?.value ?: ""
                )
            }.toMutableList()
        )
    }

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { showContent = true }

    Scaffold(
        containerColor = bg,
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(bg)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Review what we found. Edit anything that looks off.",                    fontSize = 12.sp,
                    color = Color(0xFF9E9E9E)
                )
                Button(
                    onClick = {
                        onConfirm(
                            UserProfile(
                                name = name,
                                email = email,
                                phone = phone,
                                location = location,
                                linkedIn = linkedIn,
                                summary = summary,
                                skills = skills.filter { it.isNotBlank() },
                                languages = languages.filter { it.isNotBlank() },
                                education = education.filter { it.isNotBlank() },
                                workHistory = workHistory,
                                certifications = certifications
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = teal,
                        contentColor = Color(0xFF0D0D0D)
                    )
                ) {
                    Text("Looks good — continue", fontWeight = FontWeight.Bold)
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
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                // Header
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(                        text = "Here's what we found",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "We understood most of your CV. Correct anything that looks off.",
                        fontSize = 14.sp,
                        color = Color(0xFF9E9E9E),
                        lineHeight = 20.sp
                    )
                }

                // Confidence indicator
                val confidence = extraction.overallConfidence
                ConfidenceBanner(confidence = confidence, teal = teal)

                // Personal info
                ReviewSection(title = "About you", icon = "👤") {
                    ReviewTextField(value = name, label = "Full name", onValueChange = { name = it })
                    ReviewTextField(value = email, label = "Email", onValueChange = { email = it })
                    ReviewTextField(value = phone, label = "Phone", onValueChange = { phone = it })
                    ReviewTextField(value = location, label = "Location", onValueChange = { location = it })
                    ReviewTextField(value = linkedIn, label = "LinkedIn", onValueChange = { linkedIn = it })
                    ReviewTextField(
                        value = summary,
                        label = "Summary",
                        onValueChange = { summary = it },
                        minLines = 3
                    )
                }

                // Skills
                ReviewSection(title = "Skills", icon = "⚡") {
                    ChipEditList(
                        items = skills,
                        teal = teal,
                        onRemove = { index ->
                            skills = skills.toMutableList().also { it.removeAt(index) }
                        },
                        onAdd = { value ->
                            skills = skills.toMutableList().also { it.add(value) }
                        }
                    )
                }

                // Experience
                ReviewSection(title = "Where you've made impact", icon = "💼") {
                    workHistory.forEachIndexed { index, exp ->
                        WorkExperienceCard(                            experience = exp,
                            cardDark = cardDark,
                            teal = teal,
                            onUpdate = { updated ->
                                workHistory = workHistory.toMutableList()
                                    .also { it[index] = updated }
                            },
                            onRemove = {
                                workHistory = workHistory.toMutableList()
                                    .also { it.removeAt(index) }
                            }
                        )
                    }
                    AddItemButton(
                        label = "Add experience",
                        teal = teal,
                        onClick = {
                            workHistory = workHistory.toMutableList().also {
                                it.add(WorkExperience("", "", "", "", ""))
                            }
                        }
                    )
                }

                // Education
                ReviewSection(title = "Education", icon = "🎓") {
                    ChipEditList(
                        items = education,
                        teal = teal,
                        onRemove = { index ->
                            education = education.toMutableList().also { it.removeAt(index) }
                        },
                        onAdd = { value ->
                            education = education.toMutableList().also { it.add(value) }
                        }
                    )
                }

                // Certifications
                ReviewSection(title = "Certifications", icon = "🏅") {
                    certifications.forEachIndexed { index, cert ->
                        CertificationCard(
                            cert = cert,
                            cardDark = cardDark,
                            teal = teal,
                            onUpdate = { updated ->
                                certifications = certifications.toMutableList()
                                    .also { it[index] = updated }
                            },
                            onRemove = {                                certifications = certifications.toMutableList()
                                    .also { it.removeAt(index) }
                            }
                        )
                    }
                    AddItemButton(
                        label = "Add certification",
                        teal = teal,
                        onClick = {
                            certifications = certifications.toMutableList().also {
                                it.add(Certification(name = "", issuer = ""))
                            }
                        }
                    )
                }

                // Languages
                ReviewSection(title = "Languages", icon = "🌐") {
                    ChipEditList(
                        items = languages,
                        teal = teal,
                        onRemove = { index ->
                            languages = languages.toMutableList().also { it.removeAt(index) }
                        },
                        onAdd = { value ->
                            languages = languages.toMutableList().also { it.add(value) }
                        }
                    )
                }

                // Warnings from extraction
                if (extraction.warnings.isNotEmpty()) {
                    WarningsBanner(warnings = extraction.warnings)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// ── Sub-components (FULL implementations) ─────────────────────────────────

@Composable
private fun ConfidenceBanner(confidence: Float, teal: Color) {
    val percent = (confidence * 100).toInt()
    val color = when {
        percent >= 75 -> Color(0xFF4CAF50)
        percent >= 50 -> Color(0xFFFFB830)
        else -> Color(0xFFE53935)    }
    val message = when {
        percent >= 75 -> "Strong read — most fields populated with high confidence."
        percent >= 50 -> "Partial read — some fields may need your attention."
        else -> "Low confidence — please review all fields carefully."
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.08f))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$percent%", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
        Text(text = message, fontSize = 13.sp, color = Color(0xFF9E9E9E), lineHeight = 18.sp)
    }
}

@Composable
private fun ReviewSection(
    title: String,
    icon: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = icon, fontSize = 16.sp)
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        content()
    }
}

@Composable
private fun ReviewTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,    minLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 12.sp) },
        modifier = Modifier.fillMaxWidth(),
        minLines = minLines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color(0xFF00BFA5),
            unfocusedBorderColor = Color(0xFF2A2A2A),
            focusedLabelColor = Color(0xFF00BFA5),
            unfocusedLabelColor = Color(0xFF9E9E9E),
            cursorColor = Color(0xFF00BFA5)
        ),
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
private fun ChipEditList(
    items: List<String>,
    teal: Color,
    onRemove: (Int) -> Unit,
    onAdd: (String) -> Unit
) {
    var inputValue by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (items.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(teal.copy(alpha = 0.08f))
                            .border(1.dp, teal.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = item, fontSize = 13.sp, color = Color.White,
                            modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = { onRemove(index) },
                            modifier = Modifier.size(24.dp)
                        ) {                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Remove",
                                tint = Color(0xFF9E9E9E),
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputValue,
                onValueChange = { inputValue = it },
                placeholder = { Text("Add new...", fontSize = 13.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = teal,
                    unfocusedBorderColor = Color(0xFF2A2A2A),
                    cursorColor = teal
                ),
                shape = RoundedCornerShape(8.dp)
            )
            IconButton(
                onClick = {
                    if (inputValue.isNotBlank()) {
                        onAdd(inputValue.trim())
                        inputValue = ""
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(teal.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = teal)
            }
        }
    }
}

@Composable
private fun WorkExperienceCard(    experience: WorkExperience,
    cardDark: Color,
    teal: Color,
    onUpdate: (WorkExperience) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardDark),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = experience.role.ifBlank { "New role" },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = teal
                )
                IconButton(onClick = onRemove, modifier = Modifier.size(24.dp)) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Remove",
                        tint = Color(0xFF9E9E9E),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            ReviewTextField(
                value = experience.role,
                label = "Job title",
                onValueChange = { onUpdate(experience.copy(role = it)) }
            )
            ReviewTextField(
                value = experience.company,
                label = "Company",
                onValueChange = { onUpdate(experience.copy(company = it)) }
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    ReviewTextField(
                        value = experience.startDate,
                        label = "Start",                        onValueChange = { onUpdate(experience.copy(startDate = it)) }
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    ReviewTextField(
                        value = experience.endDate,
                        label = "End",
                        onValueChange = { onUpdate(experience.copy(endDate = it)) }
                    )
                }
            }
            ReviewTextField(
                value = experience.description,
                label = "What you did",
                onValueChange = { onUpdate(experience.copy(description = it)) },
                minLines = 2
            )
        }
    }
}

@Composable
private fun CertificationCard(
    cert: Certification,
    cardDark: Color,
    teal: Color,
    onUpdate: (Certification) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardDark),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cert.name.ifBlank { "New certification" },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = teal
                )
                IconButton(onClick = onRemove, modifier = Modifier.size(24.dp)) {                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Remove",
                        tint = Color(0xFF9E9E9E),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            ReviewTextField(
                value = cert.name,
                label = "Certificate name",
                onValueChange = { onUpdate(cert.copy(name = it)) }
            )
            ReviewTextField(
                value = cert.issuer,
                label = "Issuer",
                onValueChange = { onUpdate(cert.copy(issuer = it)) }
            )
        }
    }
}

@Composable
private fun AddItemButton(label: String, teal: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFF2A2A2A), RoundedCornerShape(10.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Add, contentDescription = null, tint = teal, modifier = Modifier.size(16.dp))
            Text(text = label, fontSize = 13.sp, color = teal)
        }
    }
}

@Composable
private fun WarningsBanner(warnings: List<String>) {
    Column(        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFFB830).copy(alpha = 0.08f))
            .border(1.dp, Color(0xFFFFB830).copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text("⚠ Things to double-check", fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold, color = Color(0xFFFFB830))
        warnings.forEach { warning ->
            Text("• $warning", fontSize = 12.sp, color = Color(0xFF9E9E9E))
        }
    }
} 