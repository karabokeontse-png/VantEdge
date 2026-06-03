package com.vantedge.app.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vantedge.app.data.model.ApplicationRecord
import com.vantedge.app.data.model.ApplicationStatus
import com.vantedge.app.data.viewmodel.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.*

private val HBg    = Color(0xFF0D0D0D)
private val HCard  = Color(0xFF1A1A2E)
private val HTeal  = Color(0xFF00BFA5)
private val HAmber = Color(0xFFFFB830)
private val HOnBg  = Color(0xFFE0E0E0)

private const val URGENT_MS   = 2 * 24 * 60 * 60 * 1000L
private const val UPCOMING_MS = 7 * 24 * 60 * 60 * 1000L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
history: List<ApplicationRecord>,
viewModel: HistoryViewModel,
onOpen: (ApplicationRecord) -> Unit
) {
val context = LocalContext.current
val dateFormatter = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }

val sorted = remember(history) {  
    history.sortedWith(  
        compareBy<ApplicationRecord> { it.deadline == null }  
            .thenBy { it.deadline ?: Long.MAX_VALUE }  
            .thenByDescending { it.createdAt }  
    )  
}  

Scaffold(  
    containerColor = HBg,  
    topBar = {  
        TopAppBar(  
            title = { Text("Application History", color = HOnBg) },  
            colors = TopAppBarDefaults.topAppBarColors(containerColor = HCard)  
        )  
    }  
) { paddingValues ->  

    if (history.isEmpty()) {  
        Box(  
            modifier = Modifier  
                .fillMaxSize()  
                .padding(paddingValues),  
            contentAlignment = Alignment.Center  
        ) {  
            Text("No saved applications yet", color = Color.Gray)  
        }  
        return@Scaffold  
    }  

    LazyColumn(  
        modifier = Modifier  
            .fillMaxSize()  
            .padding(paddingValues),  
        contentPadding = PaddingValues(12.dp),  
        verticalArrangement = Arrangement.spacedBy(12.dp)  
    ) {  
        items(sorted, key = { it.id }) { record ->  
            ApplicationCard(  
                record = record,  
                dateFormatter = dateFormatter,  
                onOpen = { onOpen(record) },  
                onDelete = { viewModel.delete(record.id) },  
                onStatusChange = { newStatus -> viewModel.updateStatus(record, newStatus) },  
                onSetDeadline = { deadlineMs -> viewModel.setDeadline(record, deadlineMs, context) },  
                onClearDeadline = { viewModel.clearDeadline(record, context) }  
            )  
        }  
    }  
}

}

@Composable
private fun ApplicationCard(
record: ApplicationRecord,
dateFormatter: SimpleDateFormat,
onOpen: () -> Unit,
onDelete: () -> Unit,
onStatusChange: (ApplicationStatus) -> Unit,
onSetDeadline: (Long) -> Unit,
onClearDeadline: () -> Unit
) {
val context = LocalContext.current
val now = System.currentTimeMillis()
var showStatusMenu by remember { mutableStateOf(false) }

val urgencyColor: Color? = record.deadline?.let { dl ->  
    val remaining = dl - now  
    when {  
        remaining < 0               -> Color(0xFF616161)  
        remaining <= URGENT_MS      -> Color(0xFFE53935)  
        remaining <= UPCOMING_MS    -> HAmber  
        else                        -> null  
    }  
}  

val urgencyLabel: String? = record.deadline?.let { dl ->  
    val remaining = dl - now  
    when {  
        remaining < 0 -> "Expired"  
        remaining <= URGENT_MS -> {  
            val hours = (remaining / (60 * 60 * 1000)).toInt()  
            if (hours < 24) "Closes in ${hours}h" else "Closes tomorrow"  
        }  
        remaining <= UPCOMING_MS -> {  
            val days = (remaining / (24 * 60 * 60 * 1000)).toInt()  
            "Closes in ${days} days"  
        }  
        else -> "Deadline: ${dateFormatter.format(Date(dl))}"  
    }  
}  

Card(  
    modifier = Modifier.fillMaxWidth(),  
    colors = CardDefaults.cardColors(containerColor = HCard),  
    shape = RoundedCornerShape(12.dp)  
) {  
    // Urgency stripe  
    if (urgencyColor != null) {  
        Box(  
            modifier = Modifier  
                .fillMaxWidth()  
                .height(3.dp)  
                .background(urgencyColor)  
        )  
    }  

    Column(modifier = Modifier.padding(14.dp)) {  

        Row(  
            modifier = Modifier.fillMaxWidth(),  
            horizontalArrangement = Arrangement.SpaceBetween,  
            verticalAlignment = Alignment.Top  
        ) {  
            Column(modifier = Modifier.weight(1f)) {  
                Text(record.jobTitle, color = HOnBg, fontWeight = FontWeight.Bold, fontSize = 15.sp)  
                Text(record.company, color = Color.Gray, fontSize = 13.sp)  
            }  
            StatusChip(status = record.status, onClick = { showStatusMenu = true })  
        }  

        DropdownMenu(  
            expanded = showStatusMenu,  
            onDismissRequest = { showStatusMenu = false }  
        ) {  
            ApplicationStatus.values().forEach { status ->  
                DropdownMenuItem(  
                    text = { Text(status.label()) },  
                    onClick = {  
                        onStatusChange(status)  
                        showStatusMenu = false  
                    }  
                )  
            }  
        }  

        if (urgencyLabel != null) {  
            Spacer(modifier = Modifier.height(8.dp))  
            Row(verticalAlignment = Alignment.CenterVertically) {  
                Icon(  
                    Icons.Default.CalendarToday,  
                    contentDescription = null,  
                    tint = urgencyColor ?: HTeal,  
                    modifier = Modifier.size(13.dp)  
                )  
                Spacer(modifier = Modifier.width(4.dp))  
                Text(urgencyLabel, color = urgencyColor ?: HTeal, fontSize = 12.sp, fontWeight = FontWeight.Medium)  
                Spacer(modifier = Modifier.width(8.dp))  
                TextButton(onClick = onClearDeadline, contentPadding = PaddingValues(0.dp)) {  
                    Text("Clear", color = Color.Gray, fontSize = 11.sp)  
                }  
            }  
        }  

        Spacer(modifier = Modifier.height(10.dp))  

        Row(  
            modifier = Modifier.fillMaxWidth(),  
            horizontalArrangement = Arrangement.spacedBy(8.dp)  
        ) {  
            Button(  
                onClick = onOpen,  
                modifier = Modifier.weight(1f),  
                colors = ButtonDefaults.buttonColors(containerColor = HTeal, contentColor = Color.Black),  
                contentPadding = PaddingValues(vertical = 8.dp)  
            ) { Text("View", fontSize = 13.sp, fontWeight = FontWeight.Bold) }  

            OutlinedButton(  
                onClick = {  
                    val cal = Calendar.getInstance()  
                    record.deadline?.let { cal.timeInMillis = it }  
                    DatePickerDialog(  
                        context,  
                        { _, year, month, day ->  
                            val selected = Calendar.getInstance().apply {  
                                set(year, month, day, 23, 59, 59)  
                            }.timeInMillis  
                            onSetDeadline(selected)  
                        },  
                        cal.get(Calendar.YEAR),  
                        cal.get(Calendar.MONTH),  
                        cal.get(Calendar.DAY_OF_MONTH)  
                    ).apply {  
                        datePicker.minDate = System.currentTimeMillis()  
                    }.show()  
                },  
                modifier = Modifier.weight(1f),  
                colors = ButtonDefaults.outlinedButtonColors(contentColor = HAmber),  
                contentPadding = PaddingValues(vertical = 8.dp)  
            ) {  
                Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(14.dp))  
                Spacer(modifier = Modifier.width(4.dp))  
                Text(if (record.deadline == null) "Set Deadline" else "Change", fontSize = 13.sp)  
            }  

            IconButton(onClick = onDelete, modifier = Modifier.size(40.dp)) {  
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFE53935))  
            }  
        }  
    }  
}

}

@Composable
private fun StatusChip(status: ApplicationStatus, onClick: () -> Unit) {
val (bg, fg) = when (status) {
ApplicationStatus.SAVED      -> Color(0xFF1A1A2E) to Color.Gray
ApplicationStatus.APPLIED    -> Color(0xFF00BFA5).copy(alpha = 0.15f) to Color(0xFF00BFA5)
ApplicationStatus.INTERVIEW  -> Color(0xFFFFB830).copy(alpha = 0.15f) to Color(0xFFFFB830)
ApplicationStatus.OFFERED    -> Color(0xFF7C4DFF).copy(alpha = 0.15f) to Color(0xFF7C4DFF)
ApplicationStatus.REJECTED   -> Color(0xFFE53935).copy(alpha = 0.15f) to Color(0xFFE53935)
}
Box(
modifier = Modifier
.background(bg, RoundedCornerShape(6.dp))
.padding(horizontal = 10.dp, vertical = 4.dp)
) {
TextButton(
onClick = onClick,
contentPadding = PaddingValues(0.dp),
modifier = Modifier.height(20.dp)
) {
Text(status.label(), color = fg, fontSize = 11.sp, fontWeight = FontWeight.Bold)
}
}
}

private fun ApplicationStatus.label() = when (this) {
ApplicationStatus.SAVED     -> "Saved"
ApplicationStatus.APPLIED   -> "Applied"
ApplicationStatus.INTERVIEW -> "Interview"
ApplicationStatus.OFFERED   -> "Offered 🎉"
ApplicationStatus.REJECTED  -> "Rejected"
}