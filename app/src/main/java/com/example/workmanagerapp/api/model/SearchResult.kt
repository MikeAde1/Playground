package com.adyen.android.assignment.api.model

data class SearchResult(
    val address: SearchAddress,
    val boundingBox: BoundingBox,
    val dataSources: SearchDataSources,
    val entityType: String,
    val id: String,
    val position: Position,
    val score: Double,
    val type: String,
    val viewport: SearchViewport
)