package com.diary.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DiaryEntryDao {
    @Insert
    fun insertDiaryEntry(diaryEntry: DiaryEntry?)

    @get:Query("SELECT * FROM diary_entries ORDER BY timeCreate DESC")
    val allDiaryEntries: List<DiaryEntry?>?

    @Query("SELECT * FROM diary_entries WHERE timeCreate = :date")
    fun getDiaryEntryByDate(date: String?): DiaryEntry?
    @Update
    fun updateDiaryEntry(diaryEntry: DiaryEntry?)

    @Delete
    fun deleteDiaryEntry(diaryEntry: DiaryEntry?)
}
