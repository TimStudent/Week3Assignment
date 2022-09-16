package com.example.week3assignment.view

import android.content.ContentValues
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
    private var previewURL = ""
    private var songGenreName = "Rock"
    private var pictureLink = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/a98cff5d-a612-49d8-a0db-175994384b20/dce3hw4-2f32764d-3222-4116-84a4-6ff82576b31d.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcL2E5OGNmZjVkLWE2MTItNDlkOC1hMGRiLTE3NTk5NDM4NGIyMFwvZGNlM2h3NC0yZjMyNzY0ZC0zMjIyLTQxMTYtODRhNC02ZmY4MjU3NmIzMWQucG5nIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.NpLyS3jAEZtyhMyE1YKz6-DOZllTM6-12gFBeHpbQSk"

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
                                if (jsonArray.getJSONObject(i).has("artworkUrl100")) {
                                    pictureLink = jsonArray.getJSONObject(i).getString("artworkUrl100")
                                    Log.d(TAG, "$i $pictureLink")
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