package com.vantedge.app.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.vantedge.app.data.model.AiRawResponseArtifact

@Dao
interface AiRawResponseArtifactDao {

    @Insert
    suspend fun insert(artifact: AiRawResponseArtifact): Long

    @Query(
        """
        DELETE FROM ai_raw_response_artifact 
        WHERE id NOT IN (
            SELECT id FROM ai_raw_response_artifact 
            ORDER BY id DESC LIMIT :limit
        )
        """
    )
    suspend fun deleteOldestBeyondLimit(limit: Int)

    @Transaction
    suspend fun insertWithRetention(artifact: AiRawResponseArtifact, limit: Int = 200) {
        insert(artifact)
        deleteOldestBeyondLimit(limit)
    }

    @Query("SELECT * FROM ai_raw_response_artifact ORDER BY id DESC")
    suspend fun getAll(): List<AiRawResponseArtifact>
}
