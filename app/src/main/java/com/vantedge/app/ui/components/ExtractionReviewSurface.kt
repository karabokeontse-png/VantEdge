package com.vantedge.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.app.domain.extraction.ExtractionReview
import com.vantedge.app.domain.extraction.FieldReview
import kotlin.math.roundToInt

private val Teal = Color(0xFF00BFA5)
private val Amber = Color(0xFFFFB830)
private val CardDark = Color(0xFF1A1A2E)
private val Muted = Color(0xFF9E9E9E)

/**
 * Renders the pending-review dialog and, if the user closes it without
 * acknowledging, the persistent banner that keeps downstream actions blocked.
 */
@Composable
fun ExtractionReviewSurface(
    review: ExtractionReview,
    onConfirm: (JobExtractionResult) -> Unit
) {
    var dialogVisible by remember(review) { mutableStateOf(true) }

    if (dialogVisible) {
        ExtractionReviewDialog(
            review = review,
            onDismiss = { dialogVisible = false },
            onConfirm = { corrected ->
                dialogVisible = false
                onConfirm(corrected)
            }
        )
    } else {
        ReviewPendingBanner(review = review, onReview = { dialogVisible = true })
    }
}

@Composable
private fun ExtractionReviewDialog(
    review: ExtractionReview,
    onDismiss: () -> Unit,
    onConfirm: (JobExtractionResult) -> Unit
) {
    var title by remember(review) { mutableStateOf(review.result.jobTitle.orEmpty()) }
    var company by remember(review) { mutableStateOf(review.result.company.orEmpty()) }
    var description by remember(review) { mutableStateOf(review.result.description) }
    var requiredSkills by remember(review) { mutableStateOf(review.result.requiredSkills.joinToString(", ")) }
    var preferredSkills by remember(review) { mutableStateOf(review.result.preferredSkills.joinToString(", ")) }
    var education by remember(review) { mutableStateOf(review.result.educationRequired.orEmpty()) }

    val fieldsValid = title.isNotBlank() && company.isNotBlank() && description.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CardDark,
        titleContentColor = Color.White,
        textContentColor = Color(0xFFE0E0E0),
        title = {
            Text(
                if (review.isUnvalidatedFallback) "Unvalidated extraction" else "Review extraction",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 440.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (review.isUnvalidatedFallback) {
                    Text(
                        "The extraction fell back to an emergency reader. It has not been validated.",
                        color = Amber,
                        fontSize = 13.sp
                    )
                }

                Text(
                    "Estimated confidence: ${(review.confidenceScore * 100).roundToInt()}% — " +
                        "this is not proof that the extracted fields are correct.",
                    color = Muted,
                    fontSize = 12.sp
                )

                if (review.warnings.isNotEmpty()) {
                    Text("Extraction warnings", color = Amber, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    review.warnings.forEach { warning ->
                        Text("• $warning", color = Color(0xFFE0E0E0), fontSize = 12.sp)
                    }
                }

                Text("Fields requiring review", color = Teal, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                if (review.fieldReviews.isEmpty()) {
                    Text(
                        "No field-specific details were supplied. Review the values below.",
                        color = Muted,
                        fontSize = 12.sp
                    )
                } else {
                    review.fieldReviews.forEach { field -> FieldReviewRow(field) }
                }

                ReviewField(
                    value = title,
                    onValueChange = { title = it },
                    label = "Job title"
                )
                ReviewField(
                    value = company,
                    onValueChange = { company = it },
                    label = "Company"
                )
                ReviewField(
                    value = description,
                    onValueChange = { description = it },
                    label = "Job description",
                    minLines = 4
                )
                ReviewField(
                    value = requiredSkills,
                    onValueChange = { requiredSkills = it },
                    label = "Required skills (comma separated)"
                )
                ReviewField(
                    value = preferredSkills,
                    onValueChange = { preferredSkills = it },
                    label = "Preferred skills (comma separated)"
                )
                ReviewField(
                    value = education,
                    onValueChange = { education = it },
                    label = "Education required"
                )

                Text(
                    "Continuing confirms you reviewed the flagged fields. Your corrections are used " +
                        "for compatibility analysis and generation.",
                    color = Muted,
                    fontSize = 12.sp
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(
                        review.result.copy(
                            jobTitle = title.trim().ifBlank { null },
                            company = company.trim().ifBlank { null },
                            description = description.trim(),
                            requiredSkills = parseSkills(requiredSkills),
                            preferredSkills = parseSkills(preferredSkills),
                            educationRequired = education.trim().ifBlank { null }
                        )
                    )
                },
                enabled = fieldsValid,
                colors = ButtonDefaults.textButtonColors(contentColor = Teal)
            ) {
                Text("Use reviewed fields", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, colors = ButtonDefaults.textButtonColors(contentColor = Muted)) {
                Text("Review later")
            }
        }
    )
}

@Composable
private fun FieldReviewRow(field: FieldReview) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            fieldLabel(field.fieldName),
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        )
        val confidenceText = field.confidence.overallConfidence
            ?.let { "${(it * 100).roundToInt()}%" }
            ?: "unknown"
        Text(
            "${field.reviewReason} (confidence $confidenceText)",
            color = Amber,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun ReviewField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    minLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        minLines = minLines,
        maxLines = if (minLines > 1) Int.MAX_VALUE else 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedLabelColor = Teal,
            unfocusedLabelColor = Muted,
            focusedBorderColor = Teal,
            unfocusedBorderColor = Color.Gray
        )
    )
}

@Composable
private fun ReviewPendingBanner(review: ExtractionReview, onReview: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Amber.copy(alpha = 0.08f))
            .border(1.dp, Amber.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            if (review.isUnvalidatedFallback) {
                "Unvalidated extraction — review required"
            } else {
                "Extraction review required"
            },
            color = Amber,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp
        )
        Text(
            "Compatibility analysis and generation remain paused until you review and confirm the extracted fields.",
            color = Color(0xFFE0E0E0),
            fontSize = 12.sp
        )
        review.warnings.forEach { warning ->
            Text("• $warning", color = Muted, fontSize = 12.sp)
        }
        TextButton(
            onClick = onReview,
            colors = ButtonDefaults.textButtonColors(contentColor = Teal)
        ) {
            Text("Review extracted fields", fontWeight = FontWeight.Bold)
        }
    }
}

private fun parseSkills(raw: String): List<String> =
    raw.split(',', '\n')
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .distinct()

private fun fieldLabel(name: String): String = when (name.lowercase()) {
    "jobtitle" -> "Job title"
    "company" -> "Company"
    "description" -> "Job description"
    "requiredskills" -> "Required skills"
    "preferredskills" -> "Preferred skills"
    "educationrequired" -> "Education required"
    else -> name
}
