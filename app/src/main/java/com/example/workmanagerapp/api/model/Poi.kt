package com.adyen.android.assignment.api.model

data class Poi(
    val categories: List<String>,
    val categorySet: List<CategorySet>,
    val classifications: List<Classification>,
    val name: String,
    val phone: String,
    val url: String
)