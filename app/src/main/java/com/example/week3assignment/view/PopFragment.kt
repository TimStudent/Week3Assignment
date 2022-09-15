package com.example.week3assignment.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week3assignment.adapter.SongAdapter
import com.example.week3assignment.databinding.FragmentPopBinding
import com.example.week3assignment.model.SongData
import com.example.week3assignment.model.SongViewModel
import com.example.week3assignment.okhttp.OkHttp

class DatabaseFragment : Fragment() {

    private val binding by lazy {
        FragmentPopBinding.inflate(layoutInflater)
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

    private lateinit var mSongViewModel: SongViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        okhttp.requestURL("https://itunes.apple.com/search?term=pop&amp;media=music&amp;entity=song&amp;limit=50")
        val recyclerView = binding.eventRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mSongViewModel = ViewModelProvider(this)[SongViewModel::class.java]
        mSongViewModel.readAllData.observe(viewLifecycleOwner, Observer { song -> adapter.update(song)})
        binding.SaveBtn.setOnClickListener {
//            insertDataToDatabase()
        }
        return binding.root
    }

    private fun loadDataFromGson(){
        //get a json from link and then unload song name, song artist, and song price
        //and then set them as the values for the private vars
    }
    private fun insertDataToDatabase() {
            val song = SongData(0,
                okhttp.trackName,
                songArtist,
                songPrice)
            mSongViewModel.addSong(song)

    }

}