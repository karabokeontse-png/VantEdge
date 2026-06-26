package com.vantedge.app.data.engine

import org.junit.Assert.*
import org.junit.Test

class JsonRepairUtilTest {

    @Test
    fun `comma inside string must not be modified`() {
        val input = """{"description": "Skills include C, C++, and Java, }"}"""
        val result = JsonRepairUtil.repair(input)

        assertTrue("isSafe should be true for valid JSON", result.isSafe)
        assertTrue(
            "commas inside string must be preserved",
            result.json.contains("C, C++, and Java, }")
        )
        assertTrue(
            "closing brace inside string must be preserved",
            result.json.contains("and Java, }")
        )
        assertTrue(
            "string content inside quotes must remain intact",
            result.json.contains("\"Skills include C, C++, and Java, }\"")
        )
    }

    @Test
    fun `trailing commas removed only structurally`() {
        val input = """{"skills": ["java", "kotlin",], "valid": true,}"""
        val result = JsonRepairUtil.repair(input)

        assertTrue("isSafe should be true", result.isSafe)
        assertFalse(
            "trailing comma before ] must be removed",
            result.json.contains(",]")
        )
        assertFalse(
            "trailing comma before } must be removed",
            result.json.endsWith(",") || Regex(",\\s*}").containsMatchIn(result.json)
        )
        assertTrue(
            "non-trailing commas must be preserved",
            result.json.contains("\"java\", \"kotlin\"")
        )
        assertTrue(
            "array brackets must surround values",
            result.json.contains("[\"java\", \"kotlin\"]")
        )
        assertTrue(
            "valid field must be present with true value",
            result.json.contains("\"valid\": true")
        )
        assertTrue(
            "result must start with opening brace",
            result.json.startsWith("{")
        )
        assertTrue(
            "result must end with closing brace",
            result.json.endsWith("}")
        )
    }

    @Test
    fun `incomplete JSON handling`() {
        val input = """{"skills": ["java", "kotlin"]"""
        val result = JsonRepairUtil.repair(input)

        if (result.isSafe) {
            assertTrue(
                "result must end with closing brace when completed",
                result.json.endsWith("}")
            )
            assertTrue(
                "result must start with opening brace",
                result.json.startsWith("{")
            )
            assertTrue(
                "skills key must be present",
                result.json.contains("\"skills\"")
            )
            assertTrue(
                "java must be present in array",
                result.json.contains("\"java\"")
            )
            assertTrue(
                "kotlin must be present in array",
                result.json.contains("\"kotlin\"")
            )
            assertFalse(
                "array must not contain trailing comma after completion",
                result.json.contains(",]")
            )
        }
        // no crash is the fallback assertion regardless of isSafe
    }

    @Test
    fun `nested structure integrity`() {
        val input = """{"a": {"b": ["x", "y",]}, "c": "ok",}"""
        val result = JsonRepairUtil.repair(input)

        assertTrue("isSafe should be true", result.isSafe)
        assertFalse(
            "trailing comma inside nested array must be removed",
            result.json.contains(",]")
        )
        assertFalse(
            "trailing comma after last field must be removed",
            result.json.endsWith(",") || Regex(",\\s*}").containsMatchIn(result.json)
        )
        assertTrue(
            "outer key 'a' must be present",
            result.json.contains("\"a\"")
        )
        assertTrue(
            "nested key 'b' must be present",
            result.json.contains("\"b\"")
        )
        assertTrue(
            "inner value 'x' must be present",
            result.json.contains("\"x\"")
        )
        assertTrue(
            "inner value 'y' must be present",
            result.json.contains("\"y\"")
        )
        assertTrue(
            "outer key 'c' must be present",
            result.json.contains("\"c\"")
        )
        assertTrue(
            "value 'ok' must be present",
            result.json.contains("\"ok\"")
        )
        assertTrue(
            "result must start with opening brace",
            result.json.startsWith("{")
        )
        assertTrue(
            "result must end with closing brace",
            result.json.endsWith("}")
        )
        assertTrue(
            "nested object must have opening brace",
            result.json.contains("\"a\": {")
        )
    }
}
