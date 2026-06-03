package com.vantedge.app.util

import java.security.MessageDigest

/**
 * Pure SHA-256 hashing utility.
 * Used for privacy-safe document deduplication in telemetry.
 *
 * Contracts:
 * - Encoding: UTF-8
 * - Output: lowercase hexadecimal string (64 chars)
 * - Pure function: no state, no side effects
 */
object HashUtils {

    /**
     * Computes SHA-256 hash of [input].
     *
     * @param input Raw string to hash (typically CV rawText)
     * @return 64-character lowercase hex digest
     */
    fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest(input.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
