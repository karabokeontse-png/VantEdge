package com.vantedge.app.notifications

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object DeadlineNotificationScheduler {
    private const val THREE_DAYS_MS = 259200000L
    private const val ONE_DAY_MS = 86400000L

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

        listOf(
            Triple(deadlineMs - THREE_DAYS_MS, "Application closes in 3 days", "3-day"),
            Triple(deadlineMs - ONE_DAY_MS, "Application closes tomorrow!", "1-day"),
            Triple(sameDayAt9AM(deadlineMs), "Application closes today!", "same-day")
        ).forEach { (triggerMs, message, suffix) ->
            val delayMs = triggerMs - now
            if (delayMs > 0) {
                val data = Data.Builder()
                    .putString("jobTitle", jobTitle)
                    .putString("company", company)
                    .putString("label", message)
                    .build()
                val request = OneTimeWorkRequest.Builder(DeadlineNotificationWorker::class.java)
                    .setInitialDelay(delayMs, TimeUnit.MILLISECONDS)
                    .setInputData(data)
                    .addTag(tag)
                    .addTag("${tag}_$suffix")
                    .build()
                workManager.enqueue(request)
            }
        }
    }

    fun cancel(context: Context, recordId: Long) {
        WorkManager.getInstance(context).cancelAllWorkByTag("deadline_$recordId")
    }

    private fun sameDayAt9AM(deadlineMs: Long): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = deadlineMs
        cal.set(Calendar.HOUR_OF_DAY, 9)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }
}
