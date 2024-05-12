package com.diary.database

import androidx.lifecycle.LiveData

class DiaryRepository(private val diaryEntryDao: DiaryEntryDao) {

    val allDiaryEntries: LiveData<List<DiaryEntry>> = diaryEntryDao.allDiaryEntries

    fun insertDiaryEntry(diaryEntry: DiaryEntry) {
        diaryEntryDao.insertDiaryEntry(diaryEntry)
    }

    fun getDiaryEntryByDate(date: String): LiveData<DiaryEntry> {
        return diaryEntryDao.getDiaryEntryByDate(date)
    }

    fun updateDiaryEntry(diaryEntry: DiaryEntry) {
        diaryEntryDao.updateDiaryEntry(diaryEntry)
    }

    fun deleteDiaryEntry(diaryEntry: DiaryEntry) {
        diaryEntryDao.deleteDiaryEntry(diaryEntry)
    }
    fun getLiveDiary(id: Int): LiveData<DiaryEntry>? {
        return diaryEntryDao.getLiveDiaryEntryById(id)
    }

}
