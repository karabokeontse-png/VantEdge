package com.vantedge.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.vantedge.app.R

class DeadlineNotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    companion object {
        const val CHANNEL_ID = "vantedge_deadlines"
    }

    override fun doWork(): Result {
        val jobTitle = inputData.getString("jobTitle") ?: "Job"
        val company = inputData.getString("company") ?: ""
        val label = inputData.getString("label") ?: "Deadline reminder"

        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Application Deadlines",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Reminders for job application deadlines"
            manager.createNotificationChannel(channel)
        }

        val notificationId = System.currentTimeMillis().toInt()
        val title = jobTitle + if (!company.isBlank()) " at $company" else ""

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(label)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        manager.notify(notificationId, notification)
        return Result.success()
    }
}
