package com.diary.database

import androidx.lifecycle.LiveData

class ScheduleRepository(private val scheduleDao: ScheduleDao) {

    fun getAllSchedules(): LiveData<List<Schedule>> {
        return scheduleDao.getAllSchedules()
    }

    fun getSchedulesByDayOfWeek(dayOfWeek: Int): LiveData<List<Schedule>> {
        return scheduleDao.getSchedulesByDayOfWeek(dayOfWeek)
    }

    fun insertSchedule(schedule: Schedule) {
        scheduleDao.insertSchedule(schedule)
    }

    fun updateSchedule(schedule: Schedule) {
        scheduleDao.updateSchedule(schedule)
    }

    fun deleteSchedule(schedule: Schedule) {
        scheduleDao.deleteSchedule(schedule)
    }
}
