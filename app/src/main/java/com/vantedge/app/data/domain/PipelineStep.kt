package com.vantedge.app.domain

enum class PipelineStep(val label: String) {
    ANALYSING("Analysing job compatibility..."),
    GENERATING_CV("Generating your CV..."),
    GENERATING_COVER_LETTER("Writing cover letter..."),
    APPLYING_DESIGN("Applying design...")
}