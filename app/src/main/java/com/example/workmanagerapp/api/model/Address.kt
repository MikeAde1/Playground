package com.adyen.android.assignment.api.model

data class Address(
    val country: String,
    val countryCode: String,
    val countryCodeISO3: String,
    val countrySecondarySubdivision: String,
    val countrySubdivision: String,
    val countrySubdivisionName: String,
    val extendedPostalCode: String,
    val freeformAddress: String,
    val localName: String,
    val municipality: String,
    val municipalitySubdivision: String,
    val postalCode: String,
    val streetName: String,
    val streetNumber: String
)