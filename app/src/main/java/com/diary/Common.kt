@file:Suppress("DEPRECATION")

package com.diary

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.Resources
import android.util.Log
import android.view.View
import com.diary.model.Language
import java.util.Locale

object Common {
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

    fun Context.setDailyReminder(timeDailyReminder: String) {
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("DAILY_REMINDER", timeDailyReminder)
        editor.apply()
    }

    fun Context.setDailyReminder(): String {
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
}