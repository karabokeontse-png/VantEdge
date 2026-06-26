package com.vantedge.app.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AppLogger {
    private const val MAX_SIZE = 500
    private val buffer = ArrayDeque<String>(MAX_SIZE)
    private val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    fun d(tag: String, message: String) {
        Log.d(tag, message)
        add("D", tag, message)
    }

    fun i(tag: String, message: String) {
        Log.i(tag, message)
        add("I", tag, message)
    }

    fun w(tag: String, message: String) {
        Log.w(tag, message)
        add("W", tag, message)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        Log.e(tag, message, throwable)
        val extra = throwable?.let { "\n${it.stackTraceToString()}" }.orEmpty()
        add("E", tag, message + extra)
    }

    @Synchronized
    private fun add(level: String, tag: String, message: String) {
        val time = dateFormat.format(Date())
        val line = "$time $level/$tag: $message"
        if (buffer.size >= MAX_SIZE) {
            buffer.removeFirst()
        }
        buffer.addLast(line)
    }

    @Synchronized
    fun getLogs(): List<String> = buffer.toList()

    @Synchronized
    fun clear() {
        buffer.clear()
    }
}
