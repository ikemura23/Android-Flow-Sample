package com.ikemura.android_flow_sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface NewsRepository {
    fun observeFavoriteLatestNews(): Flow<List<String>>
    suspend fun getNews(): List<String>
}

class NewsRepositoryImpl : NewsRepository {

    override fun observeFavoriteLatestNews(): Flow<List<String>> = flow {
        delay(2000)
        emit(listOf("a", "b", "c", "d"))
    }

    override suspend fun getNews(): List<String> {
        return listOf("aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg")
    }
}

class FakeNewsRepositoryImpl : NewsRepository {
    override fun observeFavoriteLatestNews(): Flow<List<String>> = flow {
        delay(2000)
        emit(listOf("a", "b", "c", "d"))
    }

    override suspend fun getNews(): List<String> {
        return listOf("aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg")
    }
}
