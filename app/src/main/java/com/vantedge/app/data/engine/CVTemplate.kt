package com.vantedge.app.data.engine

import com.vantedge.app.data.model.UserProfile

object CVTemplate {

    fun render(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        matchedKeywords: List<String>,
        designId: String,
        schemeId: String
    ): String {
        val colors = getColors(schemeId)
        return when (designId) {
            "modern" -> modernTemplate(profile, jobTitle, company, matchedKeywords, colors)
            "minimalist" -> minimalistTemplate(profile, jobTitle, company, matchedKeywords, colors)
            "creative" -> creativeTemplate(profile, jobTitle, company, matchedKeywords, colors)
            "executive" -> executiveTemplate(profile, jobTitle, company, matchedKeywords, colors)
            else -> modernTemplate(profile, jobTitle, company, matchedKeywords, colors)
        }
    }

    fun renderCoverLetter(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        content: String,
        designId: String,
        schemeId: String
    ): String {
        val colors = getColors(schemeId)
        val font = if (designId == "executive") "Georgia, serif" else "Arial, sans-serif"
        return """
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
body { font-family: $font; margin: 20mm; color: ${colors.text}; background: ${colors.background}; font-size: 11pt; line-height: 1.6; }
.header { border-bottom: 2px solid ${colors.primary}; padding-bottom: 10px; margin-bottom: 20px; }
.name { font-size: 20pt; color: ${colors.primary}; font-weight: bold; }
.contact { color: ${colors.secondary}; font-size: 10pt; margin-top: 4px; }
p { margin: 0 0 12px 0; }
</style>
</head>
<body>
<div class="header">
<div class="name">${profile.name}</div>
<div class="contact">${profile.email} | ${profile.phone} | ${profile.location}</div>
</div>
$content
</body>
</html>
        """.trimIndent()
    }

    private data class Colors(
        val primary: String,
        val secondary: String,
        val accent: String,
        val background: String,
        val text: String,
        val sidebarBg: String,
        val sidebarText: String
    )

    private fun getColors(schemeId: String): Colors {
        return when (schemeId) {
            "navy" -> Colors("#1A237E", "#607D8B", "#ECEFF1", "#FFFFFF", "#212121", "#1A237E", "#FFFFFF")
            "emerald" -> Colors("#263238", "#2E7D32", "#EEEEEE", "#FFFFFF", "#212121", "#263238", "#FFFFFF")
            "monochrome" -> Colors("#000000", "#616161", "#FAFAFA", "#FFFFFF", "#212121", "#000000", "#FFFFFF")
            "burgundy" -> Colors("#4A0E2B", "#C4A882", "#2C1810", "#FFFFFF", "#212121", "#4A0E2B", "#FFFFFF")
            else -> Colors("#1A237E", "#607D8B", "#ECEFF1", "#FFFFFF", "#212121", "#1A237E", "#FFFFFF")
        }
    }

    // Keywords are bolded, not highlighted in yellow
    private fun highlightKeywords(text: String, keywords: List<String>): String {
        var result = text
        keywords.forEach { keyword ->
            result = result.replace(
                keyword,
                "<strong>$keyword</strong>",
                ignoreCase = true
            )
        }
        return result
    }

    private fun modernTemplate(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        keywords: List<String>,
        colors: Colors
    ): String {
        val experience = profile.workHistory.joinToString("") { exp ->
            """
            <div style='margin-bottom:12px;'>
            <div style='font-weight:bold;color:${colors.primary};'>${exp.role}</div>
            <div style='color:${colors.secondary};font-size:10pt;'>${exp.company} | ${exp.startDate} – ${exp.endDate}</div>
            <div style='margin-top:4px;'>${highlightKeywords(exp.description, keywords)}</div>
            </div>
            """.trimIndent()
        }

        val education = profile.education.joinToString("") {
            "<div style='margin-bottom:6px;'>$it</div>"
        }

        return """
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8">
<style>
* { margin:0; padding:0; box-sizing:border-box; }
body { font-family: Arial, sans-serif; background:#fff; color:${colors.text}; font-size:10pt; }
.container { display:flex; min-height:297mm; width:210mm; margin:auto; }
.sidebar { width:35%; background:${colors.sidebarBg}; color:${colors.sidebarText}; padding:20px; }
.main { width:65%; padding:20px; }
.name { font-size:18pt; font-weight:bold; margin-bottom:4px; }
.jobtitle { font-size:11pt; opacity:0.85; margin-bottom:16px; }
.section-title { font-size:11pt; font-weight:bold; text-transform:uppercase; letter-spacing:1px; border-bottom:1px solid ${colors.sidebarText}; padding-bottom:4px; margin:16px 0 8px 0; opacity:0.9; }
.main-section-title { font-size:11pt; font-weight:bold; text-transform:uppercase; letter-spacing:1px; color:${colors.primary}; border-bottom:2px solid ${colors.primary}; padding-bottom:4px; margin:16px 0 10px 0; }
.contact-item { font-size:9pt; margin-bottom:6px; opacity:0.9; }
</style>
</head>
<body>
<div class="container">
<div class="sidebar">
<div class="name">${profile.name}</div>
<div class="jobtitle">$jobTitle</div>
<div class="section-title">Contact</div>
<div class="contact-item">${profile.email}</div>
<div class="contact-item">${profile.phone}</div>
<div class="contact-item">${profile.location}</div>
<div class="contact-item">${profile.linkedIn}</div>
<div class="section-title">Skills</div>
${profile.skills.joinToString("") { "<div class='contact-item'>• $it</div>" }}
<div class="section-title">Languages</div>
${profile.languages.joinToString("") { "<div class='contact-item'>• $it</div>" }}
<div class="section-title">Certifications</div>
${profile.certifications.joinToString("") { "<div class='contact-item'>• $it</div>" }}
</div>
<div class="main">
<div class="main-section-title">Professional Summary</div>
<p>${highlightKeywords(profile.summary, keywords)}</p>
<div class="main-section-title">Experience</div>
$experience
<div class="main-section-title">Education</div>
$education
</div>
</div>
</body>
</html>
        """.trimIndent()
    }

    private fun minimalistTemplate(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        keywords: List<String>,
        colors: Colors
    ): String {
        val experience = profile.workHistory.joinToString("") { exp ->
            """
            <div style='margin-bottom:14px;'>
            <div style='font-weight:bold;'>${exp.role} — ${exp.company}</div>
            <div style='color:${colors.secondary};font-size:10pt;margin-bottom:4px;'>${exp.startDate} – ${exp.endDate}</div>
            <div>${highlightKeywords(exp.description, keywords)}</div>
            </div>
            """.trimIndent()
        }

        return """
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8">
<style>
body { font-family: Helvetica, Arial, sans-serif; margin:25mm 30mm; color:${colors.text}; font-size:10.5pt; line-height:1.7; background:#fff; }
.name { font-size:26pt; font-weight:300; letter-spacing:2px; color:${colors.primary}; margin-bottom:4px; }
.contact { color:${colors.secondary}; font-size:9.5pt; margin-bottom:30px; }
.section { margin-bottom:22px; }
.section-title { font-size:8pt; font-weight:bold; text-transform:uppercase; letter-spacing:3px; color:${colors.secondary}; margin-bottom:10px; }
hr { border:none; border-top:1px solid ${colors.accent}; margin:6px 0 16px 0; }
</style>
</head>
<body>
<div class="name">${profile.name}</div>
<div class="contact">${profile.email} &nbsp;|&nbsp; ${profile.phone} &nbsp;|&nbsp; ${profile.location} &nbsp;|&nbsp; ${profile.linkedIn}</div>
<div class="section">
<div class="section-title">Profile</div><hr>
<p>${highlightKeywords(profile.summary, keywords)}</p>
</div>
<div class="section">
<div class="section-title">Experience</div><hr>
$experience
</div>
<div class="section">
<div class="section-title">Education</div><hr>
${profile.education.joinToString("") { "<div style='margin-bottom:6px;'>$it</div>" }}
</div>
<div class="section">
<div class="section-title">Skills</div><hr>
<div>${profile.skills.joinToString(" &nbsp;·&nbsp; ")}</div>
</div>
<div class="section">
<div class="section-title">Certifications</div><hr>
<div>${profile.certifications.joinToString(" &nbsp;·&nbsp; ")}</div>
</div>
</body>
</html>
        """.trimIndent()
    }

    private fun creativeTemplate(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        keywords: List<String>,
        colors: Colors
    ): String {
        val experience = profile.workHistory.joinToString("") { exp ->
            """
            <div style='margin-bottom:14px;border-left:3px solid ${colors.secondary};padding-left:12px;'>
            <div style='font-weight:bold;color:${colors.primary};'>${exp.role}</div>
            <div style='color:${colors.secondary};font-size:10pt;'>${exp.company} | ${exp.startDate} – ${exp.endDate}</div>
            <div style='margin-top:4px;'>${highlightKeywords(exp.description, keywords)}</div>
            </div>
            """.trimIndent()
        }

        return """
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8">
<style>
* { margin:0; padding:0; box-sizing:border-box; }
body { font-family: Arial, sans-serif; background:#fff; color:${colors.text}; font-size:10pt; }
.header { background:${colors.primary}; color:#fff; padding:24px 28px; }
.name { font-size:22pt; font-weight:bold; letter-spacing:1px; }
.jobtitle { font-size:12pt; opacity:0.85; margin-top:4px; }
.contact-bar { background:${colors.secondary}; color:#fff; padding:8px 28px; font-size:9pt; display:flex; gap:20px; }
.body { display:flex; padding:20px 28px; gap:24px; }
.left { width:60%; }
.right { width:40%; }
.section-title { font-size:11pt; font-weight:bold; color:${colors.primary}; text-transform:uppercase; letter-spacing:1px; margin:16px 0 8px 0; border-bottom:2px solid ${colors.secondary}; padding-bottom:4px; }
.skill-tag { background:${colors.accent};color:${colors.primary};padding:3px 10px;margin:3px 2px;display:inline-block;border-radius:12px;font-size:9.5pt; }
</style>
</head>
<body>
<div class="header">
<div class="name">${profile.name}</div>
<div class="jobtitle">$jobTitle</div>
</div>
<div class="contact-bar">
<span>${profile.email}</span>
<span>${profile.phone}</span>
<span>${profile.location}</span>
<span>${profile.linkedIn}</span>
</div>
<div class="body">
<div class="left">
<div class="section-title">Experience</div>
$experience
<div class="section-title">Education</div>
${profile.education.joinToString("") { "<div style='margin-bottom:6px;'>$it</div>" }}
</div>
<div class="right">
<div class="section-title">Profile</div>
<p>${highlightKeywords(profile.summary, keywords)}</p>
<div class="section-title">Skills</div>
${profile.skills.joinToString("") { "<span class='skill-tag'>$it</span>" }}
<div class="section-title">Certifications</div>
${profile.certifications.joinToString("") { "<div style='margin-bottom:4px;'>• $it</div>" }}
<div class="section-title">Languages</div>
${profile.languages.joinToString("") { "<div style='margin-bottom:4px;'>• $it</div>" }}
</div>
</div>
</body>
</html>
        """.trimIndent()
    }

    private fun executiveTemplate(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        keywords: List<String>,
        colors: Colors
    ): String {
        val experience = profile.workHistory.joinToString("") { exp ->
            """
            <div style='margin-bottom:14px;'>
            <div style='font-weight:bold;'>${exp.role}, ${exp.company}</div>
            <div style='color:${colors.secondary};font-size:10pt;font-style:italic;margin-bottom:4px;'>${exp.startDate} – ${exp.endDate}</div>
            <div>${highlightKeywords(exp.description, keywords)}</div>
            </div>
            """.trimIndent()
        }

        return """
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8">
<style>
body { font-family: Georgia, serif; margin:20mm 25mm; color:${colors.text}; font-size:10.5pt; line-height:1.6; background:#fff; }
.name { font-size:20pt; font-weight:bold; text-align:center; color:${colors.primary}; letter-spacing:2px; margin-bottom:4px; }
.jobtitle { text-align:center; color:${colors.secondary}; font-size:11pt; font-style:italic; margin-bottom:6px; }
.contact { text-align:center; font-size:9.5pt; color:${colors.secondary}; margin-bottom:16px; }
.divider { border:none; border-top:2px solid ${colors.primary}; margin:10px 0; }
.thin-divider { border:none; border-top:1px solid ${colors.secondary}; margin:8px 0; }
.section-title { font-size:11pt; font-weight:bold; text-transform:uppercase; letter-spacing:2px; color:${colors.primary}; margin:16px 0 8px 0; }
</style>
</head>
<body>
<div class="name">${profile.name}</div>
<div class="jobtitle">$jobTitle</div>
<div class="contact">${profile.email} | ${profile.phone} | ${profile.location} | ${profile.linkedIn}</div>
<hr class="divider">
<div class="section-title">Executive Summary</div>
<hr class="thin-divider">
<p>${highlightKeywords(profile.summary, keywords)}</p>
<div class="section-title">Professional Experience</div>
<hr class="thin-divider">
$experience
<div class="section-title">Education</div>
<hr class="thin-divider">
${profile.education.joinToString("") { "<div style='margin-bottom:6px;'>$it</div>" }}
<div class="section-title">Core Competencies</div>
<hr class="thin-divider">
<div>${profile.skills.joinToString(" &nbsp;|&nbsp; ")}</div>
<div class="section-title">Certifications</div>
<hr class="thin-divider">
<div>${profile.certifications.joinToString(" &nbsp;|&nbsp; ")}</div>
</body>
</html>
        """.trimIndent()
    }
}
