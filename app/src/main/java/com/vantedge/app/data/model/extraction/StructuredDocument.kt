package com.vantedge.app.data.model.extraction

import com.vantedge.app.data.model.DocumentType

data class StructuredDocument(
    val pages: List<Page>,
    val documentType: DocumentType,
    val blocks: List<DocumentBlock>
)
