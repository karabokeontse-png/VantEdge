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
import androidx.compose.material.icons.filled.ArrowBack
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
import com.vantedge.app.data.model.Certification
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.model.WorkExperience

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    initialProfile: UserProfile?,
    onContinue: (UserProfile) -> Unit
) {
    val teal = Color(0xFF00BFA5)
    val bg = Color(0xFF0D0D0D)
    val cardDark = Color(0xFF1A1A2E)

    // ── Editable state ────────────────────────────────────────────────────
    var name by remember { mutableStateOf(initialProfile?.name ?: "") }
    var email by remember { mutableStateOf(initialProfile?.email ?: "") }
    var phone by remember { mutableStateOf(initialProfile?.phone ?: "") }
    var location by remember { mutableStateOf(initialProfile?.location ?: "") }
    var linkedIn by remember { mutableStateOf(initialProfile?.linkedIn ?: "") }
    var summary by remember { mutableStateOf(initialProfile?.summary ?: "") }

    var skills by remember {
        mutableStateOf(initialProfile?.skills?.toMutableList() ?: mutableListOf())
    }
    var languages by remember {
        mutableStateOf(initialProfile?.languages?.toMutableList() ?: mutableListOf())
    }
    var education by remember {
        mutableStateOf(initialProfile?.education?.toMutableList() ?: mutableListOf())
    }
    var workHistory by remember {
        mutableStateOf(initialProfile?.workHistory?.toMutableList() ?: mutableListOf())
    }
    var certifications by remember {
        mutableStateOf(initialProfile?.certifications?.toMutableList() ?: mutableListOf())
    }

    // Continue is enabled once minimum fields are filled
    val canContinue = name.isNotBlank() && email.isNotBlank() && skills.isNotEmpty()

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { showContent = true }

    Scaffold(
        containerColor = bg,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Build your profile",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = bg)
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = canContinue,
                enter = fadeIn(tween(400)) + slideInVertically(tween(400), initialOffsetY = { it })
            ) {
                Column(
                    modifier = Modifier
                        .background(bg)
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Looking good. Review before we lock it in.",
                        fontSize = 12.sp,
                        color = Color(0xFF9E9E9E)
                    )
                    Button(
                        onClick = {
                            onContinue(
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
                        Text("Review my profile", fontWeight = FontWeight.Bold)
                    }
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
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {

                // Header
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Tell us about yourself",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Fill in what you can — you can always update this later.",
                        fontSize = 14.sp,
                        color = Color(0xFF9E9E9E),
                        lineHeight = 20.sp
                    )
                }

                // ── Personal info ─────────────────────────────────────────
                EditSection(title = "About you", icon = "👤") {
                    EditTextField(value = name, label = "Full name *", onValueChange = { name = it })
                    EditTextField(value = email, label = "Email *", onValueChange = { email = it })
                    EditTextField(value = phone, label = "Phone", onValueChange = { phone = it })
                    EditTextField(value = location, label = "Location", onValueChange = { location = it })
                    EditTextField(value = linkedIn, label = "LinkedIn URL", onValueChange = { linkedIn = it })
                    EditTextField(
                        value = summary,
                        label = "Professional summary",
                        onValueChange = { summary = it },
                        minLines = 3
                    )
                }

                // ── Skills (required) ─────────────────────────────────────
                EditSection(title = "Skills *", icon = "⚡") {
                    if (skills.isEmpty()) {
                        Text(
                            text = "Add at least one skill to continue.",
                            fontSize = 12.sp,
                            color = Color(0xFFFFB830)
                        )
                    }
                    EditChipList(
                        items = skills,
                        teal = teal,
                        placeholder = "e.g. Project Management",
                        onRemove = { index ->
                            skills = skills.toMutableList().also { it.removeAt(index) }
                        },
                        onAdd = { value ->
                            skills = skills.toMutableList().also { it.add(value) }
                        }
                    )
                }

                // ── Work history ──────────────────────────────────────────
                EditSection(title = "Work experience", icon = "💼") {
                    workHistory.forEachIndexed { index, exp ->
                        EditWorkCard(
                            experience = exp,
                            cardDark = cardDark,
                            teal = teal,
                            onUpdate = { updated ->
                                workHistory = workHistory.toMutableList().also { it[index] = updated }
                            },
                            onRemove = {
                                workHistory = workHistory.toMutableList().also { it.removeAt(index) }
                            }
                        )
                    }
                    EditAddButton(
                        label = "Add experience",
                        teal = teal,
                        onClick = {
                            workHistory = workHistory.toMutableList()
                                .also { it.add(WorkExperience("", "", "", "", "")) }
                        }
                    )
                }

                // ── Education ─────────────────────────────────────────────
                EditSection(title = "Education", icon = "🎓") {
                    EditChipList(
                        items = education,
                        teal = teal,
                        placeholder = "e.g. BSc Computer Science — MIT (2020)",
                        onRemove = { index ->
                            education = education.toMutableList().also { it.removeAt(index) }
                        },
                        onAdd = { value ->
                            education = education.toMutableList().also { it.add(value) }
                        }
                    )
                }

                // ── Certifications ────────────────────────────────────────
                EditSection(title = "Certifications", icon = "🏅") {
                    certifications.forEachIndexed { index, cert ->
                        EditCertCard(
                            cert = cert,
                            cardDark = cardDark,
                            teal = teal,
                            onUpdate = { updated ->
                                certifications = certifications.toMutableList().also { it[index] = updated }
                            },
                            onRemove = {
                                certifications = certifications.toMutableList().also { it.removeAt(index) }
                            }
                        )
                    }
                    EditAddButton(
                        label = "Add certification",
                        teal = teal,
                        onClick = {
                            certifications = certifications.toMutableList()
                                .also { it.add(Certification(name = "", issuer = "")) }
                        }
                    )
                }

                // ── Languages ─────────────────────────────────────────────
                EditSection(title = "Languages", icon = "🌐") {
                    EditChipList(
                        items = languages,
                        teal = teal,
                        placeholder = "e.g. English, French",
                        onRemove = { index ->
                            languages = languages.toMutableList().also { it.removeAt(index) }
                        },
                        onAdd = { value ->
                            languages = languages.toMutableList().also { it.add(value) }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// ── Sub-components ────────────────────────────────────────────────────────

@Composable
private fun EditSection(
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
private fun EditTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    minLines: Int = 1
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
private fun EditChipList(
    items: List<String>,
    teal: Color,
    placeholder: String,
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
                        Text(
                            text = item,
                            fontSize = 13.sp,
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { onRemove(index) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
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
                placeholder = { Text(placeholder, fontSize = 13.sp) },
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
private fun EditWorkCard(
    experience: WorkExperience,
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
            EditTextField(
                value = experience.role,
                label = "Job title",
                onValueChange = { onUpdate(experience.copy(role = it)) }
            )
            EditTextField(
                value = experience.company,
                label = "Company",
                onValueChange = { onUpdate(experience.copy(company = it)) }
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    EditTextField(
                        value = experience.startDate,
                        label = "Start date",
                        onValueChange = { onUpdate(experience.copy(startDate = it)) }
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    EditTextField(
                        value = experience.endDate,
                        label = "End date",
                        onValueChange = { onUpdate(experience.copy(endDate = it)) }
                    )
                }
            }
            EditTextField(
                value = experience.description,
                label = "What you did",
                onValueChange = { onUpdate(experience.copy(description = it)) },
                minLines = 2
            )
        }
    }
}

@Composable
private fun EditCertCard(
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
                IconButton(onClick = onRemove, modifier = Modifier.size(24.dp)) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Remove",
                        tint = Color(0xFF9E9E9E),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            EditTextField(
                value = cert.name,
                label = "Certificate name",
                onValueChange = { onUpdate(cert.copy(name = it)) }
            )
            EditTextField(
                value = cert.issuer,
                label = "Issuer",
                onValueChange = { onUpdate(cert.copy(issuer = it)) }
            )
        }
    }
}

@Composable
private fun EditAddButton(label: String, teal: Color, onClick: () -> Unit) {
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
