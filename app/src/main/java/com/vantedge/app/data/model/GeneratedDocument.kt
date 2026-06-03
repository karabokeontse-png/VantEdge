package com.vantedge.app.data.model

data class GeneratedDocument(
    val id: String,
    val jobTitle: String,
    val company: String,
    val content: String,
    val score: Int,
    val date: Long = System.currentTimeMillis()
)