package com.vantedge.app.data.storage

import androidx.room.*

@Dao
interface OnboardingDraftDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: OnboardingDraftEntity)

    @Query("SELECT * FROM onboarding_draft WHERE id = :id")
    suspend fun getDraft(id: String): OnboardingDraftEntity?

    @Query("DELETE FROM onboarding_draft WHERE id = :id")
    suspend fun deleteDraft(id: String)

    /**
     * SAFETY CLEANUP: removes any orphaned or corrupted drafts
     * (prevents "ghost state" after crashes / killed coroutines)
     */
    @Query("DELETE FROM onboarding_draft")
    suspend fun clearAll()
}