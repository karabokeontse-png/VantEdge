package com.vantedge.app.data.engine.extraction

import com.vantedge.app.data.model.DocumentType
import com.vantedge.app.domain.PipelineTrace
import com.vantedge.app.data.model.extraction.BlockMetadata
import com.vantedge.app.data.model.extraction.BlockType
import com.vantedge.app.data.model.extraction.DocumentBlock
import com.vantedge.app.data.model.extraction.Page
import com.vantedge.app.data.model.extraction.StructuredDocument
import java.util.Locale
import java.util.UUID

object DocumentStructureAnalyzer {
    private val HEADER_FOOTER_BLACKLIST = listOf(
        "vacancy announcement", "vacancy notice", "page", "confidential",
        "biust", "botswana international university"
    )

    private val SECTION_HEADER_PATTERNS = listOf(
        "main purpose of the", "competencies/skills", "qualifications/requirements",
        "key performance areas", "key accountabilities", "duties and responsibilities",
        "experience required", "education required", "how to apply",
        "closing date", "terms of service", "remuneration", "benefits",
        "about the role", "job summary", "position summary",
        "minimum requirements", "preferred requirements",
        "key competencies", "technical competencies", "behavioral competencies"
    )

    private val LIST_INDICATORS = listOf(
        Regex("""^\s*[-•*]\s"""),
        Regex("""^\s*\d+[\.\)]\s"""),
        Regex("""^\s*[a-zA-Z][\.\)]\s"""),
        Regex("""^\s*\([a-zA-Z0-9]\)\s""")
    )

    private val EMAIL_REGEX = Regex("""[a-zA-Z0-9._%+\-]+@[a-zA-Z0-9.\-]+\.[a-zA-Z]{2,}""")
    private val PHONE_REGEX = Regex("""(\+?\d[\d\s\-().]{7,}\d)""")
    private val DATE_REGEX = Regex(
        """\b(\d{1,2}\s+(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[a-z]*\.?\s+\d{4}|(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[a-z]*\.?\s+\d{1,2},?\s+\d{4}|\d{1,2}[/-]\d{1,2}[/-]\d{2,4})\b""",
        RegexOption.IGNORE_CASE
    )
    private val CURRENCY_REGEX = Regex("""[RZ$£€]\s*\d+[\d,.]*|\d+[\d,.]*\s*(USD|ZAR|BWP|EUR|GBP)""", RegexOption.IGNORE_CASE)

    fun analyze(text: String, correlationId: String? = null): StructuredDocument {
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("DocumentStructureAnalyzer", mapOf(
            "textLength" to text.length,
            "correlationId" to correlationId
        ))

        val lines = text.lines()
        val pageCount = detectPageCount(lines)

        val pages = splitIntoPages(lines, pageCount)

        val blocks = classifyBlocks(lines)

        val annotatedBlocks = blocks.map { block ->
            val metadata = extractMetadata(block.text)
            block.copy(metadata = metadata)
        }

        val strippedBlocks = stripPageChrome(annotatedBlocks)

        val document = StructuredDocument(
            pages = pages,
            documentType = DocumentType.CV,
            blocks = strippedBlocks
        )

        val durationMs = System.currentTimeMillis() - startMs
        PipelineTrace.exit("DocumentStructureAnalyzer", durationMs, mapOf(
            "blocks" to document.blocks.size,
            "pages" to document.pages.size,
            "correlationId" to correlationId
        ))

        return document
    }

    private fun detectPageCount(lines: List<String>): Int {
        val pageMarkers = lines.count { line ->
            Regex("""\bpage\s+\d+""", RegexOption.IGNORE_CASE).containsMatchIn(line)
        }
        return maxOf(1, pageMarkers)
    }

    private fun splitIntoPages(lines: List<String>, pageCount: Int): List<Page> {
        if (pageCount <= 1) {
            return listOf(Page(number = 1, text = lines.joinToString("\n")))
        }
        val pages = mutableListOf<Page>()
        val currentLines = mutableListOf<String>()
        var pageNum = 1

        for (line in lines) {
            val pageMatch = Regex("""\bpage\s+(\d+)""", RegexOption.IGNORE_CASE).find(line)
            if (pageMatch != null && currentLines.isNotEmpty()) {
                pages.add(Page(number = pageNum, text = currentLines.joinToString("\n")))
                currentLines.clear()
                pageNum = pageMatch.groupValues[1].toIntOrNull() ?: (pageNum + 1)
            }
            currentLines.add(line)
        }
        if (currentLines.isNotEmpty()) {
            pages.add(Page(number = pageNum, text = currentLines.joinToString("\n")))
        }
        return pages
    }

    private fun classifyBlocks(lines: List<String>): List<DocumentBlock> {
        val blocks = mutableListOf<DocumentBlock>()
        var currentLines = mutableListOf<String>()
        var currentType = BlockType.UNKNOWN

        fun flushBlock() {
            if (currentLines.isEmpty()) return
            val text = currentLines.joinToString("\n")
            if (text.isBlank()) return
            blocks.add(
                DocumentBlock(
                    id = UUID.randomUUID().toString().take(8),
                    type = currentType,
                    text = text,
                    page = 1,
                    confidence = if (currentType == BlockType.UNKNOWN) 0.5f else 0.8f
                )
            )
            currentLines = mutableListOf()
            currentType = BlockType.UNKNOWN
        }

        for (line in lines) {
            if (line.isBlank()) {
                flushBlock()
                continue
            }

            val trimmed = line.trim()
            val lower = trimmed.lowercase(Locale.ROOT)

            val isHeading = SECTION_HEADER_PATTERNS.any { lower.startsWith(it) } ||
                    (trimmed.length < 60 && trimmed.all { it.isUpperCase() || it.isWhitespace() || it.isDigit() || it in "/," }) ||
                    SECTION_HEADER_PATTERNS.any { lower.contains(it) }

            val isList = LIST_INDICATORS.any { it.containsMatchIn(trimmed) }

            val isHeader = HEADER_FOOTER_BLACKLIST.any { lower.contains(it) }

            val newType = when {
                isHeading -> BlockType.HEADING
                isList -> BlockType.LIST
                isHeader -> BlockType.HEADER
                trimmed.count { it == '|' } >= 2 -> BlockType.TABLE
                else -> BlockType.PARAGRAPH
            }

            if (newType != currentType && currentLines.isNotEmpty()) {
                flushBlock()
            }
            currentType = newType
            currentLines.add(trimmed)
        }
        flushBlock()

        return blocks
    }

    private fun extractMetadata(text: String): BlockMetadata {
        val emails = EMAIL_REGEX.findAll(text).map { it.value }.toList()
        val phones = PHONE_REGEX.findAll(text).map { it.value }.toList()
        val dates = DATE_REGEX.findAll(text).map { it.value }.toList()
        val currencies = CURRENCY_REGEX.findAll(text).map { it.value }.toList()

        return BlockMetadata(
            detectedEmails = emails,
            detectedPhoneNumbers = phones,
            detectedDates = dates,
            detectedCurrencyValues = currencies
        )
    }

    private fun stripPageChrome(blocks: List<DocumentBlock>): List<DocumentBlock> {
        val headerFooterIndices = mutableSetOf<Int>()

        blocks.forEachIndexed { idx, block ->
            val lower = block.text.lowercase(Locale.ROOT)
            val isChrome = HEADER_FOOTER_BLACKLIST.any { lower.contains(it) } &&
                    (block.type == BlockType.HEADER || block.type == BlockType.FOOTER)
            if (isChrome) {
                headerFooterIndices.add(idx)
            }
        }

        return blocks.filterIndexed { idx, _ -> idx !in headerFooterIndices }
    }
}
