package com.adyen.android.assignment.api.model

data class Result(
    val address: Address,
    val dataSources: DataSources,
    val dist: Double,
    val entryPoints: List<EntryPoint>,
    val id: String,
    val info: String,
    val poi: Poi,
    val position: PositionX,
    val score: Double,
    val type: String,
    val viewport: Viewport
)