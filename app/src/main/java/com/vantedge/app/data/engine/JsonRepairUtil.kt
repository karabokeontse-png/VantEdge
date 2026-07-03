package com.vantedge.app.data.engine

import org.json.JSONArray
import org.json.JSONObject
import java.util.Stack

object JsonRepairUtil {

    data class RepairResult(val json: String, val isSafe: Boolean)

    fun repair(raw: String): RepairResult {
        val sb = StringBuilder()
        val stack = Stack<Char>()
        var inString = false
        var escaped = false

        raw.forEach { c ->
            sb.append(c)
            if (inString) {
                if (escaped) {
                    escaped = false
                } else if (c == '\\') {
                    escaped = true
                } else if (c == '"') {
                    inString = false
                }
            } else {
                when (c) {
                    '"' -> inString = true
                    '{' -> stack.push('{')
                    '[' -> stack.push('[')
                    '}' -> if (stack.isNotEmpty() && stack.peek() == '{') stack.pop()
                    ']' -> if (stack.isNotEmpty() && stack.peek() == '[') stack.pop()
                }
            }
        }

        if (inString) {
            return RepairResult(raw, false)
        }

        while (stack.isNotEmpty()) {
            when (stack.pop()) {
                '{' -> sb.append("}")
                '[' -> sb.append("]")
            }
        }

        val finalString = removeTrailingCommas(sb.toString())

        return validate(finalString, raw)
    }

    private fun validate(result: String, original: String): RepairResult {
        try {
            val obj = JSONObject(result)
            if (obj.length() > 0) {
                return RepairResult(result, true)
            }
        } catch (_: Exception) {}

        try {
            val arr = JSONArray(result)
            if (arr.length() > 0) {
                return RepairResult(result, true)
            }
        } catch (_: Exception) {}

        return RepairResult(original, false)
    }

    private fun removeTrailingCommas(json: String): String {
        val sb = StringBuilder()
        var inString = false
        var escaped = false
        var i = 0

        while (i < json.length) {
            val c = json[i]

            if (inString) {
                sb.append(c)
                if (escaped) {
                    escaped = false
                } else if (c == '\\') {
                    escaped = true
                } else if (c == '"') {
                    inString = false
                }
                i++
                continue
            }

            when {
                c == '"' -> {
                    inString = true
                    sb.append(c)
                }
                c == ',' -> {
                    var j = i + 1
                    while (j < json.length && json[j].isWhitespace()) {
                        j++
                    }
                    if (j < json.length && (json[j] == '}' || json[j] == ']')) {
                        // skip trailing comma
                    } else {
                        sb.append(c)
                    }
                }
                else -> sb.append(c)
            }
            i++
        }

        return sb.toString()
    }
}
