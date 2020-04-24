package com.devmmurray.guardiannews

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

@Throws(IOException::class)
internal fun getUrlAsString(urlAddress: String): String {
    val url = URL(urlAddress)
    val conn = url.openConnection() as HttpsURLConnection
    conn.requestMethod = "GET"
    conn.setRequestProperty("Accept", "application/json")
    conn.setRequestProperty("api-key", "84cf9d88-bc11-4946-9816-b93b79d1a236")
    var string = ""
    try {
        val inputStream = conn.inputStream
        if (conn.responseCode != HttpsURLConnection.HTTP_OK) {
            throw IOException("${conn.responseMessage} for $urlAddress")
        }
        string = if (inputStream != null) {
            convertStreamToString(inputStream)
        } else {
            "Error retrieving $urlAddress"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("GetUrlAsString", "*** Error *** ${e.message}")
    } finally {
        conn.disconnect()
    }
    return string
}

@Throws(IOException::class)
private fun convertStreamToString(inputStream: InputStream): String {
    val reader = BufferedReader(InputStreamReader(inputStream))
    val sb = StringBuilder()
    var line: String? = reader.readLine()
    while (line != null) {
        sb.append(line).append("\n")
        line = reader.readLine()
    }
    reader.close()
    return sb.toString()
}

@Throws(JSONException::class)
internal fun parseJson(jsonData: String): ArrayList<Data> {
    val dataArray = arrayListOf<Data>()

    try {
        val rawJSON = JSONObject(jsonData)
        val response = rawJSON.getJSONObject("response")
        val results = response.getJSONArray("results")

        for (i in 0..9) {
            val item = results.getJSONObject(i)
            val sectionName = item.getString("sectionName")
            val webTitle = item.getString("webTitle")
            val webUrl = item.getString("webUrl")

            Log.d("ParseJSON", "$sectionName, $webTitle, $webUrl")
            dataArray.add(Data(sectionName, webTitle, webUrl))
        }

    } catch (e: JSONException) {
        e.printStackTrace()
        Log.e("parseJSON", "JSON Exception: ${e.message}")
    }

    return dataArray
}

internal fun getUrl(search: String, page: Int): String {
    val baseURL = "https://content.guardianapis.com/search?q="
    val apiKey = "&api-key=84cf9d88-bc11-4946-9816-b93b79d1a236"
    val pageNumber = "&page=$page"
//    "https://content.guardianapis.com/search?q=debate&api-key=84cf9d88-bc11-4946-9816-b93b79d1a236"
    return "$baseURL$search$apiKey$pageNumber"
}
