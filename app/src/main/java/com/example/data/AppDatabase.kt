package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

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
        ScannedDocument::class
    ],
    version = 13,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun educationDao(): EducationDao
    abstract fun studentToolsDao(): StudentToolsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smart_study_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
