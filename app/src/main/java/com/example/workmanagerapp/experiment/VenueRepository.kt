package com.example.workmanagerapp.experiment

import com.adyen.android.assignment.api.model.VenuesResponse
import com.example.workmanagerapp.api.model.SearchResponse
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception

class VenueRepositoryImpl: VenueRepository {
    private val venueApi = PlacesService.instance

    override suspend fun fetchVenues(latitude: Float?, longitude: Float?, mapsApiKey: String?): VenuesResponse {
        return try {
            venueApi.getVenueRecommendations(VERSION_NUMBER, EXTENSION, latitude, longitude, mapsApiKey)
        } catch (e: Exception) {
            VenuesResponse(status = false, message = e.errorMessage())
        }
    }

    override suspend fun searchVenue(query: String, mapsApiKey: String): SearchResponse {
        return try {
            venueApi.searchVenue(VERSION_NUMBER, EXTENSION, query, mapsApiKey)
        } catch (e: Exception) {
            SearchResponse(status = false, message = e.errorMessage())
        }
    }

    override fun fetchRxVenues(latitude: Float?, longitude: Float?, mapsApiKey: String?): Single<VenuesResponse> {
        return try {
            venueApi.getRxVenueRecommendations(VERSION_NUMBER, EXTENSION, latitude, longitude, mapsApiKey)
        } catch (e: Exception) {
            Single.just(VenuesResponse(status = false, message = e.errorMessage()))
        }
    }

    override fun searchRxVenue(query: String, mapsApiKey: String): Single<SearchResponse> {
        return try {
            venueApi.searchRxVenue(VERSION_NUMBER, EXTENSION, query, mapsApiKey)
        } catch (e: Exception) {
            Single.just(SearchResponse(status = false, message = e.errorMessage()))
        }
    }

    companion object {
        const val VERSION_NUMBER = 2
        const val EXTENSION = "json"
    }
}

interface VenueRepository {

    suspend fun fetchVenues(
        latitude: Float?,
        longitude: Float?,
        mapsApiKey: String?
    ): VenuesResponse

    fun fetchRxVenues(
        latitude: Float?,
        longitude: Float?,
        mapsApiKey: String?
    ): Single<VenuesResponse>

    suspend fun searchVenue(query: String, mapsApiKey: String): SearchResponse
    fun searchRxVenue(query: String, mapsApiKey: String): Single<SearchResponse>
}

fun Throwable.errorMessage(): String {
    printStackTrace()
    var response = "An error occurred. Please try again"
    //Log.e("Http Error", "$message")

    if (this is HttpException) {
        return try {
            val msg: String? = response()?.errorBody()?.string()
            response = JSONObject(msg).getString("errorText")
            //Log.e("Http Error",  "$msg")
            response
        } catch (e: Exception) {
            response
        }
    }
    return response
}