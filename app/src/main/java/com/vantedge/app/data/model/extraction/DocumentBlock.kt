package com.vantedge.app.data.model.extraction

data class DocumentBlock(
    val id: String,
    val type: BlockType,
    val text: String,
    val page: Int,
    val confidence: Float,
    val metadata: BlockMetadata = BlockMetadata()
)
