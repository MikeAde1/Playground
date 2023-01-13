package com.adyen.android.assignment.api.model

data class SearchSummary(
    val fuzzyLevel: Int,
    val numResults: Int,
    val offset: Int,
    val query: String,
    val queryTime: Int,
    val queryType: String,
    val totalResults: Int
)