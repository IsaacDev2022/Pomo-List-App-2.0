package com.pomolistapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.pomolist.feature_task.presentation.timer.components.NOTIFICATION_CHANNEL_ID
import com.pomolist.feature_task.presentation.timer.components.NOTIFICATION_CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PomoListApp: Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

//        val notificationManager = applicationContext.getSystemService(Application.NOTIFICATION_SERVICE) as NotificationManager
//
//        val notificationChannel = NotificationChannel(
//            NOTIFICATION_CHANNEL_ID,
//            NOTIFICATION_CHANNEL_NAME,
//            NotificationManager.IMPORTANCE_HIGH
//        )

//        notificationManager.createNotificationChannel(notificationChannel)
    }
}