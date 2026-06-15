package com.vantedge.app.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CycleEntity::class, OnboardingDraftEntity::class],
    version = 2,
    exportSchema = false
)
abstract class VantEdgeDatabase : RoomDatabase() {

    abstract fun cycleDao(): CycleDao
    abstract fun onboardingDraftDao(): OnboardingDraftDao

    companion object {
        @Volatile
        private var INSTANCE: VantEdgeDatabase? = null

        fun getInstance(context: Context): VantEdgeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VantEdgeDatabase::class.java,
                    "vantedge_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}