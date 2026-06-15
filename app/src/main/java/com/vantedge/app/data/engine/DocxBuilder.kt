package com.vantedge.app.data.engine

import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object DocxBuilder {

    fun build(plainText: String): ByteArray {
        val lines = plainText.split("\n")
        val paragraphs = lines.joinToString("") { line ->
            val trimmed = line.trim()
            when {
                trimmed.isEmpty() -> "<w:p/>"
                trimmed == trimmed.uppercase() && trimmed.length > 3 -> {
                    // ALL CAPS = section header
                    """<w:p><w:pPr><w:pStyle w:val="Heading1"/></w:pPr><w:r><w:rPr><w:b/><w:sz w:val="24"/></w:rPr><w:t xml:space="preserve">${escapeXml(trimmed)}</w:t></w:r></w:p>"""
                }
                trimmed.startsWith("- ") -> {
                    val content = processInline(trimmed.removePrefix("- "))
                    """<w:p><w:pPr><w:ind w:left="360"/></w:pPr><w:r><w:t xml:space="preserve">• </w:t></w:r>$content</w:p>"""
                }
                else -> {
                    """<w:p>${processInline(trimmed)}</w:p>"""
                }
            }
        }

        val documentXml = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<w:document xmlns:wpc="http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas"
  xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
  xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
<w:body>
$paragraphs
<w:sectPr>
  <w:pgSz w:w="12240" w:h="15840"/>
  <w:pgMar w:top="1440" w:right="1440" w:bottom="1440" w:left="1440"/>
</w:sectPr>
</w:body>
</w:document>"""

        val relsXml = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
  <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
</Relationships>"""

        val wordRelsXml = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
  <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles" Target="styles.xml"/>
</Relationships>"""

        val stylesXml = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<w:styles xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
  <w:style w:type="paragraph" w:styleId="Normal">
    <w:name w:val="Normal"/>
    <w:rPr><w:sz w:val="22"/></w:rPr>
  </w:style>
  <w:style w:type="paragraph" w:styleId="Heading1">
    <w:name w:val="heading 1"/>
    <w:rPr><w:b/><w:sz w:val="26"/></w:rPr>
  </w:style>
</w:styles>"""

        val contentTypesXml = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
  <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
  <Default Extension="xml" ContentType="application/xml"/>
  <Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/>
  <Override PartName="/word/styles.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml"/>
</Types>"""

        val baos = ByteArrayOutputStream()
        val zip = ZipOutputStream(baos)

        fun addEntry(name: String, content: String) {
            zip.putNextEntry(ZipEntry(name))
            zip.write(content.toByteArray(Charsets.UTF_8))
            zip.closeEntry()
        }

        addEntry("[Content_Types].xml", contentTypesXml)
        addEntry("_rels/.rels", relsXml)
        addEntry("word/document.xml", documentXml)
        addEntry("word/_rels/document.xml.rels", wordRelsXml)
        addEntry("word/styles.xml", stylesXml)

        zip.finish()
        return baos.toByteArray()
    }

    private fun processInline(text: String): String {
        val parts = text.split("**")
        return parts.mapIndexed { index, part ->
            if (part.isEmpty()) ""
            else if (index % 2 == 1) {
                // Bold
                """<w:r><w:rPr><w:b/></w:rPr><w:t xml:space="preserve">${escapeXml(part)}</w:t></w:r>"""
            } else {
                """<w:r><w:t xml:space="preserve">${escapeXml(part)}</w:t></w:r>"""
            }
        }.joinToString("")
    }

    private fun escapeXml(text: String): String {
        return text
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;")
    }
}