package com.vantedge.app.data.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vantedge.app.data.model.TelemetryRecord
import com.vantedge.app.data.storage.TelemetryStorage
import com.vantedge.app.util.TelemetryExportService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DebugViewModel(
    context: Context,
    private val telemetryStorage: TelemetryStorage
) : ViewModel() {

    companion object {
        private const val TAG = "DebugViewModel"

        fun factory(
            context: Context,
            telemetryStorage: TelemetryStorage
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DebugViewModel(
                        context.applicationContext,
                        telemetryStorage
                    ) as T
                }
            }
        }
    }

    private val appContext = context.applicationContext
    private val exportService = TelemetryExportService(appContext, telemetryStorage)

    private val _records = MutableStateFlow<List<TelemetryRecord>>(emptyList())
    val records: StateFlow<List<TelemetryRecord>> = _records.asStateFlow()

    private val _exportEvent = MutableSharedFlow<Intent>(extraBufferCapacity = 1)
    val exportEvent: SharedFlow<Intent> = _exportEvent.asSharedFlow()

    private val _isExporting = MutableStateFlow(false)
    val isExporting: StateFlow<Boolean> = _isExporting.asStateFlow()

    init {
        viewModelScope.launch {
            Log.i(TAG, "[DebugViewModel] Init — loading recent records.")
            val parsed = telemetryStorage.getRecentRecords()
            _records.value = parsed
            Log.i(TAG, "[DebugViewModel] Init complete — loaded ${parsed.size} records.")
        }
    }

    fun refreshRecords() {
        viewModelScope.launch {
            Log.i(TAG, "[DebugViewModel] Manual refresh triggered.")
            val parsed = telemetryStorage.getRecentRecords()
            _records.value = parsed
            Log.i(TAG, "[DebugViewModel] Refresh complete — ${parsed.size} records.")
        }
    }

    fun exportTelemetry() {
        if (_isExporting.value) {
            Log.w(TAG, "[DebugViewModel] Export already in progress — ignoring call.")
            return
        }
        viewModelScope.launch {
            _isExporting.value = true
            try {
                val result = exportService.createExportFile()
                if (result != null) {
                    val (file, _) = result
                    val intent = exportService.getShareIntent(file)
                    if (intent != null) {
                        _exportEvent.emit(intent)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "[DebugViewModel] Export failed: ${e.message}")
            } finally {
                _isExporting.value = false
            }
        }
    }
}
