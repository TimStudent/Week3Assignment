package com.example.week3assignment.okhttp

import android.content.ContentValues.TAG
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.week3assignment.adapter.SongAdapter
import com.example.week3assignment.model.SongData
import com.example.week3assignment.model.SongViewModel
import okhttp3.*
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException
import java.lang.Exception


object OkHttp {

    private val adapter by lazy {
        SongAdapter {
        }
    }

    private lateinit var mSongViewModel: SongViewModel
    private var url = ""
    private var jsonString = ""
    var trackName = "Sample Name"
    var artistName = ""
    var trackPrice = 0.0
    var previewUrl = ""


    fun requestURL(urlLink: String) {

        url = urlLink
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    jsonString = response.body!!.string()
                    println(jsonString)

                    val jsonObject = JSONTokener(jsonString).nextValue() as JSONObject
                    val jsonArray = jsonObject.getJSONArray("results")
                    try {
                        for (i in 0 until jsonArray.length()) {
                            if (jsonArray.getJSONObject(i).has("trackName")) {
                                trackName = jsonArray.getJSONObject(i).getString("trackName")
                                Log.d(TAG, "$i: $trackName")
                            }
                            if (jsonArray.getJSONObject(i).has("artistName")) {
                                artistName = jsonArray.getJSONObject(i).getString("artistName")
                                Log.d(TAG, "$i: $artistName")
                            }
                            if (jsonArray.getJSONObject(i).has("trackPrice")) {
                                trackPrice = jsonArray.getJSONObject(i).getDouble("trackPrice")
                                Log.d(TAG, "$i: $trackPrice")
                            }
                            if (jsonArray.getJSONObject(i).has("previewUrl")){
                                previewUrl = jsonArray.getJSONObject(i).getString("previewUrl")
                                Log.d(TAG, "$i $previewUrl")
                            }
                            insertDataToDatabase()
                        }

                    } catch (e: Exception) {

                    }
                }
            }
        })
    }

    fun insertDataToDatabase() {
            val song = SongData(
                0,
                trackName,
                artistName,
                trackPrice,
                previewUrl
            )

            mSongViewModel.addSong(song)

    }
}