package com.vantedge.app.data.viewmodel

sealed class ExportEvent {
    object Success : ExportEvent()
    data class Error(val message: String) : ExportEvent()
    object OpenDocument : ExportEvent()
}
