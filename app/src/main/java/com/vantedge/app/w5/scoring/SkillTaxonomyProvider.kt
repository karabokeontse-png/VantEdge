package com.vantedge.app.w5.scoring

import android.content.Context
import org.json.JSONObject

object SkillTaxonomyProvider {
    private var _taxonomy: Set<String>? = null

    fun load(context: Context) {
        val am = context.assets
        val skillJson = JSONObject(
            am.open("validation/skill_taxonomy.json").bufferedReader().readText()
        )
        _taxonomy = parseTaxonomy(skillJson)
    }

    fun getTaxonomy(): Set<String> {
        _taxonomy?.let { return it }
        val stream = javaClass.classLoader?.getResourceAsStream("validation/skill_taxonomy.json")
        if (stream != null) {
            _taxonomy = parseTaxonomy(JSONObject(stream.bufferedReader().readText()))
            return _taxonomy!!
        }
        throw IllegalStateException(
            "SkillTaxonomyProvider.load(context) must be called before getTaxonomy()"
        )
    }

    private fun parseTaxonomy(skillJson: JSONObject): Set<String> {
        val skills = mutableSetOf<String>()
        val cats = skillJson.getJSONArray("categories")
        for (i in 0 until cats.length()) {
            val arr = cats.getJSONObject(i).getJSONArray("skills")
            for (j in 0 until arr.length()) {
                skills.add(arr.getString(j).lowercase())
            }
        }
        return skills
    }
}
