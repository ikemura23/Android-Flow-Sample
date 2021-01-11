package com.ikemura.android_flow_sample

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * UIから参照されるViewModel
 */
class NewsSearchListViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val searchText = MutableLiveData("")

    // val suggestList: LiveData<List<SuggestUiModel>> = searchText.map {
    //     val model = SuggestUiModel(
    //         name = it,
    //         id = it
    //     )
    //     listOf(model, model, model)
    //
    // }

    @ExperimentalCoroutinesApi
    val suggestList: LiveData<List<SuggestUiModel>> = searchText.asFlow()
        .distinctUntilChanged()
        // .debounce(1000L)
        .searchWithCustomDebounce(delayMillis = 1000L)
        .asLiveData()

    @ExperimentalCoroutinesApi
    private fun Flow<String>.searchWithCustomDebounce(delayMillis: Long): Flow<List<SuggestUiModel>> {
        var lastEmissionTime = 0L
        return mapLatest { searchText ->
            Log.d(TAG, "searchText: $searchText")
            if (searchText.isNotBlank()) {
                val interval = SystemClock.uptimeMillis() - lastEmissionTime
                if (interval < delayMillis) {
                    delay(delayMillis - interval)
                }
                lastEmissionTime = SystemClock.uptimeMillis()
                newsRepository.getNews().map { it.toUiModel() }
            } else {
                emptyList()
            }
        }
    }

    private fun String.toUiModel() = SuggestUiModel(
        name = this,
        id = getNowDate()
    )

    /**
     * 現在日時をyyyy/MM/dd HH:mm:ssで取得
     */
    private fun getNowDate(): String {
        val date = Date(System.currentTimeMillis())
        return SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN).format(date)
    }

    companion object {
        private val TAG = NewsSearchListViewModel::class.java.simpleName
    }
}

data class SuggestUiModel(
    val name: String,
    val id: String
)
