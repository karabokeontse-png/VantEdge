package com.vantedge.app.data.viewmodel

sealed class CycleNavEvent {
    object ToAnalysisResult : CycleNavEvent()
    data class ToCycleRestored(val cycleId: String, val stage: CycleStage) : CycleNavEvent()
    object ToDesignPicker : CycleNavEvent()
    object ToFinalResult : CycleNavEvent()
}
