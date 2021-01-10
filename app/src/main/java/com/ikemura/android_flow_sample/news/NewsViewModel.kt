package com.ikemura.android_flow_sample

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

/**
 * UIから参照されるViewModel
 */
class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LatestNewsUiState?>(null)
    val uiState: StateFlow<LatestNewsUiState?> = _uiState

    val searchText = MutableLiveData("")

    @ExperimentalCoroutinesApi
    val suggestList : LiveData<List<SuggestUiModel>> = searchText.asFlow()
        .distinctUntilChanged()
        .searchWithCustomDebounce(delayMillis = 1000L)
        .asLiveData()

    @ExperimentalCoroutinesApi
    private fun Flow<String>.searchWithCustomDebounce(delayMillis: Long): Flow<List<SuggestUiModel>> {
        var lastEmissionTime = 0L
        return mapLatest { searchText ->

            if (searchText.isNotBlank()) {
                val interval = SystemClock.uptimeMillis() - lastEmissionTime
                if (interval < delayMillis) {
                    delay(delayMillis - interval)
                }
                lastEmissionTime = SystemClock.uptimeMillis()

                // TODO: RepositoryでAPI呼び出し
                emptyList()
            } else {
                emptyList()
            }
        }
    }

    init {
        viewModelScope.launch {
            newsRepository.observeFavoriteLatestNews().collect { favoriteNews ->
                _uiState.value = LatestNewsUiState.Success(favoriteNews)
            }
        }
    }
}

sealed class LatestNewsUiState {
    data class Success(val news: List<String>) : LatestNewsUiState()
    data class Error(val exception: Throwable) : LatestNewsUiState()
}

sealed class SuggestUiModel {
    data class Item(
        val name: String,
        val id: String
    ) : SuggestUiModel()
}
