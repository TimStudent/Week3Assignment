package com.example.week3assignment.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week3assignment.adapter.SongAdapter
import com.example.week3assignment.databinding.FragmentClassicBinding
import com.example.week3assignment.model.SongData
import com.example.week3assignment.model.SongViewModel
import com.example.week3assignment.okhttp.OkHttp
import okhttp3.*
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException

class ClassicFragment : Fragment() {

    private val binding by lazy {
        FragmentClassicBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        SongAdapter{
        }
    }
    private val okhttp by lazy {
        OkHttp
    }
    private var songName = "Subhuman"
    private var songArtist = "Michael Bar"
    private var songPrice = 2.00
    private var previewURL = ""
    private var songGenreName = "Classic"
    private var pictureLink = ""

    private var jsonString = ""
    private lateinit var mSongViewModel: SongViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //okhttp.requestURL("https://itunes.apple.com/search?term=classic&amp;media=music&amp;entity=song&amp;limit=50")
        val recyclerView = binding.eventRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mSongViewModel = ViewModelProvider(this)[SongViewModel::class.java]
        mSongViewModel.readClassic.observe(viewLifecycleOwner, Observer { song -> adapter.update(song)})

        val url = "https://itunes.apple.com/search?term=classic&amp;media=music&amp;entity=song&amp;limit=50"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (adapter.itemCount == 0) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        jsonString = response.body!!.string()
                        println(jsonString)

                        val jsonObject = JSONTokener(jsonString).nextValue() as JSONObject
                        val jsonArray = jsonObject.getJSONArray("results")
                        try {
                            for (i in 0 until jsonArray.length()) {
                                if (jsonArray.getJSONObject(i).has("trackName")) {
                                    songName = jsonArray.getJSONObject(i).getString("trackName")
                                    Log.d(ContentValues.TAG, "$i: $songName")
                                }
                                if (jsonArray.getJSONObject(i).has("artistName")) {
                                    songArtist = jsonArray.getJSONObject(i).getString("artistName")
                                    Log.d(ContentValues.TAG, "$i: $songArtist")
                                }
                                if (jsonArray.getJSONObject(i).has("trackPrice")) {
                                    songPrice = jsonArray.getJSONObject(i).getDouble("trackPrice")
                                    Log.d(ContentValues.TAG, "$i: $songPrice")
                                }
                                if (jsonArray.getJSONObject(i).has("previewUrl")) {
                                    previewURL = jsonArray.getJSONObject(i).getString("previewUrl")
                                    Log.d(ContentValues.TAG, "$i $previewURL")
                                }
                                if (jsonArray.getJSONObject(i).has("artworkUrl100")) {
                                    pictureLink = jsonArray.getJSONObject(i).getString("artworkUrl100")
                                    Log.d(ContentValues.TAG, "$i $pictureLink")
                                }

                                val song = SongData(
                                    0,
                                    songName,
                                    songArtist,
                                    songPrice,
                                    previewURL,
                                    songGenreName,
                                    pictureLink
                                )
                                mSongViewModel.addSong(song)
                            }

                        } catch (e: Exception) {

                        }
                    }
                }
            }
        })

        return binding.root
    }



}