package com.vantedge.app.data.viewmodel

sealed class RestorationState {
    object Loading : RestorationState()
    object None : RestorationState()
    data class Restored(val cycleId: String) : RestorationState()
}
