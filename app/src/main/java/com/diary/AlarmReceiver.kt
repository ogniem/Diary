package com.diary

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Xử lý sự kiện của báo thức ở đây
        // Ví dụ: Hiển thị thông báo, gửi thông báo cho người dùng, hoặc thực hiện hành động khác
        context?.let {
            val notificationManager = it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notification = NotificationCompat.Builder(it, "channelId")
                .setContentTitle("Báo thức")
                .setContentText("Đã đến giờ!")
                .setSmallIcon(R.drawable.logo_app)
                .build()

            notificationManager.notify(0, notification)
        }
    }
}
