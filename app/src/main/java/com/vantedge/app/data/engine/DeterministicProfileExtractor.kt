package com.vantedge.app.data.engine

import com.vantedge.app.w5.scoring.SkillTaxonomyProvider
import org.json.JSONArray
import org.json.JSONObject

/**
 * DETERMINISTIC-FIRST profile extraction (no AI).
 *
 * Builds the SAME JSON schema that the AI prompt requests, from the already-extracted
 * document text, using only evidence present in the text (no fabrication). The result is
 * fed through the EXISTING pipeline (JsonRepairUtil -> normalizeRootKeys -> parse ->
 * ExtractionValidator -> P2ValidationEngine).
 *
 * Returns null when the document does not contain the fields required by the output
 * contract (name AND email). Callers must treat null as a truthful "AI unavailable /
 * insufficient deterministic evidence" outcome, never as success.
 */
object DeterministicProfileExtractor {

    private val EMAIL = Regex("""[a-zA-Z0-9._%+\-]+@[a-zA-Z0-9.\-]+\.[a-zA-Z]{2,}""")
    private val PHONE = Regex("""(\+?\d[\d\s\-().]{7,}\d)""")
    private val URL = Regex("""(https?://\S+|www\.\S+|linkedin\.com/\S+)""", RegexOption.IGNORE_CASE)
    private val YEAR = Regex("""\b(19|20)\d{2}\b""")
    private val DATE_RANGE = Regex(
        """(?i)((?:jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[a-z]*\.?\s*\d{4}|(?:19|20)\d{2})\s*(?:-|–|—|to|until|till)\s*(present|current|now|(?:jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[a-z]*\.?\s*\d{4}|(?:19|20)\d{2})"""
    )
    private val DEGREE = Regex(
        """(?i)\b(bachelor|master|b\.?sc|b\.?a\b|b\.?eng|b\.?com|m\.?sc|m\.?a\b|m\.?eng|mba|ph\.?d|diploma|honours|honors|degree|doctorate)\b"""
    )

    private val SECTION_ALIASES = mapOf(
        "summary" to listOf("summary", "profile", "about", "professional summary", "personal statement", "objective", "career summary"),
        "experience" to listOf("experience", "work experience", "work history", "employment", "professional experience", "employment history", "career history"),
        "education" to listOf("education", "academic background", "academics", "qualifications", "educational background"),
        "skills" to listOf("skills", "technical skills", "core competencies", "competencies", "key skills", "core skills",
            "professional skills", "areas of expertise", "expertise"),
        "certifications" to listOf("certifications", "certificates", "licenses", "professional certifications", "courses")
    )

    fun extractJson(rawText: String): String? {
        if (rawText.isBlank()) return null

        val lines = rawText.lines().map { it.trim() }.filter { it.isNotBlank() }
        if (lines.isEmpty()) return null

        val email = EMAIL.find(rawText)?.value?.trim()
        val phone = PHONE.find(rawText)?.value?.trim()
        val linkedIn = URL.findAll(rawText).map { it.value }.firstOrNull { it.contains("linkedin", true) }
        val name = extractName(lines)

        // Output contract: name + email are required by ExtractionValidator (BLOCKER).
        if (name.isNullOrBlank() || email.isNullOrBlank()) return null

        val sections = collectSections(lines)

        val summary = sections["summary"]?.joinToString(" ") { it }?.trim()?.take(500)?.takeIf { it.isNotBlank() }

        val skills = extractSkills(rawText, sections["skills"])
        val work = extractWork(sections["experience"])
        val education = extractEducation(sections["education"])
        val certs = extractCertifications(sections["certifications"])

        val root = JSONObject()
        root.put("name", name)
        root.put("email", email)
        root.put("phone", phone ?: "")
        root.put("summary", summary ?: "")
        if (linkedIn != null) root.put("linkedIn", linkedIn)

        val skillsArr = JSONArray()
        skills.forEach { skillsArr.put(it) }
        root.put("skills", skillsArr)

        val workArr = JSONArray()
        work.forEach { w ->
            val o = JSONObject()
            o.put("jobTitle", w.first)
            o.put("company", w.second)
            o.put("startDate", w.third)
            o.put("endDate", w.fourth)
            o.put("description", w.fifth)
            workArr.put(o)
        }
        root.put("workHistory", workArr)

        val eduArr = JSONArray()
        education.forEach { e ->
            val o = JSONObject()
            o.put("institution", e.first)
            o.put("qualification", e.second)
            o.put("fieldOfStudy", e.third)
            o.put("graduationYear", e.fourth)
            eduArr.put(o)
        }
        root.put("education", eduArr)

        val certArr = JSONArray()
        certs.forEach { certArr.put(it) }
        root.put("certifications", certArr)

        return root.toString()
    }

    // ---------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------

    private fun normalizeHeader(line: String): String? {
        val l = line.lowercase().trim().trimEnd(':').trim()
        if (l.length > 40) return null
        for ((key, aliases) in SECTION_ALIASES) {
            if (aliases.any { l == it || l.startsWith("$it ") }) return key
        }
        return null
    }

    private fun collectSections(lines: List<String>): Map<String, List<String>> {
        val result = mutableMapOf<String, MutableList<String>>()
        var current: String? = null
        for (line in lines) {
            val header = normalizeHeader(line)
            if (header != null) {
                current = header
                if (result[header] == null) result[header] = mutableListOf()
                continue
            }
            if (current != null) result[current]!!.add(line)
        }
        return result
    }

    private fun extractName(lines: List<String>): String? {
        for (line in lines.take(12)) {
            if (EMAIL.containsMatchIn(line) || URL.containsMatchIn(line)) continue
            if (normalizeHeader(line) != null) continue
            if (line.contains("@") || line.count { it.isDigit() } > 0) continue
            if (line.split(Regex("\\s+")).size !in 2..5) continue
            if (line.length !in 3..60) continue
            val letters = line.count { it.isLetter() }
            if (letters < 3) continue
            if (letters.toFloat() / line.length < 0.6f) continue
            return line
        }
        return null
    }

    private fun extractSkills(rawText: String, skillSection: List<String>?): List<String> {
        val out = LinkedHashSet<String>()
        val lower = rawText.lowercase()
        val taxonomy = try {
            SkillTaxonomyProvider.getTaxonomy()
        } catch (e: Exception) {
            emptySet()
        }
        // Evidence-based: the skill token must literally appear in the document text.
        for (skill in taxonomy) {
            if (skill.length < 2) continue
            if (Regex("""(?<![a-z0-9])${Regex.escape(skill)}(?![a-z0-9])""").containsMatchIn(lower)) {
                out.add(skill)
            }
        }
        // Additional skills listed verbatim in a Skills section (comma/bullet/pipe separated).
        skillSection?.forEach { line ->
            line.split(Regex("[,;|•·/]+")).forEach { token ->
                val t = token.trim().trimEnd('.', '-').trim()
                if (t.length in 2..40 && t.count { it.isLetter() } >= 2) {
                    if (!t.equals("skills", true)) out.add(t)
                }
            }
        }
        return out.take(60)
    }

    // (title, company, startDate, endDate, description)
    private fun extractWork(section: List<String>?): List<WorkTuple> {
        if (section.isNullOrEmpty()) return emptyList()
        val out = mutableListOf<WorkTuple>()
        var previous = ""
        for (line in section) {
            val range = DATE_RANGE.find(line)
            if (range == null) {
                previous = line
                continue
            }
            val rangeText = range.value
            val before = line.substring(0, range.range.first).trim().trimEnd(',', '-', '–', '—', '|').trim()
            val titleSource = if (before.isNotBlank()) before else previous
            if (titleSource.isBlank()) { previous = line; continue }

            val parts = titleSource.split(Regex("""\s+(?:at|@|[-–—|])\s+|,\s+"""))
            val title = parts.firstOrNull()?.trim().orEmpty()
            val company = parts.drop(1).joinToString(", ").trim()
            if (title.isBlank()) { previous = line; continue }

            val dates = rangeText.split(Regex("""(?i)\s*(?:-|–|—|to|until|till)\s*"""))
            val start = dates.getOrNull(0)?.trim().orEmpty()
            val end = dates.getOrNull(1)?.trim().orEmpty()
            out.add(WorkTuple(title, company, start, end, ""))
            previous = ""
            if (out.size >= 8) break
        }
        return out
    }

    // (institution, qualification, fieldOfStudy, graduationYear)
    private fun extractEducation(section: List<String>?): List<EduTuple> {
        if (section.isNullOrEmpty()) return emptyList()
        val out = mutableListOf<EduTuple>()
        var previous = ""
        for (line in section) {
            if (DEGREE.containsMatchIn(line)) {
                val qual = line.trim()
                val inst = previous
                val year = YEAR.find(line)?.value ?: YEAR.find(previous)?.value ?: ""
                out.add(EduTuple(inst, qual, "", year))
                previous = line
            } else {
                previous = line
            }
        }
        // If degree lines had no preceding institution, still return what we found (institution may be blank -> warning only).
        return out
    }

    private fun extractCertifications(section: List<String>?): List<String> {
        if (section.isNullOrEmpty()) return emptyList()
        return section.asSequence()
            .map { it.trim() }
            .filter { it.length in 3..120 && it.count { c -> c.isLetter() } >= 3 }
            .take(10)
            .toList()
    }

    private data class WorkTuple(
        val first: String, val second: String, val third: String, val fourth: String, val fifth: String
    )
    private data class EduTuple(
        val first: String, val second: String, val third: String, val fourth: String
    )
}
