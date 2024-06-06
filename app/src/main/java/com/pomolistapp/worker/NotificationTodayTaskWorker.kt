package com.pomolistapp.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.pomolist.feature_task.presentation.timer.components.NotificationService

class NotificationTodayTaskWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val timerNotification = NotificationService(applicationContext)

        timerNotification.showNotificationTodayTask()

        return Result.success()
    }

}