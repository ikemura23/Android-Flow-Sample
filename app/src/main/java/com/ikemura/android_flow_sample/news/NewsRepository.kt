package com.ikemura.android_flow_sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface NewsRepository {
    fun observeFavoriteLatestNews(): Flow<List<String>>
    suspend fun getNews(searchText: String): List<String>
}

class NewsRepositoryImpl : NewsRepository {

    override fun observeFavoriteLatestNews(): Flow<List<String>> = flow {
        delay(2000)
        emit(listOf("a", "b", "c", "d"))
    }

    override suspend fun getNews(searchText: String): List<String> {
        delay(2000)
        return listOf("1:$searchText", "2:$searchText", "3:$searchText", "4:$searchText", "5:$searchText")
    }
}

class FakeNewsRepositoryImpl : NewsRepository {
    override fun observeFavoriteLatestNews(): Flow<List<String>> = flow {
        delay(2000)
        emit(listOf("a", "b", "c", "d"))
    }

    override suspend fun getNews(searchText: String): List<String> {
        delay(2000)
        return listOf("1:$searchText", "2:$searchText", "3:$searchText", "4:$searchText", "5:$searchText")
    }
}
