package com.example.week3assignment.adapter

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.week3assignment.R
import com.example.week3assignment.databinding.CardViewLayoutBinding
import com.example.week3assignment.model.SongData

class SongAdapter(
    private val mList: MutableList<SongData> = mutableListOf(),
    private val onSongClickListener: (SongData) -> Unit) :RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            CardViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    fun update(newData: List<SongData>) {
        mList.clear()
        mList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(binding: ViewHolder, position: Int) {
        binding.bind(mList[position], onSongClickListener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}

class ViewHolder(private val binding:CardViewLayoutBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(song: SongData, onSongClickListener: (SongData) -> Unit) {
        binding.SongTitle.text = song.trackName
        binding.SongArtist.text = song.artistName
        binding.SongPrice.text = song.trackPrice.toString()
        binding.imageView.load(song.pictureLink)
        binding.GridLayout.setOnClickListener {
            val url = song.songPreview
            val mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(url)
                prepare() // might take long! (for buffering, etc)
                start()
            }
        }
        onSongClickListener.invoke(song)
    }
}