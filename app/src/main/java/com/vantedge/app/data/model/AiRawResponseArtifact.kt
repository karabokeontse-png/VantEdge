package com.vantedge.app.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ai_raw_response_artifact",
    indices = [Index(value = ["correlationId"])]
)
data class AiRawResponseArtifact(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val correlationId: String,
    val attemptNumber: Int,
    val model: String,
    val provider: String,
    val timestamp: Long,
    val requestStartMs: Long,
    val requestDurationMs: Long,
    val httpCode: Int,
    val finishReason: String,
    val promptHash: String,
    val bodyLength: Int,
    val rawResponse: String,
    val failureType: String,
    val failureStage: String
)
