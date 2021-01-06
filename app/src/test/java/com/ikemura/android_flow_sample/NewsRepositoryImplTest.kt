package com.ikemura.android_flow_sample

import junit.framework.TestCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class NewsRepositoryImplTest : TestCase() {

    private val target: NewsRepository = NewsRepositoryImpl()
    private val ALL_ITEM = listOf("a", "b", "c", "d")
    public override fun setUp() {
        super.setUp()
    }

    public override fun tearDown() {}

    @Test
    fun testObserveFavoriteLatestNews() {
        runBlocking {
            target.observeFavoriteLatestNews().collect {
                assertThat(it).isEqualTo(ALL_ITEM)
            }
        }
    }
}
