package com.adyen.android.assignment.api.model

data class VenuesResponse(
    val message: String? = null,
    val status: Boolean? = true,
    val results: List<Result>? = null,
    val summary: Summary? = null
) {
    override fun toString(): String {
        return "VenueResponse(message = $message, status = $status)"
    }
}