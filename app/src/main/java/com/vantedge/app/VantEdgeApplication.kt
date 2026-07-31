package com.vantedge.app

import android.app.Application
import android.util.Log
import com.vantedge.app.util.LogFileProvider
import com.vantedge.app.w5.scoring.ExtractionValidator
import com.vantedge.app.w5.scoring.SkillTaxonomyProvider
import com.vantedge.pipeline.validation.P2ValidationEngine

class VantEdgeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LogFileProvider.bind(this)
        P2ValidationEngine.initialize(this)
        ExtractionValidator.init(this)
        SkillTaxonomyProvider.load(this)
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
