package com.diary.database

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.diary.Common
import com.diary.Common.convertStringToCalendar
import com.diary.Common.convertStringToHour
import com.diary.service.ReminderBroadCastReceiver
import java.text.SimpleDateFormat
import java.util.Locale

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

    fun alarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderBroadCastReceiver::class.java)
            .putExtra(Common.KEY_ALARM_DAY_OF_WEEK, dayOfWeek)
            .putExtra(Common.KEY_ENABLE_REMINDER, isReminder)

        val pendingIntent = PendingIntent.getBroadcast(context, id,intent, PendingIntent.FLAG_IMMUTABLE )
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.time = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse(time)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

    }
}
