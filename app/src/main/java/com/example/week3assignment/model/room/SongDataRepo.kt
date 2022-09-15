package com.example.week3assignment.model.room

import androidx.lifecycle.LiveData
import com.example.week3assignment.model.SongData

class SongDataRepo(private val songDao: SongDao){

    val readAllData: LiveData<List<SongData>> = songDao.readAllData()
    val readRock: LiveData<List<SongData>> = songDao.readRock()
    val readClassic: LiveData<List<SongData>> = songDao.readClassic()
    val readPop: LiveData<List<SongData>> = songDao.readPop()

    suspend fun addSong(song: SongData){
        songDao.addSong(song)
    }
}