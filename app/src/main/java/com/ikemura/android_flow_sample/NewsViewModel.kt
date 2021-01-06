package com.ikemura.android_flow_sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * UIから参照されるViewModel
 */
class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LatestNewsUiState?>(null)
    val uiState: StateFlow<LatestNewsUiState?> = _uiState

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
