package com.vantedge.app.data.domain

import com.vantedge.app.data.engine.DocxBuilder
import com.vantedge.app.data.infrastructure.MediaStoreExporter
import com.vantedge.app.data.model.GenerationCycle

class DocumentExportUseCase(private val exporter: MediaStoreExporter) {

    suspend fun export(cycle: GenerationCycle): Result<ExportReceipt> {
        val cvContent = cycle.cvContent
            ?: return Result.failure(
                Exception("Export requires generated CV content, but cvContent is null")
            )

        val plainText = stripHtmlToPlainText(cvContent)
        if (plainText.isBlank()) {
            return Result.failure(Exception("CV content is empty — nothing to export"))
        }

        val docxBytes = DocxBuilder.build(plainText)
        return exporter.save(cycle.jobTitle, docxBytes).map { fileName ->
            ExportReceipt(fileName, cycle.jobTitle, cycle.id)
        }
    }

    fun stripHtmlToPlainText(html: String): String {
        return html
            .replace(Regex("</p>", RegexOption.IGNORE_CASE), "\n")
            .replace(Regex("<li[^>]*>", RegexOption.IGNORE_CASE), "- ")
            .replace(Regex("</li>", RegexOption.IGNORE_CASE), "\n")
            .replace(Regex("<[bh]r\\s*/?>", RegexOption.IGNORE_CASE), "\n")
            .replace(Regex("<b>|</b>|<strong>|</strong>", RegexOption.IGNORE_CASE), "**")
            .replace(Regex("<[^>]+>"), "")
            .replace(Regex("\n{3,}"), "\n\n")
            .trim()
    }
}
