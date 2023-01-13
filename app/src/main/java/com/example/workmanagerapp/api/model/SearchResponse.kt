package com.example.workmanagerapp.api.model

import com.adyen.android.assignment.api.model.SearchResult
import com.adyen.android.assignment.api.model.SearchSummary

data class SearchResponse(
    val message: String? = null,
    val status: Boolean? = true,
    val results: List<SearchResult>? = null,
    val summary: SearchSummary? = null
) {
    override fun toString(): String {
        return "SearchResponse(message = $message, status = $status)"
    }
}