package com.devmmurray.guardiannews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.news_item.view.*

class NewsHolder(view: View) : RecyclerView.ViewHolder(view) {
    var sectionName: TextView = view.section_name
    var webTitle: TextView = view.newsTitle

    fun bind(data: Data) {
        sectionName.text = data.sectionName
        webTitle.text = data.title
    }
}

class NewsAdapter(private var dataList: ArrayList<Data>) : RecyclerView.Adapter<NewsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsHolder(view)
    }

    override fun getItemCount() =
        if (dataList.isNotEmpty()) dataList.size else 1


    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun loadNewData(newData: ArrayList<Data>) {
        dataList = newData
        notifyDataSetChanged()
    }
}