package com.vantedge.app.notifications

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.vantedge.app.R

// ── Worker ──────────────────────────────────────────────────────────────────

class DeadlineNotificationWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val jobTitle = inputData.getString("jobTitle") ?: "Job"
        val company  = inputData.getString("company")  ?: ""
        val label    = inputData.getString("label")    ?: "Deadline reminder"

        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Application Deadlines",
                NotificationManager.IMPORTANCE_HIGH
            ).apply { description = "Reminders for job application deadlines" }
            manager.createNotificationChannel(channel)
        }

        val notificationId = System.currentTimeMillis().toInt()
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("$jobTitle${if (company.isNotBlank()) " at $company" else ""}")
            .setContentText(label)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        manager.notify(notificationId, notification)
        return Result.success()
    }

    companion object {
        const val CHANNEL_ID = "vantedge_deadlines"
    }
}

// ── Scheduler ────────────────────────────────────────────────────────────────

object DeadlineNotificationScheduler {

    /**
     * Schedules 3 notifications: 3 days before, 1 day before, same day at 9 AM.
     * Tags each work request with the record ID so they can be cancelled together.
     */
    fun schedule(
        context: Context,
        recordId: Long,
        jobTitle: String,
        company: String,
        deadlineMs: Long
    ) {
        val workManager = WorkManager.getInstance(context)
        val now = System.currentTimeMillis()
        val tag = "deadline_$recordId"

        val reminders = listOf(
            Triple(deadlineMs - 3 * 24 * 60 * 60 * 1000L, "Application closes in 3 days", "3-day"),
            Triple(deadlineMs - 1 * 24 * 60 * 60 * 1000L, "Application closes tomorrow!", "1-day"),
            Triple(sameDayAt9AM(deadlineMs),               "Application closes today!",    "same-day")
        )

        reminders.forEach { (triggerMs, message, suffix) ->
            val delayMs = triggerMs - now
            if (delayMs <= 0) return@forEach  // skip if time has already passed

            val data = Data.Builder()
                .putString("jobTitle", jobTitle)
                .putString("company",  company)
                .putString("label",    message)
                .build()

            val request = OneTimeWorkRequestBuilder<DeadlineNotificationWorker>()
                .setInitialDelay(delayMs, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .addTag(tag)
                .addTag("${tag}_$suffix")
                .build()

            workManager.enqueue(request)
        }
    }

    fun cancel(context: Context, recordId: Long) {
        WorkManager.getInstance(context).cancelAllWorkByTag("deadline_$recordId")
    }

    /** Returns the deadline date at 09:00 local time */
    private fun sameDayAt9AM(deadlineMs: Long): Long {
        val cal = java.util.Calendar.getInstance().apply {
            timeInMillis = deadlineMs
            set(java.util.Calendar.HOUR_OF_DAY, 9)
            set(java.util.Calendar.MINUTE, 0)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }
        return cal.timeInMillis
    }
}
