package com.example.workmanagerapp

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlin.coroutines.ContinuationInterceptor

/*@ExperimentalCoroutinesApi
class MainCoroutineRule : TestWatcher(), TestCoroutineScope by TestCoroutineScope() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }

}*/


/*@ExperimentalCoroutinesApi
class CoroutineScopeRule: TestWatcher() {
    private val testCoroutineDispatcher = UnconfinedTestDispatcher()

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testCoroutineDispatcher)
        dispatcherProvider = CoroutineDispatcherProvider(
            main = dispatcher,
            default = dispatcher,
            io = dispatcher
        )
    }
    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}*/

@ExperimentalCoroutinesApi
class CoroutineTestRule(val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())) : TestWatcher() {

  /*  val testDispatcherProvider = object : DispatcherProvider {
        override fun default(): CoroutineDispatcher = testDispatcher
        override fun io(): CoroutineDispatcher = testDispatcher
        override fun main(): CoroutineDispatcher = testDispatcher
        override fun unconfined(): CoroutineDispatcher = testDispatcher
    }*/

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

interface DispatcherProvider {

    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined

}

class DefaultDispatcherProvider : DispatcherProvider

