package com.pomolist.feature_task.presentation.timer.components

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import com.pomolistapp.R
import com.pomolistapp.feature_task.presentation.MainActivity
import kotlin.random.Random

const val NOTIFICATION_CHANNEL_ID = "channel"
const val NOTIFICATION_CHANNEL_NAME = "channel name"
const val NOTIFICATION_ID = 100
const val REQUEST_CODE = 200

class NotificationService(
    private val context: Context
) {

    private val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    private val myIntent = Intent(context, MainActivity::class.java)
    private val pendingIntent = PendingIntent.getActivity(
        context,
        REQUEST_CODE,
        myIntent,
        PendingIntent.FLAG_IMMUTABLE
    )

    fun showNotificationEndPomodoro() {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.pomodoro)
            .setContentTitle("Fim!!")
            .setContentText("Pomodoro finalizado!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun showNotificationEndBreak() {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.pomodoro)
            .setContentTitle("Fim!!")
            .setContentText("Intervalo finalizado!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun showNotificationNowTask() {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.pomodoro)
            .setContentTitle("Alerta")
            .setContentText("Voce tem tarefas agendadas para agora!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun showNotificationExpireSoonTask() {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.pomodoro)
            .setContentTitle("Alerta")
            .setContentText("Voce tem uma tarefa que expira em uma hora!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun showNotificationTodayTask() {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.pomodoro)
            .setContentTitle("Alerta")
            .setContentText("Voce tem uma tarefas agendadas para hoje!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}