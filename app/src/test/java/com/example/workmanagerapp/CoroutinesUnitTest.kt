package com.example.workmanagerapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.adyen.android.assignment.api.model.SearchResult
import com.example.workmanagerapp.api.model.Resource
import com.example.workmanagerapp.api.model.SearchResponse
import com.example.workmanagerapp.experiment.MainViewModel
import com.example.workmanagerapp.experiment.RxViewModel
import com.example.workmanagerapp.experiment.VenueRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CoroutinesUnitTest {

    @get:Rule
    var liveDataRule = InstantTaskExecutorRule()


    //replace main dispatcher
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    //add this as a constructor parameter
    @ExperimentalCoroutinesApi
    private val testDispatcher = UnconfinedTestDispatcher()

    var venueRepo = mock<VenueRepository>()

    private lateinit var viewModel: MainViewModel

    private val liveDataObserver = mock<Observer<Any>>()

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

    @Before
    fun init() {
        viewModel = MainViewModel(venueRepo)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `throw error when venues search request is not successful` () = runTest(testDispatcher) {

        whenever(venueRepo.searchVenue("query", "BuildConfig.MAPS_API_KEY")).thenReturn(searchResponse)
        viewModel.searchResult.observeForever(liveDataObserver)
        viewModel.searchVenue("query")
        verify(venueRepo).searchVenue("query", "BuildConfig.MAPS_API_KEY")
        verify(liveDataObserver).onChanged(
            Resource.error(
                msg = "An error occurred. Please try again",
                data = null
            )
        )

        /*whenever(venueRepo.searchVenue("query", "BuildConfig.MAPS_API_KEY")).thenReturn(searchSuccessResponse)
        viewModel.searchResult.observeForever(liveDataObserver)
        viewModel.searchVenue("query")
        verify(venueRepo).searchVenue("query", "BuildConfig.MAPS_API_KEY")
        verify(liveDataObserver).onChanged(
            Resource.success(data = listOf<SearchResult>())
        )*/
    }

    @Test
    fun `throw error when close venues request is successful` () = runTest(testDispatcher) {
        whenever(venueRepo.searchVenue("query", "BuildConfig.MAPS_API_KEY")).thenReturn(searchSuccessResponse)
        viewModel.searchResult.observeForever(liveDataObserver)
        viewModel.searchVenue("query")
        verify(venueRepo).searchVenue("query", "BuildConfig.MAPS_API_KEY")
        verify(liveDataObserver).onChanged(
            Resource.success(data = listOf<SearchResult>())
        )
    }

    @Test
    fun `show success when venues search request is successful`()  {

    }

    @Test
    fun `show success when close venues request is successful`() {

    }
}

