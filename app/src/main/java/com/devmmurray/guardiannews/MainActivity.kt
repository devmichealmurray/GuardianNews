package com.devmmurray.guardiannews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnDataAvailable {

    private val newsRecyclerViewAdapter = NewsAdapter(arrayListOf())
    private var page = 1
    private var search = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = newsRecyclerViewAdapter

        searchButton.setOnClickListener {
            search = search_edit_text.text.toString()
            searchWord(search)
        }

        loadMoreButton.setOnClickListener {
            loadMore(search)
        }
    }

    private fun searchWord(search: String) {
        page = 1
        val url = getUrl(search, page)
        val getJsonData = GetJsonData(this)
        getJsonData.execute(url)
        search_edit_text.text.clear()
        search_edit_text.clearFocus()
    }

    private fun loadMore(search: String) {
        val pageNumber = page + 1
        val url = getUrl(search, pageNumber)
        val getJsonData = GetJsonData(this)
        getJsonData.execute(url)
    }

    override fun onDataAvailable(data: ArrayList<Data>) {
        newsRecyclerViewAdapter.loadNewData(data)
    }

}
