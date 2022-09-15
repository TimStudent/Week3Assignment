package com.example.week3assignment.model.room

import androidx.lifecycle.LiveData
import com.example.week3assignment.model.SongData

class SongDataRepo(private val songDao: SongDao){

    val readAllData: LiveData<List<SongData>> = songDao.readAllData()

    suspend fun addSong(song: SongData){
        songDao.addSong(song)
    }
}