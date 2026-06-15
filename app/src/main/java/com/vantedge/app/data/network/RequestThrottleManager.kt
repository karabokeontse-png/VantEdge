package com.vantedge.app.data.network

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object RequestThrottleManager {

    private val mutex = Mutex()

    private var lastRequestTime = 0L

    private const val MIN_INTERVAL_MS = 12000L

    suspend fun <T> throttle(block: suspend () -> T): T {
        return mutex.withLock {
            val now = System.currentTimeMillis()
            val elapsed = now - lastRequestTime

            if (elapsed < MIN_INTERVAL_MS) {
                val waitTime = MIN_INTERVAL_MS - elapsed
                kotlinx.coroutines.delay(waitTime)
            }

            lastRequestTime = System.currentTimeMillis()
            block()
        }
    }
}