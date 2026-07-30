package com.vantedge.app.w5.scoring

import android.content.Context
import org.json.JSONObject

object SkillTaxonomyProvider {
    private lateinit var _taxonomy: Set<String>

    fun load(context: Context) {
        val am = context.assets
        val skillJson = JSONObject(
            am.open("validation/skill_taxonomy.json").bufferedReader().readText()
        )
        val skills = mutableSetOf<String>()
        val cats = skillJson.getJSONArray("categories")
        for (i in 0 until cats.length()) {
            val arr = cats.getJSONObject(i).getJSONArray("skills")
            for (j in 0 until arr.length()) {
                skills.add(arr.getString(j).lowercase())
            }
        }
        _taxonomy = skills
    }

    fun getTaxonomy(): Set<String> {
        if (!::_taxonomy.isInitialized) {
            throw IllegalStateException(
                "SkillTaxonomyProvider.load(context) must be called before getTaxonomy()"
            )
        }
        return _taxonomy
    }
}
