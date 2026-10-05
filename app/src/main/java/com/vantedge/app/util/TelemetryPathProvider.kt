package com.vantedge.app.util

import android.content.Context
import java.io.File

object TelemetryPathProvider {
    lateinit var appContext: Context

    fun bind(context: Context) {
        appContext = context.applicationContext
    }

    fun file(): File =
        File(appContext.filesDir, "telemetry.log")
}
