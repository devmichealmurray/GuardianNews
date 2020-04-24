package com.devmmurray.guardiannews

import android.os.AsyncTask

class GetJsonData(private val listener: OnDataAvailable)
    : AsyncTask<String, Void, ArrayList<Data>>() {

    override fun doInBackground(vararg params: String): ArrayList<Data> {
        val jsonData = getUrlAsString(params[0])
        return parseJson(jsonData)
    }

    override fun onPostExecute(result: ArrayList<Data>) {
        listener.onDataAvailable(result)
    }
}