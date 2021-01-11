package com.ikemura.android_flow_sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ikemura.android_flow_sample.databinding.ActivityMainBinding
import com.ikemura.android_flow_sample.search.NewsSearchListFragment
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val newsViewModel = NewsViewModel(NewsRepositoryImpl()) // 仮

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(binding.container.id, NewsSearchListFragment())
            .commitNow()

        // startFlow()
    }

    private fun startFlow() {

        lifecycleScope.launchWhenCreated {
            newsViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is LatestNewsUiState.Success -> showFavoriteNews(uiState.news)
                    is LatestNewsUiState.Error -> showError(uiState.exception)
                }
            }
        }
    }

    private fun showFavoriteNews(news: List<String>) {
        Toast.makeText(this, news.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun showError(exception: Throwable) {
        Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
    }
}
