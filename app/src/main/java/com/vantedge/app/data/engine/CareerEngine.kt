package com.vantedge.app.data.engine

import com.vantedge.app.data.model.UserProfile

object CareerEngine {

    fun generateCV(profile: UserProfile): String {
        return buildString {
            appendLine(profile.name)
            appendLine(profile.email)
            appendLine(profile.phone)
            appendLine(profile.location)
            appendLine(profile.linkedIn)
            appendLine()

            appendLine("Summary")
            appendLine(profile.summary)
            appendLine()

            appendLine("Skills")
            profile.skills.forEach { appendLine("- $it") }
            appendLine()

            appendLine("Work Experience")
            profile.workHistory.forEach { work ->
                appendLine("${work.role} at ${work.company}")
                appendLine("${work.startDate} - ${work.endDate}")
                appendLine(work.description)
                appendLine()
            }

            appendLine("Education")
            profile.education.forEach { appendLine("- $it") }
            appendLine()

            appendLine("Certifications")
            profile.certifications.forEach { appendLine("- $it") }
            appendLine()

            appendLine("Languages")
            profile.languages.forEach { appendLine("- $it") }
        }
    }

    fun generateCoverLetter(profile: UserProfile, jobDescription: String): String {
        return """
            Dear Hiring Manager,

            My name is ${profile.name}, and I am applying for this role.

            $jobDescription

            With my experience in:
            ${profile.skills.joinToString(", ")}

            I believe I am a strong candidate.

            Sincerely,
            ${profile.name}
        """.trimIndent()
    }
}