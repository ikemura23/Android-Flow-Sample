package com.ikemura.android_flow_sample.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ikemura.android_flow_sample.R
import com.ikemura.android_flow_sample.SuggestUiModel

class NewsItemRecyclerViewAdapter : RecyclerView.Adapter<NewsItemRecyclerViewAdapter.ViewHolder>() {

    private val uiModels = mutableListOf<SuggestUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: SuggestUiModel = uiModels[position]
        holder.idView.text = item.id
        holder.contentView.text = item.name
    }

    override fun getItemCount(): Int = uiModels.size

    fun refresh(suggestUiModels: List<SuggestUiModel>) {
        uiModels.clear()
        uiModels.addAll(suggestUiModels)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}
