package com.vantedge.app.util

import java.security.MessageDigest

object HashUtils {
    fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = input.toByteArray(Charsets.UTF_8)
        return digest.digest(bytes).joinToString("") { "%02x".format(it) }
    }
}
