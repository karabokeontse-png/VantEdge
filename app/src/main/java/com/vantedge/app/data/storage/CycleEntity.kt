package com.vantedge.app.data.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cycles")
data class CycleEntity(
    @PrimaryKey val id: String,
    val jobTitle: String,
    val company: String,
    val createdAt: Long,
    val isCommitted: Boolean,
    val isVisibleInHistory: Boolean,
    val version: Int?,
    val cycleJson: String // full GenerationCycle serialized as JSON
)