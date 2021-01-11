package com.ikemura.android_flow_sample.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikemura.android_flow_sample.NewsRepositoryImpl
import com.ikemura.android_flow_sample.NewsSearchListViewModel
import com.ikemura.android_flow_sample.R
import com.ikemura.android_flow_sample.databinding.NewsSearchListFragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

class NewsSearchListFragment : Fragment() {

    private lateinit var binding: NewsSearchListFragmentBinding
    private val newsViewModel = NewsSearchListViewModel(NewsRepositoryImpl()) // 仮

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.news_search_list_fragment, container, false)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listAdapter = NewsItemRecyclerViewAdapter()
        with(binding) {
            binding.viewModel = newsViewModel
            binding.lifecycleOwner = viewLifecycleOwner

            list.layoutManager = LinearLayoutManager(context)
            list.adapter = listAdapter
        }
        newsViewModel.suggestList.observe(viewLifecycleOwner) { suggestUiModels ->
            Log.d(TAG, "suggestUiModels: $suggestUiModels")
            listAdapter.refresh(suggestUiModels)
        }
    }

    companion object {
        private val TAG = NewsSearchListFragment::class.java.simpleName
    }
}
