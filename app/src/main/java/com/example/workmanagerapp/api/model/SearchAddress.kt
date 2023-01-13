package com.adyen.android.assignment.api.model

data class SearchAddress(
    val country: String,
    val countryCode: String,
    val countryCodeISO3: String,
    val countrySecondarySubdivision: String,
    val countrySubdivision: String,
    val countrySubdivisionCode: String,
    val freeformAddress: String,
    val municipality: String
)