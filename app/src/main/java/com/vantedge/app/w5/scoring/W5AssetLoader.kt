package com.vantedge.app.w5.scoring

import android.content.Context
import org.json.JSONObject

object W5AssetLoader {
    private lateinit var _assets: ValidationAssets

    fun load(context: Context) {
        val am = context.assets

        val stopWordsJson = JSONObject(
            am.open("w5/stop_words.json").bufferedReader().readText()
        )
        val stopWords = mutableSetOf<String>()
        val swArr = stopWordsJson.getJSONArray("stopWords")
        for (i in 0 until swArr.length()) {
            stopWords.add(swArr.getString(i).lowercase())
        }

        val dictJson = JSONObject(
            am.open("w5/keyword_dictionary.json").bufferedReader().readText()
        )
        val keywordDictionary = mutableMapOf<String, List<String>>()
        val dictObj = dictJson.getJSONObject("keywordDictionary")
        val keys = dictObj.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val list = mutableListOf<String>()
            val listArr = dictObj.getJSONArray(key)
            for (i in 0 until listArr.length()) {
                list.add(listArr.getString(i).lowercase())
            }
            keywordDictionary[key] = list
        }

        _assets = ValidationAssets(
            stopWords = stopWords,
            keywordDictionary = keywordDictionary
        )
    }

    fun getAssets(): ValidationAssets {
        if (!::_assets.isInitialized) {
            throw IllegalStateException(
                "W5AssetLoader.load(context) must be called before getAssets()"
            )
        }
        return _assets
    }
}
