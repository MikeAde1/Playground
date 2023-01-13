package com.example.workmanagerapp.experiment

import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.workmanagerapp.api.model.Resource
import com.adyen.android.assignment.api.model.Result
import com.adyen.android.assignment.api.model.SearchResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class RxViewModel(private val savedStateHandle: SavedStateHandle,
                  private val repository: VenueRepository): ViewModel() {
    var locationIntentRequest: IntentSenderRequest? = null
    //private var repository = VenueRepository()
    private val progressLiveData = MutableLiveData<Boolean>()
    private val _venuesResult = MutableLiveData<Resource<List<Result>>>()
    private val _searchResult = MutableLiveData<Resource<List<SearchResult>>>()
    private val compositeDisposable = CompositeDisposable()

    val venuesResult: LiveData<Resource<List<Result>>>
        get() = _venuesResult

    val searchResult: LiveData<Resource<List<SearchResult>>>
        get() = _searchResult

    private fun fetchVenues(latitude: Float?, longitude: Float) {
        compositeDisposable.add(
             repository.fetchRxVenues(
                 latitude,
                 longitude,
                 "BuildConfig.MAPS_API_KEY"
             ).subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .applyProgressBar(progressLiveData)
                 .subscribeBy(
                    onSuccess = {
                        if (it.status == true) {
                            _venuesResult.value = (Resource.success(it.results))
                        } else {
                            _venuesResult.value = (Resource.error(it.message))
                        }
                    }, onError = {
                         _venuesResult.value = Resource.error(it.message)
                    }
                )
        )
    }

    fun searchVenue(query: String) {
        compositeDisposable.add(
            repository.searchRxVenue(
                query,
                "BuildConfig.MAPS_API_KEY"
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .applyProgressBar(progressLiveData)
                .subscribeBy(
                    onSuccess = {
                        if (it.status == true) {
                            _searchResult.value = Resource.success(it.results)
                        } else {
                            _searchResult.value = Resource.error(it.message)
                        }
                    }, onError = {
                        _searchResult.value = Resource.error(it.message)
                    }
                )
        )
    }


    fun updateLocation(latitude: Double?, longitude: Double?) {
        if (latitude != null && longitude != null) {
            fetchVenues(latitude.toFloat(), longitude.toFloat())
        }
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}

fun <T> Single<T>.applyProgressBar(progressLiveData: MutableLiveData<Boolean>) =
    doOnSubscribe { progressLiveData.value = true }
        .doOnEvent { t1, t2 -> progressLiveData.value = false }