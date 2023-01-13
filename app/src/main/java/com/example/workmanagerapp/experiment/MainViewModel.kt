package com.example.workmanagerapp.experiment

import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workmanagerapp.api.model.Resource
import com.adyen.android.assignment.api.model.Result
import com.adyen.android.assignment.api.model.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: VenueRepository): ViewModel() {

    var locationIntentRequest: IntentSenderRequest? = null
    private val _venuesResult = MutableLiveData<Resource<List<Result>>>()
    private val _searchResult = MutableLiveData<Resource<List<SearchResult>>>()

    val venuesResult: LiveData<Resource<List<Result>>>
        get() = _venuesResult

    val searchResult: LiveData<Resource<List<SearchResult>>>
        get() = _searchResult

    private fun fetchVenues(latitude: Float?, longitude: Float) {
        _venuesResult.value = Resource.loading()
        viewModelScope.launch {
            val response = repository.fetchVenues(
                latitude,
                longitude,
                "BuildConfig.MAPS_API_KEY"
            )

            if (response.status == true) {
                _venuesResult.postValue(Resource.success(response.results))
            } else {
                _venuesResult.postValue(Resource.error(response.message))
            }
        }
    }

    fun searchVenue(query: String) {
        _searchResult.value = Resource.loading()
        viewModelScope.launch (Dispatchers.Default){
            val response = repository.searchVenue(
                query,
                "BuildConfig.MAPS_API_KEY"
            )

            if (response.status == true) {
                _searchResult.postValue(Resource.success(response.results))
            } else {
                _searchResult.postValue(Resource.error(response.message))
            }
        }
    }

    fun setLocationRequest(request: IntentSenderRequest?) {
        locationIntentRequest = request
    }

    fun updateLocation(latitude: Double?, longitude: Double?) {
        if (latitude != null && longitude != null) {
            fetchVenues(latitude.toFloat(), longitude.toFloat())
        }
    }
}

