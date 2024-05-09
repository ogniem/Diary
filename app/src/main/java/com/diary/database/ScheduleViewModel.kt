package com.diary.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ScheduleViewModel(private val scheduleRepository: ScheduleRepository) : ViewModel() {

    fun getAllSchedules(): LiveData<List<Schedule>> {
        return scheduleRepository.getAllSchedules()
    }

    fun getSchedulesByDayOfWeek(dayOfWeek: Int): LiveData<List<Schedule>> {
        return scheduleRepository.getSchedulesByDayOfWeek(dayOfWeek)
    }

    fun insertSchedule(schedule: Schedule) {
        scheduleRepository.insertSchedule(schedule)
    }

    fun updateSchedule(schedule: Schedule) {
        scheduleRepository.updateSchedule(schedule)
    }

    fun deleteSchedule(schedule: Schedule) {
        scheduleRepository.deleteSchedule(schedule)
    }
}

