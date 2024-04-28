package com.diary.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ScheduleDao {
    @Insert
    fun insertSchedule(schedule: Schedule)

    @Query("SELECT * FROM schedule")
    fun getAllSchedules(): List<Schedule>

    @Query("SELECT * FROM schedule WHERE dayOfWeek = :dayOfWeek")
    fun getSchedulesByDayOfWeek(dayOfWeek: Int): List<Schedule>

    @Update
    fun updateSchedule(schedule: Schedule)

    @Delete
    fun deleteSchedule(schedule: Schedule)
}
