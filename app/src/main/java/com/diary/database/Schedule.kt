package com.diary.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var dayOfWeek: Int,
    var isReminder: Boolean,
    var time: String,
    var title: String,
    var content: String
) {
    constructor() : this(0, 0, false, "", "", "")
}
