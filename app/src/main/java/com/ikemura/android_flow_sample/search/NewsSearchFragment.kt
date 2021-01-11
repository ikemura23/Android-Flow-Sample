package com.ikemura.android_flow_sample.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ikemura.android_flow_sample.R
import com.ikemura.android_flow_sample.search.dummy.DummyContent

/**
 * A fragment representing a list of Items.
 */
class NewsSearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.news_search_list_fragment, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = NewsItemRecyclerViewAdapter(DummyContent.ITEMS)
            }
        }
        return view
    }
}
