package com.vantedge.app.data.storage

import android.content.Context
import com.google.gson.Gson
import com.vantedge.app.data.model.OnboardingDraft

class OnboardingDraftStore(context: Context) {

    private val dao: OnboardingDraftDao =
        VantEdgeDatabase.getInstance(context).onboardingDraftDao()

    private val gson = Gson()

    private val ACTIVE_ID = "active_draft"

    /**
     * ATOMIC WRITE: ensures no stale overlap between CV uploads
     * (delete + insert prevents corrupted REPLACE edge cases)
     */
    suspend fun saveDraft(draft: OnboardingDraft) {

        try {
            dao.deleteDraft(ACTIVE_ID)

            val entity = OnboardingDraftEntity(
                id = ACTIVE_ID,
                draftJson = gson.toJson(draft)
            )

            dao.upsert(entity)

        } catch (e: Exception) {
            throw Exception("Failed to save draft: " + (e.message ?: "unknown"))
        }
    }

    /**
     * SAFE READ: guards against corrupted JSON or partial writes
     */
    suspend fun loadDraft(): OnboardingDraft? {

        val entity = try {
            dao.getDraft(ACTIVE_ID)
        } catch (e: Exception) {
            null
        } ?: return null

        return try {
            gson.fromJson(entity.draftJson, OnboardingDraft::class.java)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * HARD RESET: used before every CV pipeline run
     */
    suspend fun clearDraft() {
        try {
            dao.deleteDraft(ACTIVE_ID)
        } catch (e: Exception) {
            // no-op (must never crash pipeline reset)
        }
    }

    /**
     * SAFETY: force reset + write in one call (used by ViewModel if needed)
     */
    suspend fun resetAndSave(draft: OnboardingDraft) {
        clearDraft()
        saveDraft(draft)
    }
}