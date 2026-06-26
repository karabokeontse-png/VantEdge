package com.vantedge.app.data.model

data class RelevancyItem(
    val name: String,
    val type: String,
    val matchPercent: Int,
    val aiDescription: String,
    val relevancyGroup: String
)
