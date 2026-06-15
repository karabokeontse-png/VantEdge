package com.vantedge.app.data.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "onboarding_draft")
data class OnboardingDraftEntity(
    @PrimaryKey val id: String = "active_draft",
    val draftJson: String
)