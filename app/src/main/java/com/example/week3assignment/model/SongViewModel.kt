package com.example.week3assignment.model

import android.app.Application
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.week3assignment.adapter.SongAdapter
import com.example.week3assignment.model.room.SongDataBase
import com.example.week3assignment.model.room.SongDataRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SongViewModel(application: Application) : AndroidViewModel(application) {
    val readRock: LiveData<List<SongData>>
    val readClassic: LiveData<List<SongData>>
    val readPop: LiveData<List<SongData>>
    private val repository: SongDataRepo

    init {
        val songDao = SongDataBase.getDatabase(application).songDao()
        repository = SongDataRepo(songDao)
        readRock = repository.readRock
        readClassic = repository.readClassic
        readPop = repository.readPop
    }

    fun addSong(song: SongData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSong(song)
        }
    }

//    fun playSong(song: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            var url = ""
//            val mediaPlayer = MediaPlayer().apply {
//                setAudioAttributes(
//                    AudioAttributes.Builder()
//                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                        .setUsage(AudioAttributes.USAGE_MEDIA)
//                        .build()
//                )
//            }
//            try {
//                if (mediaPlayer.isPlaying) {
//                    mediaPlayer.pause()
//                    mediaPlayer.stop()
//                } else {
//                    url = song
//                    mediaPlayer.setDataSource(url)
//                    mediaPlayer.setOnPreparedListener {
//                        it.start()
//                    }
//                    mediaPlayer.setOnCompletionListener {
//                        it.release()
//                    }
//                    mediaPlayer.prepareAsync()
//                }
//            } catch (e: Exception) {
//
//            }
//        }
//    }
}