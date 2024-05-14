package com.diary.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ReminderBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("TAG123", "onReceive: 1")
        val serviceIntent = Intent(context, ReminderService::class.java)
            .apply {
                putExtra("alarmId", intent.getIntExtra("alarmId", 1))
                putExtra("title", intent.getStringExtra("title"))
                putExtra("content", intent.getStringExtra("content"))
            }
        context.startForegroundService(serviceIntent)
    }
}
