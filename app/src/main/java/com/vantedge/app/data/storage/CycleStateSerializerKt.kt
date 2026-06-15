package com.vantedge.app.data.storage

import com.google.gson.reflect.TypeToken
import com.vantedge.app.data.model.CompatibilityRecord
import java.lang.reflect.Type

internal const val TYPE_KEY = "type"
internal const val TYPE_ANALYSIS_ONLY = "AnalysisOnly"
internal const val TYPE_GENERATION_READY = "GenerationReady"
internal const val TYPE_FULL_CYCLE = "FullCycle"

internal val compatibilityType: Type = object : TypeToken<CompatibilityRecord>() {}.type
