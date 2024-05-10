package com.diary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DiaryEntry::class], version = 1)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryEntryDao(): DiaryEntryDao

    companion object {
        private var instance: DiaryDatabase? = null

        @Synchronized
        fun getInstance(context: Context): DiaryDatabase {

            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    DiaryDatabase::class.java,
                    "diary_database"
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}
