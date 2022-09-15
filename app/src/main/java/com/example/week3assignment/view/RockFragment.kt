package com.example.week3assignment.view

import android.content.ContentValues.TAG
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
import com.example.week3assignment.databinding.FragmentRockBinding
import com.example.week3assignment.model.SongData
import com.example.week3assignment.model.SongViewModel
import com.example.week3assignment.okhttp.OkHttp
import okhttp3.*
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException

class RockFragment : Fragment() {

    private val binding by lazy {
        FragmentRockBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        SongAdapter{

        }
    }
    private val okhttp by lazy {
        OkHttp
    }
    private lateinit var mSongViewModel: SongViewModel
    private var songName = "Story of My Life"
    private var songArtist = "One Direction"
    private var songPrice = 1.29
    private var previewURL = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview125/v4/ce/32/81/ce32816e-b9e8-e9a8-94bf-40e4f99e3596/mzaf_2990346819158497013.plus.aac.p.m4a"
    private var songGenreName = "Rock"

    //
    private var url = ""
    private var jsonString = ""
    //


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //okhttp.requestURL("https://itunes.apple.com/search?term=rock&amp;media=music&amp;entity=song&amp;limit=50")
        val recyclerView = binding.eventRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mSongViewModel = ViewModelProvider(this)[SongViewModel::class.java]
        mSongViewModel.readRock.observe(viewLifecycleOwner, Observer { song -> adapter.update(song)})


        ////
        url = "https://itunes.apple.com/search?term=rock&amp;media=music&amp;entity=song&amp;limit=50"
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
                                    Log.d(TAG, "$i: $songName")
                                }
                                if (jsonArray.getJSONObject(i).has("artistName")) {
                                    songArtist = jsonArray.getJSONObject(i).getString("artistName")
                                    Log.d(TAG, "$i: $songArtist")
                                }
                                if (jsonArray.getJSONObject(i).has("trackPrice")) {
                                    songPrice = jsonArray.getJSONObject(i).getDouble("trackPrice")
                                    Log.d(TAG, "$i: $songPrice")
                                }
                                if (jsonArray.getJSONObject(i).has("previewUrl")) {
                                    previewURL = jsonArray.getJSONObject(i).getString("previewUrl")
                                    Log.d(TAG, "$i $previewURL")
                                }

                                val song = SongData(
                                    0,
                                    songName,
                                    songArtist,
                                    songPrice,
                                    previewURL,
                                    songGenreName
                                )
                                mSongViewModel.addSong(song)
                            }

                        } catch (e: Exception) {

                        }
                    }
                }
            }
        })

        binding.musicPlayerBtn.setOnClickListener{

        }
        return binding.root
    }
}