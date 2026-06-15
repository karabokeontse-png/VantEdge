package com.vantedge.app.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CycleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCycle(cycle: CycleEntity)

    @Query("SELECT * FROM cycles ORDER BY createdAt DESC")
    fun getAllCycles(): Flow<List<CycleEntity>>

    @Query("SELECT * FROM cycles WHERE id = :id LIMIT 1")
    suspend fun getCycleById(id: String): CycleEntity?

    @Query("DELETE FROM cycles WHERE id = :id")
    suspend fun deleteCycleById(id: String)
}