package com.vantedge.app.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vantedge.app.data.model.ApplicationRecord
import com.vantedge.app.data.model.ApplicationStatus
import com.vantedge.app.data.storage.HistoryStore
import com.vantedge.app.notifications.DeadlineNotificationScheduler
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val store: HistoryStore
) : ViewModel() {

    val historyFlow: StateFlow<List<ApplicationRecord>> = store.historyFlow

    fun add(record: ApplicationRecord) {
        viewModelScope.launch {
            store.addRecord(record)
        }
    }

    fun delete(id: Long) {
        store.deleteRecord(id)
    }

    fun clear() {
        store.clearAll()
    }

    fun updateStatus(record: ApplicationRecord, newStatus: ApplicationStatus) {
        val updated = record.copy(status = newStatus)
        store.updateRecord(updated)
    }

    fun setDeadline(record: ApplicationRecord, deadlineMs: Long, context: Context) {
        // Cancel old reminders if any, then schedule new ones
        DeadlineNotificationScheduler.cancel(context, record.id)
        DeadlineNotificationScheduler.schedule(context, record.id, record.jobTitle, record.company, deadlineMs)
        val updated = record.copy(deadline = deadlineMs, reminderSet = true)
        store.updateRecord(updated)
    }

    fun clearDeadline(record: ApplicationRecord, context: Context) {
        DeadlineNotificationScheduler.cancel(context, record.id)
        val updated = record.copy(deadline = null, reminderSet = false)
        store.updateRecord(updated)
    }
}
