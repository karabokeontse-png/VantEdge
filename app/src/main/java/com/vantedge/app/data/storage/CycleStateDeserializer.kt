package com.vantedge.app.data.storage

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.CycleState
import com.vantedge.app.data.model.DesignConfig
import java.lang.reflect.Type

private val compatibilityType = object : TypeToken<CompatibilityRecord>() {}.type

class CycleStateDeserializer : JsonDeserializer<CycleState> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): CycleState {
        val obj = json.asJsonObject
        val type = obj.get("type")?.asString
            ?: throw JsonParseException("Missing type field in CycleState JSON")

        return when (type) {
            "AnalysisOnly" -> CycleState.AnalysisOnly(
                compatibility = context.deserialize(obj.get("compatibility"), compatibilityType)
            )
            "GenerationReady" -> {
                val keywordsElement = obj.get("matchedKeywords")
                val keywords = if (keywordsElement != null && keywordsElement.isJsonArray) {
                    keywordsElement.asJsonArray.mapNotNull { element ->
                        if (element.isJsonPrimitive && element.asJsonPrimitive.isString) {
                            element.asString
                        } else null
                    }
                } else emptyList()
                CycleState.GenerationReady(
                    compatibility = context.deserialize(obj.get("compatibility"), compatibilityType),
                    matchedKeywords = keywords,
                    coverLetterBody = obj.get("coverLetterBody")?.asString ?: ""
                )
            }
            "FullCycle" -> CycleState.FullCycle(
                compatibility = context.deserialize(obj.get("compatibility"), compatibilityType),
                cvContent = obj.get("cvContent")?.asString ?: "",
                coverLetterContent = obj.get("coverLetterContent")?.asString ?: "",
                design = context.deserialize(obj.get("design"), DesignConfig::class.java)
            )
            else -> throw JsonParseException("Unknown CycleState type: $type")
        }
    }
}
