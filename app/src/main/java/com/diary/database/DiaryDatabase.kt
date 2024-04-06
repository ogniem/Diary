package com.diary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DiaryEntry::class], version = 4)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryEntryDao(): DiaryEntryDao?

    companion object {
        private var instance: DiaryDatabase? = null

        @Synchronized
        fun getInstance(context: Context): DiaryDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DiaryDatabase::class.java, "diary_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}
