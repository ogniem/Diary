@file:Suppress("DEPRECATION")

package com.diary

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.View
import com.diary.database.DiaryEntry
import com.diary.database.Schedule
import com.diary.model.Day
import com.diary.model.Language
import com.diary.service.ReminderBroadCastReceiver
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


object Common {

    val KEY_ALARM_DAY_OF_WEEK = "KEY_ALARM_DAY_OF_WEEK"
    val KEY_ENABLE_REMINDER = "KEY_ENABLE_REMINDER"
    val KEY_POSITION_DIARY = "KEY_POSITION_DIARY"

    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    fun getListLanguages(): List<Language> {
        return listOf(
            Language(R.string.vietnam, R.drawable.ic_vietnam, "vi"),
            Language(R.string.english, R.drawable.ic_england_flag, "en"),
            Language(R.string.hindi, R.drawable.ic_india_flag, "hi"),
            Language(R.string.spanish, R.drawable.ic_spanish_flag, "es"),
            Language(R.string.french, R.drawable.ic_french_flag, "fr"),
            Language(R.string.arabic, R.drawable.ic_arabic_flag, "ar"),
            Language(R.string.bengali, R.drawable.ic_bengal_flag, "bn"),
            Language(R.string.rusian, R.drawable.ic_russian_flag, "ru"),
            Language(R.string.potuguese, R.drawable.ic_portugal, "pt"),
            Language(R.string.indo, R.drawable.ic_indo_flag, "in"),
            Language(R.string.german, R.drawable.ic_german_flag, "de"),
            Language(R.string.itali, R.drawable.ic_itali_flag, "it"),
            Language(R.string.korea, R.drawable.ic_korean_flag, "ko"),
        )
    }

    fun View.invisible() {
        visibility = View.INVISIBLE
    }

    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.gone() {
        visibility = View.GONE
    }


    fun setLocale(context: Context) {
        val locale = Locale(getListLanguages()[getLanguage(context)].key)
        Locale.setDefault(locale)
        Log.d("TAG123", "setLocale: " + locale)
        val resources = context.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun saveLanguage(context: Context, position: Int) {
        val sharedPreferences = context.getSharedPreferences("app_preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("KEY_LANG", position)
        editor.apply()
    }

    fun getLanguage(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences("app_preferences", MODE_PRIVATE)
        return sharedPreferences.getInt("KEY_LANG", 0)
    }

    fun Context.setFirstOpenApp() {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("FIRST_OPEN", false)
        editor.apply()
    }

    fun Context.isFirstOpenApp(): Boolean {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        return sharedPreferences.getBoolean("FIRST_OPEN", true)
    }

    fun Context.setUserName(username: String) {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("USER_NAME", username)
        editor.apply()
    }

    fun Context.getUserName(): String {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        return sharedPreferences.getString("USER_NAME", "") ?: ""
    }

    fun Context.setEnableReminder(enable: Boolean) {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("ENABLE_REMINDER", enable)
        editor.apply()
    }

    fun Context.isEnableReminder(): Boolean {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        return sharedPreferences.getBoolean("ENABLE_REMINDER", true)
    }

    fun convertHourToString(hour: String, minute: String, isAM: Boolean): String {
        val period = if (isAM) "AM" else "PM"
        return String.format("%s:%s %s", hour, minute, period)
    }

    fun convertStringToHour(timeString: String): Triple<String, String, Boolean>? {
        val parts = timeString.split(":", " ")
        if (parts.size != 3) return null

        val hour = parts[0]
        val minute = parts[1]
        val isAM = parts[2].equals("AM", ignoreCase = true)

        return Triple(hour, minute, isAM)
    }

    fun Context.setDailyReminder(timeDailyReminder: String) {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("DAILY_REMINDER", timeDailyReminder)
        editor.apply()
    }

    fun Context.getDailyReminder(): String {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        return sharedPreferences.getString("DAILY_REMINDER", "") ?: ""
    }

    fun Context.setPasscode(passCode: String) {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("PASS_CODE", passCode)
        editor.apply()
    }

    fun Context.getPasscode(): String {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        return sharedPreferences.getString("PASS_CODE", "") ?: ""
    }

    fun Context.setSercurityQues(pos: Int) {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("SERCURITY_QUES", pos)
        editor.apply()
    }

    fun Context.getSercurityAns(): String {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        return sharedPreferences.getString("SERCURITY_ANS", "") ?: ""
    }

    fun Context.setSercurityAns(ans: String) {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("SERCURITY_ANS", ans)
        editor.apply()
    }

    fun Context.getSercurityQues(): Int {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        return sharedPreferences.getInt("SERCURITY_QUES", 0)
    }


    fun Context.setThemeHome(theme: Int) {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("THEME", theme)
        editor.apply()
    }

    fun Context.getThemeHome(): Int {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        return sharedPreferences.getInt("THEME", 1)
    }

    fun Context.setRepeat(repeat: Int) {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("REPEAT", repeat)
        editor.apply()
    }

    fun Context.getRepeat(): Int {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        return sharedPreferences.getInt("REPEAT", 2)
    }

    fun Context.setSort(isNewest: Boolean) {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("SORT", isNewest)
        editor.apply()
    }

    fun Context.getSort(): Boolean {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        return sharedPreferences.getBoolean("SORT", true)
    }

    fun Calendar.convertCalendarToString(): String {
        return simpleDateFormat.format(this.time)
    }

    fun String.convertStringToCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(simpleDateFormat.parse(this).time)
        return calendar
    }

    fun Calendar.getDay(): Int {
        return this.get(Calendar.DAY_OF_MONTH)
    }

    fun Calendar.getMonth(): Int {
        return (this.get(Calendar.MONTH) + 1)
    }

    fun Calendar.getYear(): Int {
        return this.get(Calendar.YEAR)
    }

    fun getListDayHasDiary(listDiary: List<DiaryEntry?>?): List<Day> {
        val list: MutableList<Day> = ArrayList()

        for (diary in listDiary!!) {
            val calendar = diary!!.timeCreate!!.convertStringToCalendar()
            val day = Day(calendar.getDay(), calendar.getMonth(), calendar.getYear())
            if (!list.contains(day)) {
                list.add(day)
            }
        }
        return list
    }

    fun getIconByEmotion(emotion: Int): Int {
        return when (emotion) {
            0 -> {
                R.drawable.ic_emotion_1
            }

            1 -> {
                R.drawable.ic_emotion_2
            }

            2 -> {
                R.drawable.ic_emotion_3
            }

            3 -> {
                R.drawable.ic_emotion_4
            }

            4 -> {
                R.drawable.ic_emotion_5
            }

            5 -> {
                R.drawable.ic_emotion_6
            }

            6 -> {
                R.drawable.ic_emotion_7
            }

            else -> {
                R.drawable.ic_emotion_5
            }
        }
    }

    fun Context.getTextByEmotion(emotion: Int): String {
        val textSource = when (emotion) {
            0 -> {
                R.string.emotion_1
            }

            1 -> {
                R.string.emotion_2
            }

            2 -> {
                R.string.emotion_3
            }

            3 -> {
                R.string.emotion_4
            }

            4 -> {
                R.string.emotion_5
            }

            5 -> {
                R.string.emotion_6
            }

            6 -> {
                R.string.emotion_7
            }

            else -> {
                R.string.emotion_5
            }
        }
        return getString(textSource)
    }

    fun findItemById(items: List<DiaryEntry>, id: Int): DiaryEntry? {
        return items.find { it.id == id }
    }

    fun Context.setReminderSchedule(schedule: Schedule) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (!schedule.isReminder) {
            // Hủy báo thức hiện tại nếu có
            val existingIntent = Intent(this, ReminderBroadCastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                schedule.id,
                existingIntent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )
            pendingIntent?.let {
                alarmManager.cancel(it)
                it.cancel()
            }
        } else {
            val calendar = Calendar.getInstance()
            when (schedule.dayOfWeek) {
                1 -> calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                2 -> calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
                3 -> calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
                4 -> calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
                5 -> calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
                6 -> calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
                7 -> calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            }

            convertStringToHour(schedule.time)?.let { (hour, minute, isAM) ->
                calendar.set(Calendar.HOUR_OF_DAY, hour.toInt() - 1)
                calendar.set(Calendar.MINUTE, minute.toInt())
                if (isAM) {
                    calendar.set(Calendar.AM_PM, Calendar.AM)
                } else {
                    calendar.set(Calendar.AM_PM, Calendar.PM)
                }
            }
            Log.d("TAG123", "setReminder: " + (calendar.timeInMillis - System.currentTimeMillis()))
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 7)
            }

            val intent = Intent(this, ReminderBroadCastReceiver::class.java).apply {
                putExtra("alarmId", schedule.id)
                putExtra("title", schedule.title)
                putExtra("content", schedule.content)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                schedule.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

//            val timeRepeat = 7 * 24 * 60 * 60 * 1000L
//
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                timeRepeat,
//                pendingIntent
//            )

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    }

    fun Context.setReminder() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (isEnableReminder()) {
            val existingIntent = Intent(this, ReminderBroadCastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                -1,
                existingIntent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )
            pendingIntent?.let {
                alarmManager.cancel(it)
                it.cancel()
            }
        } else {
            val calendar = Calendar.getInstance()

            convertStringToHour(getDailyReminder())?.let { (hour, minute, isAM) ->
                calendar.set(Calendar.HOUR_OF_DAY, hour.toInt() - 1)
                calendar.set(Calendar.MINUTE, minute.toInt())
                if (isAM) {
                    calendar.set(Calendar.AM_PM, Calendar.AM)
                } else {
                    calendar.set(Calendar.AM_PM, Calendar.PM)
                }
            }

            Log.d(
                "TAG123",
                "setReminderád gsadf: " + (calendar.timeInMillis - System.currentTimeMillis())
            )
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                if (getRepeat() <= 2) {
                    calendar.add(Calendar.DAY_OF_YEAR, 1)
                } else if (getRepeat() == 3) {
                    calendar.add(Calendar.DAY_OF_YEAR, 7)
                } else if (getRepeat() == 4) {
                    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1)
                } else {
                    calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1)
                }
            }

            val intent = Intent(this, ReminderBroadCastReceiver::class.java).apply {
                putExtra("alarmId", -1)
                putExtra("title", getString(R.string.daily_reminder))
                putExtra("content", getString(R.string.daily_reminder_content))
            }
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                -1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    }
}