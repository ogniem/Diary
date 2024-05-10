package com.diary.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class DiaryViewModel (private val diaryRepository: DiaryRepository) : ViewModel() {

    fun getAllDiary(): LiveData<List<DiaryEntry>> {
        return diaryRepository.allDiaryEntries
    }

    fun insertDiary(diaryEntry: DiaryEntry) {
        diaryRepository.insertDiaryEntry(diaryEntry)
    }

    fun updateDiary(diaryEntry: DiaryEntry) {
        diaryRepository.updateDiaryEntry(diaryEntry)
    }

    fun deleteDiary(diaryEntry: DiaryEntry) {
        diaryRepository.deleteDiaryEntry(diaryEntry)
    }
}