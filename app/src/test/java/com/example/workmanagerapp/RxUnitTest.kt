package com.example.workmanagerapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.example.workmanagerapp.api.model.Resource
import com.adyen.android.assignment.api.model.SearchResult
import com.example.workmanagerapp.api.model.SearchResponse
import com.example.workmanagerapp.experiment.RxViewModel
import com.example.workmanagerapp.experiment.VenueRepository
import com.example.workmanagerapp.experiment.VenueRepositoryImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RxUnitTest {

    @get:Rule
    var rxRule = RxSchedulersOverrideRule()

    @get:Rule
    var liveDataRule = InstantTaskExecutorRule()

    var venueRepo = mock<VenueRepository>()

    private lateinit var viewModel: RxViewModel

    private val liveDataObserver = mock<Observer<Any>>()

    /*var searchResponse = SearchResponse(
        val message: String? = null,
    val status: Boolean? = true,
    val results: List<SearchResult>? = null,
    val summary: SearchSummary? = null
    )*/

    var searchResponse = SearchResponse(
        message = "An error occurred. Please try again",
        status = false,
        results = listOf<SearchResult>()
    )

    var searchSuccessResponse = SearchResponse(
        message = "Success",
        status = true,
        results = listOf<SearchResult>()
    )

    private  var savedStateHandle: SavedStateHandle = SavedStateHandle()

    @Before
    fun init() {
        viewModel = RxViewModel(savedStateHandle, venueRepo)
    }

    @Test
    fun `throw error when venues search request is not successful` () {
        whenever(venueRepo.searchRxVenue("query", "BuildConfig.MAPS_API_KEY")).thenReturn(Single.just(searchResponse))
        viewModel.searchResult.observeForever(liveDataObserver)
        viewModel.searchVenue("query")
        verify(venueRepo).searchRxVenue("query", "BuildConfig.MAPS_API_KEY")
        verify(liveDataObserver).onChanged(Resource.error(msg = "An error occurred. Please try again", data = null))
    }

    @Test
    fun `show success when venues search request is successful` () {
        whenever(venueRepo.searchRxVenue("query", "BuildConfig.MAPS_API_KEY")).thenReturn(Single.just(searchSuccessResponse))
        viewModel.searchResult.observeForever(liveDataObserver)
        viewModel.searchVenue("query")
        verify(venueRepo).searchRxVenue("query", "BuildConfig.MAPS_API_KEY")
        verify(liveDataObserver).onChanged(Resource.success(data = listOf<SearchResult>()))
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class RxSchedulersOverrideRule : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
                base.evaluate()

                RxJavaPlugins.reset()
                RxAndroidPlugins.reset()
            }
        }
    }
}