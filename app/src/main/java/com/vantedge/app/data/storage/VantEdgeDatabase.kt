package com.vantedge.app.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vantedge.app.data.model.AiRawResponseArtifact

@Database(
    entities = [CycleEntity::class, OnboardingDraftEntity::class, AiRawResponseArtifact::class],
    version = 3,
    exportSchema = false
)
abstract class VantEdgeDatabase : RoomDatabase() {

    abstract fun cycleDao(): CycleDao
    abstract fun onboardingDraftDao(): OnboardingDraftDao
    abstract fun aiRawResponseArtifactDao(): AiRawResponseArtifactDao

    companion object {
        @Volatile
        private var INSTANCE: VantEdgeDatabase? = null

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ai_raw_response_artifact` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `correlationId` TEXT NOT NULL,
                        `attemptNumber` INTEGER NOT NULL,
                        `model` TEXT NOT NULL,
                        `provider` TEXT NOT NULL,
                        `timestamp` INTEGER NOT NULL,
                        `requestStartMs` INTEGER NOT NULL,
                        `requestDurationMs` INTEGER NOT NULL,
                        `httpCode` INTEGER NOT NULL,
                        `finishReason` TEXT NOT NULL,
                        `promptHash` TEXT NOT NULL,
                        `bodyLength` INTEGER NOT NULL,
                        `rawResponse` TEXT NOT NULL,
                        `failureType` TEXT NOT NULL,
                        `failureStage` TEXT NOT NULL
                    )
                    """
                )
                db.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_ai_raw_response_artifact_correlationId` ON `ai_raw_response_artifact` (`correlationId`)"
                )
            }
        }

        fun getInstance(context: Context): VantEdgeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VantEdgeDatabase::class.java,
                    "vantedge_database"
                )
                .addMigrations(MIGRATION_2_3)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
