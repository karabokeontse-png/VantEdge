package com.vantedge.observability.p2.replay

import com.vantedge.pipeline.validation.ValidationDecision
import com.vantedge.pipeline.validation.ValidationTrace
import org.json.JSONArray
import org.json.JSONObject

data class RuleSetSnapshot(
    val rules: List<ReplayRule>
)

data class ReplayRule(
    val name: String,
    val field: String,
    val type: String,
    val params: Map<String, Any> = emptyMap(),
    val severity: String,
    val failureCode: String = ""
)

object ReplayEngine {

    fun replay(
        input: Any,
        ruleSetSnapshot: RuleSetSnapshot
    ): ValidationDecision {
        val trace = ValidationTrace(
            pipelineStage = "replay",
            correlationId = "replay-${System.currentTimeMillis()}"
        )
        for (rule in ruleSetSnapshot.rules) {
            val result = evaluate(rule, input, trace)
            trace.addResult(result)
        }
        return computeDecision(trace)
    }

    fun snapshotFromJson(json: JSONObject): RuleSetSnapshot {
        val rules = mutableListOf<ReplayRule>()
        val stages = listOf("compatibility", "job_extraction", "generator")
        for (stage in stages) {
            val stageObj = json.optJSONObject(stage) ?: continue
            val rulesArray = stageObj.optJSONArray("rules") ?: continue
            for (i in 0 until rulesArray.length()) {
                val r = rulesArray.getJSONObject(i)
                val params = mutableMapOf<String, Any>()
                val paramsObj = r.optJSONObject("params")
                if (paramsObj != null) {
                    for (key in paramsObj.keys()) {
                        val v = paramsObj.get(key)
                        when (v) {
                            is Int -> params[key] = v
                            is String -> params[key] = v
                            is Double -> params[key] = v
                            is Boolean -> params[key] = v
                        }
                    }
                }
                rules.add(ReplayRule(
                    name = r.getString("name"),
                    field = r.getString("field"),
                    type = r.getString("type"),
                    params = params,
                    severity = r.getString("severity"),
                    failureCode = r.optString("failure_code")
                ))
            }
        }
        return RuleSetSnapshot(rules)
    }

    private fun evaluate(
        rule: ReplayRule,
        input: Any,
        trace: ValidationTrace
    ): com.vantedge.pipeline.validation.RuleResult {
        return when (rule.type) {
            "non_blank_string" -> evaluateNonBlankString(rule, input)
            "non_empty_array" -> evaluateNonEmptyArray(rule, input)
            "int_range" -> evaluateIntRange(rule, input)
            "min_length" -> evaluateMinLength(rule, input)
            else -> com.vantedge.pipeline.validation.RuleResult(
                rule.name, rule.field, true, rule.severity
            )
        }
    }

    private fun resolveTextValue(rule: ReplayRule, input: Any): String {
        return if (input is String) {
            input
        } else if (input is JSONObject) {
            input.optString(rule.field, "")
        } else {
            input.toString()
        }
    }

    private fun evaluateNonBlankString(
        rule: ReplayRule, input: Any
    ): com.vantedge.pipeline.validation.RuleResult {
        val value = resolveTextValue(rule, input)
        val passed = value.isNotBlank()
        return com.vantedge.pipeline.validation.RuleResult(
            rule.name, rule.field, passed, rule.severity
        )
    }

    private fun evaluateNonEmptyArray(
        rule: ReplayRule, input: Any
    ): com.vantedge.pipeline.validation.RuleResult {
        if (input is JSONObject) {
            val arr = input.optJSONArray(rule.field)
            val passed = arr != null && arr.length() > 0
            return com.vantedge.pipeline.validation.RuleResult(
                rule.name, rule.field, passed, rule.severity
            )
        }
        return com.vantedge.pipeline.validation.RuleResult(
            rule.name, rule.field, false, rule.severity,
            detail = "input is not JSONObject"
        )
    }

    private fun evaluateIntRange(
        rule: ReplayRule, input: Any
    ): com.vantedge.pipeline.validation.RuleResult {
        val min = (rule.params["min"] as? Int) ?: 0
        val max = (rule.params["max"] as? Int) ?: 100
        val value = if (input is JSONObject) {
            input.optInt(rule.field, Int.MAX_VALUE)
        } else {
            Int.MAX_VALUE
        }
        val passed = value in min..max
        return com.vantedge.pipeline.validation.RuleResult(
            rule.name, rule.field, passed, rule.severity,
            detail = if (!passed) "value=$value range=[$min,$max]" else null
        )
    }

    private fun evaluateMinLength(
        rule: ReplayRule, input: Any
    ): com.vantedge.pipeline.validation.RuleResult {
        val minChars = (rule.params["min_chars"] as? Int) ?: 100
        val minWords = (rule.params["min_words"] as? Int) ?: 20
        val text = resolveTextValue(rule, input)
        val charOk = text.length >= minChars
        val wordOk = text.split("\\s+".toRegex()).count { it.isNotBlank() } >= minWords
        val passed = charOk && wordOk
        return com.vantedge.pipeline.validation.RuleResult(
            rule.name, rule.field, passed, rule.severity,
            detail = if (!passed) "chars=${text.length}" else null
        )
    }

    private fun computeDecision(trace: ValidationTrace): ValidationDecision {
        val blockerFails = trace.rules.filter { it.severity == "BLOCKER" && !it.passed }
        val warnings = trace.rules.filter { !it.passed && it.severity != "BLOCKER" }.map { it.ruleName }
        return when {
            blockerFails.isNotEmpty() -> ValidationDecision.Reject(
                blockerFails.first().detail ?: "BLOCKER: ${blockerFails.first().ruleName}",
                trace
            )
            warnings.isNotEmpty() -> ValidationDecision.Degraded(trace, warnings)
            else -> ValidationDecision.Accept(trace)
        }
    }
}
