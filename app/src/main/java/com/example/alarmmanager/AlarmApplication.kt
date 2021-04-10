package com.example.alarmmanager

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.alarmmanager.utils.AppConstants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlarmApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannnel()
    }

    fun createNotificationChannnel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                    AppConstants.CHANNEL_ID,
                    "Alarm Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager: NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

}