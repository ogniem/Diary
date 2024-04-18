@file:Suppress("DEPRECATION")

package com.diary

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.Resources
import android.util.Log
import android.view.View
import com.diary.database.DiaryEntry
import com.diary.model.Day
import com.diary.model.Language
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


object Common {

    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    fun getListLanguages(): List<Language> {
        return listOf(
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
        val resources = Resources.getSystem()
        val configuration = resources.configuration
        val locale = Locale(getListLanguages()[getLanguage(context)].key)
        Log.d("TAG123", "ngôn ngữ chọn: $locale")
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    fun saveLanguage(context: Context, position: Int) {
        val sharedPreferences = context.getSharedPreferences("app_preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("KEY_LANG", position)
        editor.apply()
    }

    private fun getLanguage(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences("app_preferences", MODE_PRIVATE)
        return sharedPreferences.getInt("KEY_LANG", 0)
    }

    fun Context.setNotFirstOpenApp() {
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
    fun convertTimeToString(hour: String, minute: String, isAM: Boolean): String {
        val period = if (isAM) "AM" else "PM"
        return String.format("%s:%s %s", hour, minute, period)
    }

    fun convertStringToTime(timeString: String): Triple<String, String, Boolean>? {
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
            var day = Day(calendar.getDay(), calendar.getMonth(), calendar.getYear())
            if (!list.contains(day)) {
                list.add(day)
            }
        }
        return list
    }
    fun convertAmPmTo24Hour(time: String): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date = sdf.parse(time)
        val cal = Calendar.getInstance()
        date?.let { cal.time = it }
        val hour24 = SimpleDateFormat("HH:mm", Locale.getDefault())
        return hour24.format(cal.time)
    }
}