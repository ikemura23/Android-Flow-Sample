package com.ikemura.android_flow_sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface NewsRepository {
    fun observeFavoriteLatestNews(): Flow<List<String>>
}

class NewsRepositoryImpl : NewsRepository {

    override fun observeFavoriteLatestNews(): Flow<List<String>> = flow {
        delay(2000)
        emit(listOf("a", "b", "c", "d"))
    }
}
