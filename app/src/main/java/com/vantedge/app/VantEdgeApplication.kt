package com.vantedge.app

import android.app.Application
import android.util.Log

class VantEdgeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
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
