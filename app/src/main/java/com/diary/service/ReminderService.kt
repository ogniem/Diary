package com.diary.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.diary.R
import com.diary.activity.MainActivity


class ReminderService : Service() {
    override fun onCreate() {
        super.onCreate()
        Log.d("TAG123", "onReceive: 2")
        createNotificationChannel()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG123", "onReceive: 3")
        val alarmId = intent?.getIntExtra("alarmId", -1) ?: -1
        val label = intent?.getStringExtra("title") ?: getString(R.string.reminder)
        val content = intent?.getStringExtra("content") ?: getString(R.string.schedule)
        if (alarmId != -1) {
            val notification = createNotification(label, content)
            startForeground(1, notification)
            showAlarmScreen(alarmId, label)
        } else {
            stopSelf()
        }
        return START_NOT_STICKY
    }

    private fun createNotification(label: String, content: String): Notification {
        Log.d("TAG123", "onReceive: 4")
        val channelId = "alarm_channel"
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        startActivity(intent)
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle(label)
            .setContentText(content)
            .setSmallIcon(R.drawable.logo_app)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNotificationChannel() {
        Log.d("TAG123", "onReceive: 5")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "alarm_channel"
            val channelName = "Alarm Notification"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for alarm notifications"
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showAlarmScreen(alarmId: Int, label: String) {
        Log.d("TAG123", "onReceive: 6")
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("alarmId", alarmId)
            putExtra("title", label)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}