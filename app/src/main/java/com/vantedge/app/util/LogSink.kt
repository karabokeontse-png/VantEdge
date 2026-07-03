package com.vantedge.app.util

import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object LogSink {
    private const val TAG = "LogSink"
    private const val CAP = 2L * 1024 * 1024
    private const val MAX_ARCHIVES = 5
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)
    private val sessionId: String = UUID.randomUUID().toString().take(8)
    private val lock = ReentrantLock()

    fun write(
        level: String,
        stage: String,
        correlationId: String?,
        message: String
    ) {
        val file = try {
            LogFileProvider.file()
        } catch (e: Exception) {
            Log.e(TAG, "LogFileProvider not bound: ${e.message}")
            return
        }
        val timestamp = dateFormat.format(Date())
        val cid = correlationId ?: sessionId
        val line = "$timestamp\t$level\t$stage\t$cid\t$message"
        val bytesToWrite = (line + System.lineSeparator())
            .toByteArray(Charsets.UTF_8)
            .size
        lock.withLock {
            rotateIfNeeded(file, bytesToWrite)
            try {
                file.appendText(line + System.lineSeparator(), Charsets.UTF_8)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to write trace: ${e.message}")
            }
        }
    }

    private fun rotateIfNeeded(activeFile: File, bytesToWrite: Int) {
        if (activeFile.length() + bytesToWrite <= CAP) return

        val activePath = activeFile.absolutePath

        try {
            // ── Phase 1: archive cascade (reverse order) ──

            // delete .5
            val f5 = File("$activePath.5")
            if (f5.exists() && !f5.delete()) {
                if (File(activePath).exists()) {
                    Log.w(TAG, "ROTATION_FAILED: delete .5")
                    return
                }
                phase2(activePath)
                return
            }
            // rename .4 -> .5
            val f4 = File("$activePath.4")
            if (f4.exists() && !f4.renameTo(File("$activePath.5"))) {
                if (File(activePath).exists()) {
                    Log.w(TAG, "ROTATION_FAILED: rename .4 -> .5")
                    return
                }
                phase2(activePath)
                return
            }
            // rename .3 -> .4
            val f3 = File("$activePath.3")
            if (f3.exists() && !f3.renameTo(File("$activePath.4"))) {
                if (File(activePath).exists()) {
                    Log.w(TAG, "ROTATION_FAILED: rename .3 -> .4")
                    return
                }
                phase2(activePath)
                return
            }
            // rename .2 -> .3
            val f2 = File("$activePath.2")
            if (f2.exists() && !f2.renameTo(File("$activePath.3"))) {
                if (File(activePath).exists()) {
                    Log.w(TAG, "ROTATION_FAILED: rename .2 -> .3")
                    return
                }
                phase2(activePath)
                return
            }
            // rename .1 -> .2
            val f1 = File("$activePath.1")
            if (f1.exists() && !f1.renameTo(File("$activePath.2"))) {
                if (File(activePath).exists()) {
                    Log.w(TAG, "ROTATION_FAILED: rename .1 -> .2")
                    return
                }
                phase2(activePath)
                return
            }
            // rename active -> .1
            val activeNow = File(activePath)
            if (activeNow.exists() && !activeNow.renameTo(File("$activePath.1"))) {
                Log.w(TAG, "ROTATION_FAILED: rename active -> .1")
                return
            }

            // ── Phase 2: ensure active file exists ──
            phase2(activePath)
        } catch (e: Exception) {
            Log.w(TAG, "ROTATION_FAILED: ${e.message}")
        }
    }

    private fun phase2(activePath: String) {
        val active = File(activePath)
        if (!active.exists() && !active.createNewFile()) {
            Log.w(TAG, "ROTATION_FAILED: CREATE_ACTIVE")
            Log.e(TAG, "WRITE_FAILURE: active log unavailable")
            return
        }
        Log.i(TAG, "Rotated vantedge_trace.log -> .1, archives=$MAX_ARCHIVES")
    }
}
