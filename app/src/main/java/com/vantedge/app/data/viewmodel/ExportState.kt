package com.vantedge.app.data.viewmodel

sealed class ExportState {
    object Idle : ExportState()
    object Exporting : ExportState()
    object Ready : ExportState()
}
