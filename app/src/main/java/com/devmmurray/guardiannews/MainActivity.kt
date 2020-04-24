package com.devmmurray.guardiannews

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnDataAvailable {

    private val newsRecyclerViewAdapter = NewsAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = newsRecyclerViewAdapter

        searchButton.setOnClickListener {
            searchWord(search_edit_text)
        }
    }

    private fun searchWord(search: EditText) {
        var pageNumber = 1
        val url = getUrl(search.text.toString(), pageNumber)
        val getJsonData = GetJsonData(this)
        getJsonData.execute(url)
        search_edit_text.text.clear()
        search_edit_text.clearFocus()
    }

    override fun onDataAvailable(data: ArrayList<Data>) {
        newsRecyclerViewAdapter.loadNewData(data)
    }

}
