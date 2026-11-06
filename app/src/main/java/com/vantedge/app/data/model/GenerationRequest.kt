package com.vantedge.app.data.model

/**
 * Which artifact(s) the user asked Quick Generate to produce.
 * Independent of [GenerationMode] (which is the workflow) and not persisted.
 */
enum class GenerationRequest { CV_ONLY, COVER_LETTER_ONLY, BOTH }
