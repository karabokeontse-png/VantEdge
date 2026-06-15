package com.vantedge.app.data.storage

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.CycleState
import com.vantedge.app.data.model.DesignConfig
import java.lang.reflect.Type

private const val TYPE_KEY = "type"
private const val TYPE_ANALYSIS_ONLY = "AnalysisOnly"
private const val TYPE_GENERATION_READY = "GenerationReady"
private const val TYPE_FULL_CYCLE = "FullCycle"

private val compatibilityType = object : TypeToken<CompatibilityRecord>() {}.type

class CycleStateSerializer : JsonSerializer<CycleState> {
    override fun serialize(
        src: CycleState,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val obj = JsonObject()
        when (src) {
            is CycleState.AnalysisOnly -> {
                obj.addProperty(TYPE_KEY, TYPE_ANALYSIS_ONLY)
                obj.add("compatibility", context.serialize(src.compatibility, compatibilityType))
            }
            is CycleState.GenerationReady -> {
                obj.addProperty(TYPE_KEY, TYPE_GENERATION_READY)
                obj.add("compatibility", context.serialize(src.compatibility, compatibilityType))
                obj.add("matchedKeywords", context.serialize(src.matchedKeywords))
                obj.addProperty("coverLetterBody", src.coverLetterBody)
            }
            is CycleState.FullCycle -> {
                obj.addProperty(TYPE_KEY, TYPE_FULL_CYCLE)
                obj.add("compatibility", context.serialize(src.compatibility, compatibilityType))
                obj.addProperty("cvContent", src.cvContent)
                obj.addProperty("coverLetterContent", src.coverLetterContent)
                obj.add("design", context.serialize(src.design))
            }
        }
        return obj
    }
}

class CycleStateDeserializer : JsonDeserializer<CycleState> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): CycleState {
        val obj = json.asJsonObject
        val type = obj.get(TYPE_KEY)?.asString
            ?: throw JsonParseException("Missing type field in CycleState JSON")

        return when (type) {
            TYPE_ANALYSIS_ONLY -> CycleState.AnalysisOnly(
                compatibility = context.deserialize(obj.get("compatibility"), compatibilityType)
            )
            TYPE_GENERATION_READY -> {
                val keywordsElement = obj.get("matchedKeywords")
                val keywords = if (keywordsElement != null && keywordsElement.isJsonArray) {
                    keywordsElement.asJsonArray.map { it.asString }
                } else emptyList()
                CycleState.GenerationReady(
                    compatibility = context.deserialize(obj.get("compatibility"), compatibilityType),
                    matchedKeywords = keywords,
                    coverLetterBody = obj.get("coverLetterBody")?.asString ?: ""
                )
            }
            TYPE_FULL_CYCLE -> CycleState.FullCycle(
                compatibility = context.deserialize(obj.get("compatibility"), compatibilityType),
                cvContent = obj.get("cvContent")?.asString ?: "",
                coverLetterContent = obj.get("coverLetterContent")?.asString ?: "",
                design = context.deserialize(obj.get("design"), DesignConfig::class.java)
            )
            else -> throw JsonParseException("Unknown CycleState type: $type")
        }
    }
}