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
import com.example.week3assignment.databinding.FragmentClassicBinding
import com.example.week3assignment.model.SongViewModel
import com.example.week3assignment.okhttp.OkHttp

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
    private lateinit var mSongViewModel: SongViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        okhttp.requestURL("https://itunes.apple.com/search?term=classic&amp;media=music&amp;entity=song&amp;limit=50")
        val recyclerView = binding.eventRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mSongViewModel = ViewModelProvider(this)[SongViewModel::class.java]
        mSongViewModel.readAllData.observe(viewLifecycleOwner, Observer { song -> adapter.update(song)})

        return binding.root
    }



}