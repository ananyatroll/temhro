package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        UserProgress::class,
        StudySubject::class,
        SubjectNote::class,
        ExamQuestion::class,
        Flashcard::class,
        AnalyzedVideo::class,
        StudentCalendarEvent::class,
        StudyTask::class,
        StudyGoal::class,
        GradeCourse::class,
        GradeAssessment::class,
        StudySessionLog::class,
        ScannedDocument::class,
        Entitlement::class,
        PurchaseRequest::class
    ],
    version = 16,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun educationDao(): EducationDao
    abstract fun studentToolsDao(): StudentToolsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_14_15 = object : Migration(14, 15) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE subject_notes ADD COLUMN releaseDate TEXT NOT NULL DEFAULT ''")
            }
        }

        private val MIGRATION_15_16 = object : Migration(15, 16) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `entitlements` (
                        `productId` TEXT NOT NULL,
                        `grantedAtMillis` INTEGER NOT NULL,
                        `purchaseReference` TEXT NOT NULL,
                        PRIMARY KEY(`productId`)
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `purchase_requests` (
                        `reference` TEXT NOT NULL,
                        `productId` TEXT NOT NULL,
                        `category` TEXT NOT NULL,
                        `stream` TEXT,
                        `academicYear` INTEGER,
                        `department` TEXT,
                        `plan` TEXT NOT NULL,
                        `amount` INTEGER NOT NULL,
                        `currency` TEXT NOT NULL,
                        `language` TEXT NOT NULL,
                        `status` TEXT NOT NULL,
                        `payerName` TEXT NOT NULL,
                        `transactionId` TEXT NOT NULL,
                        `createdAtMillis` INTEGER NOT NULL,
                        PRIMARY KEY(`reference`)
                    )
                    """.trimIndent()
                )
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smart_study_database"
                )
                .addMigrations(MIGRATION_14_15, MIGRATION_15_16)
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
