package com.diary.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DiaryEntryDao {
    @Insert
    fun insertDiaryEntry(diaryEntry: DiaryEntry)

    @get:Query("SELECT * FROM diary_entries ORDER BY timeCreate DESC")
    val allDiaryEntries: LiveData<List<DiaryEntry>>

    @Query("SELECT * FROM diary_entries WHERE timeCreate = :date")
    fun getDiaryEntryByDate(date: String): LiveData<DiaryEntry>
    @Update
    fun updateDiaryEntry(diaryEntry: DiaryEntry?)

    @Delete
    fun deleteDiaryEntry(diaryEntry: DiaryEntry?)

    @Query("SELECT * FROM diary_entries WHERE id = :id")
    fun getDiaryEntryById(id: Int): DiaryEntry

}
