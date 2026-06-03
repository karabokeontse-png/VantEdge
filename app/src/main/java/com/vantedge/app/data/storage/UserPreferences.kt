package com.vantedge.app.data.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vantedge.app.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private val Context.dataStore by preferencesDataStore(name = "user_profile")

class UserPreferences(private val context: Context) {

    companion object {
        val NAME = stringPreferencesKey("name")
        val EMAIL = stringPreferencesKey("email")
        val PHONE = stringPreferencesKey("phone")
        val LOCATION = stringPreferencesKey("location")
        val LINKEDIN = stringPreferencesKey("linkedin")
        val SUMMARY = stringPreferencesKey("summary")
        val SKILLS = stringPreferencesKey("skills")
        val EDUCATION = stringPreferencesKey("education")
        val CERTIFICATIONS = stringPreferencesKey("certifications")
        val LANGUAGES = stringPreferencesKey("languages")
        val WORK_HISTORY = stringPreferencesKey("work_history")
        val ONBOARDING_COMPLETE = booleanPreferencesKey("onboarding_complete")
    }

    val userProfileFlow: Flow<UserProfile> = context.dataStore.data.map { prefs ->
        UserProfile(
            name = prefs[NAME] ?: "",
            email = prefs[EMAIL] ?: "",
            phone = prefs[PHONE] ?: "",
            location = prefs[LOCATION] ?: "",
            linkedIn = prefs[LINKEDIN] ?: "",
            summary = prefs[SUMMARY] ?: "",
            skills = prefs[SKILLS]
                ?.split(",")
                ?.map { it.trim() }
                ?.filter { it.isNotBlank() }
                ?: emptyList(),
            education = prefs[EDUCATION]
                ?.split(",")
                ?.map { it.trim() }
                ?.filter { it.isNotBlank() }
                ?: emptyList(),
            certifications = parseCertifications(prefs[CERTIFICATIONS] ?: "[]"),
            languages = prefs[LANGUAGES]
                ?.split(",")
                ?.map { it.trim() }
                ?.filter { it.isNotBlank() }
                ?: emptyList(),
            workHistory = parseWorkHistory(prefs[WORK_HISTORY] ?: "[]")
        )
    }

    val onboardingCompleteFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[ONBOARDING_COMPLETE] ?: false
    }

    suspend fun saveProfile(profile: UserProfile) {
        context.dataStore.edit { prefs ->
            prefs[NAME] = profile.name
            prefs[EMAIL] = profile.email
            prefs[PHONE] = profile.phone
            prefs[LOCATION] = profile.location
            prefs[LINKEDIN] = profile.linkedIn
            prefs[SUMMARY] = profile.summary
            prefs[SKILLS] = profile.skills.joinToString(",")
            prefs[EDUCATION] = profile.education.joinToString(",")
            prefs[LANGUAGES] = profile.languages.joinToString(",")
            prefs[CERTIFICATIONS] = serializeCertifications(profile.certifications)
            prefs[WORK_HISTORY] = serializeWorkHistory(profile.workHistory)
            prefs[ONBOARDING_COMPLETE] = true
        }
    }

    private fun serializeCertifications(list: List<Certification>): String {
        val array = JSONArray()
        list.forEach { cert ->
            array.put(JSONObject().apply {
                put("id", cert.id)
                put("name", cert.name)
                put("issuer", cert.issuer)
                put("localFileUri", cert.localFileUri)
                put("cloudUrl", cert.cloudUrl)
                put("verificationUrl", cert.verificationUrl)
                put("issueDate", cert.issueDate)
                put("expiryDate", cert.expiryDate)
                put("dateAdded", cert.dateAdded)
            })
        }
        return array.toString()
    }

    private fun parseCertifications(json: String): List<Certification> {
        return try {
            val array = JSONArray(json)
            (0 until array.length()).map { i ->
                val obj = array.getJSONObject(i)
                Certification(
                    id = obj.optString("id"),
                    name = obj.optString("name"),
                    issuer = obj.optString("issuer"),
                    localFileUri = obj.optString("localFileUri").takeIf { it.isNotBlank() },
                    cloudUrl = obj.optString("cloudUrl").takeIf { it.isNotBlank() },
                    verificationUrl = obj.optString("verificationUrl").takeIf { it.isNotBlank() },
                    issueDate = obj.optLong("issueDate"),
                    expiryDate = obj.optLong("expiryDate"),
                    dateAdded = obj.optLong("dateAdded")
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun serializeWorkHistory(list: List<WorkExperience>): String {
        val array = JSONArray()
        list.forEach { w ->
            array.put(JSONObject().apply {
                put("role", w.role)
                put("company", w.company)
                put("startDate", w.startDate)
                put("endDate", w.endDate)
                put("description", w.description)
            })
        }
        return array.toString()
    }

    private fun parseWorkHistory(json: String): List<WorkExperience> {
        return try {
            val array = JSONArray(json)
            (0 until array.length()).map { i ->
                val obj = array.getJSONObject(i)
                WorkExperience(
                    role = obj.optString("role"),
                    company = obj.optString("company"),
                    startDate = obj.optString("startDate"),
                    endDate = obj.optString("endDate"),
                    description = obj.optString("description")
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}