package com.diary

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        startService(Intent(this, SocketService::class.java))
    }
}