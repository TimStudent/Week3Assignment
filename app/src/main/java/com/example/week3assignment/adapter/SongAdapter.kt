package com.example.week3assignment.adapter

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week3assignment.R
import com.example.week3assignment.databinding.CardViewLayoutBinding
import com.example.week3assignment.model.SongData
import com.squareup.picasso.Picasso


class SongAdapter(
    private val mList: MutableList<SongData> = mutableListOf(),
    private val onSongClickListener: (SongData) -> Unit) :RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            CardViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @SuppressLint("NotifyDataSetChanged")
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

        Picasso.get()
            .load(song.pictureLink)
            .placeholder(R.drawable.ic_baseline_wallpaper_24)
            .error(R.drawable.ic_baseline_error_24)
            .into(binding.imageView)

        var url: String
        val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }

        binding.GridLayout.setOnClickListener {
            try {
                if(mediaPlayer.isPlaying){
                    mediaPlayer.pause()
                    mediaPlayer.reset()
                }
                else{
                    url = song.songPreview
                    mediaPlayer.setDataSource(url)
                    mediaPlayer.setOnPreparedListener{
                        it.start()
                    }
                    mediaPlayer.setOnCompletionListener {
                        it.release()
                    }
                    mediaPlayer.prepareAsync()
                }
            }catch (e: Exception){

            }
        }
        onSongClickListener.invoke(song)
    }
}