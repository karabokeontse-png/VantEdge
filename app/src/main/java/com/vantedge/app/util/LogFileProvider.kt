package com.vantedge.app.util

import android.content.Context
import java.io.File

object LogFileProvider {
    lateinit var appContext: Context

    fun bind(context: Context) {
        appContext = context.applicationContext
    }

    fun file(): File =
        File(appContext.cacheDir, "vantedge_trace.log")
}
