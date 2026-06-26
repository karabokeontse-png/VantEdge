package com.vantedge.app

import android.app.Application
import android.util.Log
import com.vantedge.app.util.LogFileProvider

class VantEdgeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LogFileProvider.bind(this)
        Log.i(
            "VantEdge",
            "STARTUP version=${BuildConfig.VERSION_NAME} " +
            "code=${BuildConfig.VERSION_CODE} " +
            "build=${BuildConfig.BUILD_TYPE} " +
            "appId=${BuildConfig.APPLICATION_ID}" +
            " startedAt=${System.currentTimeMillis()}"
        )
    }
}
