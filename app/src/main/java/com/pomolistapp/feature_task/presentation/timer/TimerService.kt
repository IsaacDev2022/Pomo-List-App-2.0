package com.pomolistapp.feature_task.presentation.timer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.pomolist.feature_task.presentation.timer.components.NOTIFICATION_CHANNEL_ID
import com.pomolistapp.R
import com.pomolistapp.feature_task.presentation.timer.screens.formatDuration

class TimerService : Service() {

    private var context: Context? = null
    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "100"
    private var isDestroyed = false

    private lateinit var timer: CountDownTimer
    var value: String = ""

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        startForeground(NOTIFICATION_ID, showNotification(""))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startTimerForeground()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startTimerForeground() {
        Toast.makeText(this, "Iniciando Service", Toast.LENGTH_SHORT).show()
        timer = object : CountDownTimer(120000, 1000) {
            override fun onTick(remaning: Long) {
                value = (remaning / 1000).formatDuration()
                updateNotification(value)
            }

            override fun onFinish() {
                value = "Done"
            }
        }.start()
    }

    private fun stopTimerForeground() {
        timer.cancel()
    }

    private fun updateNotification(data: String) {
        val notification: Notification = showNotification(data)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as
                NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun showNotification(content: String): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID, "Notificação",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setContentTitle("Pomodoro")
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        isDestroyed = true
        stopTimerForeground()
    }
}