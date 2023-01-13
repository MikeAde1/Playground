package com.adyen.android.assignment.api.model

data class Summary(
    val fuzzyLevel: Int,
    val geoBias: GeoBias,
    val numResults: Int,
    val offset: Int,
    val queryTime: Int,
    val queryType: String,
    val totalResults: Int
)