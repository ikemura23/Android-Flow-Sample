package com.ikemura.android_flow_sample

import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat

class NewsViewModelTest : TestCase() {
    private lateinit var testTarget: NewsViewModel

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()
    // https://developer.android.com/kotlin/flow/test?hl=ja
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val TEST_SUCCESS = LatestNewsUiState.Success(listOf("a", "b", "c", "d"))

    @ExperimentalCoroutinesApi
    public override fun setUp() {
        super.setUp()
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @ExperimentalCoroutinesApi
    override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @ExperimentalCoroutinesApi
    fun testGetUiState() {
        coroutineDispatcher.runBlockingTest {
            val fakeNewsRepositoryImpl = FakeNewsRepositoryImpl()
            testTarget = NewsViewModel(fakeNewsRepositoryImpl)

            testTarget.uiState.value?.let {
                assertThat(it).isEqualTo(TEST_SUCCESS)
            }
        }
    }
}
