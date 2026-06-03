package com.vantedge.app.data.storage

import com.vantedge.app.data.model.CompatibilityRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CompatibilityStore {

    private val mutex = Mutex()

    private val _historyFlow = MutableStateFlow<List<CompatibilityRecord>>(emptyList())
    val historyFlow: StateFlow<List<CompatibilityRecord>> = _historyFlow

    suspend fun addRecord(record: CompatibilityRecord) {
        mutex.withLock {
            val updated = _historyFlow.value.toMutableList()
            updated.add(0, record)
            _historyFlow.value = updated
        }
    }

    fun deleteRecord(id: Long) {
        _historyFlow.value = _historyFlow.value.filter { it.id != id }
    }

    fun clearAll() {
        _historyFlow.value = emptyList()
    }
}