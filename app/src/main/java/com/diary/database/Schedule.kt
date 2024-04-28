package com.diary.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dayOfWeek: Int,
    val isReminder: Boolean,
    val time: String,
    val title: String,
    val content: String
)
