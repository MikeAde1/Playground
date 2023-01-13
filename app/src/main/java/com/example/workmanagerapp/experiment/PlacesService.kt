package com.example.workmanagerapp.experiment

import com.example.workmanagerapp.api.model.SearchResponse
import com.adyen.android.assignment.api.model.VenuesResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


interface PlacesService {
    /**
     * Get venue recommendations.
     */

    @GET("search/{versionNumber}/nearbySearch/.{ext}")
    suspend fun getVenueRecommendations(
        @Path("versionNumber") versionNumber: Int?,
        @Path("ext") ext: String?,
        @Query("lat") latitude: Float?,
        @Query("lon") longitude: Float?,
        @Query("key") key: String?
    ): VenuesResponse

    @GET("search/{versionNumber}/nearbySearch/.{ext}")
    fun getRxVenueRecommendations(
        @Path("versionNumber") versionNumber: Int?,
        @Path("ext") ext: String?,
        @Query("lat") latitude: Float?,
        @Query("lon") longitude: Float?,
        @Query("key") key: String?
    ): Single<VenuesResponse>

    @GET("search/{versionNumber}/search/{query}.{ext}")
    suspend fun searchVenue(
        @Path("versionNumber") versionNumber: Int?,
        @Path("ext") ext: String?,
        @Query("query") query: String,
        @Query("key") key: String?
    ): SearchResponse

    @GET("search/{versionNumber}/search/{query}.{ext}")
    fun searchRxVenue(
        @Path("versionNumber") versionNumber: Int?,
        @Path("ext") ext: String?,
        @Query("query") query: String,
        @Query("key") key: String?
    ): Single<SearchResponse>

    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://TOMTOM_BASE_URL")
                .client(getOkhttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }

        private fun getOkhttpClient(): OkHttpClient {
            val clientBuilder = OkHttpClient.Builder().apply {
                /*val loggingInterceptor = HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
                addInterceptor(loggingInterceptor)*/
            }
            return clientBuilder.build()
        }

        val instance: PlacesService by lazy { retrofit.create(PlacesService::class.java) }
    }
}
