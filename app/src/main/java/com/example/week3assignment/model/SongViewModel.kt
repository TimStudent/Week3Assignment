package com.example.week3assignment.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.week3assignment.model.room.SongDataBase
import com.example.week3assignment.model.room.SongDataRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SongViewModel (application: Application): AndroidViewModel(application){
    val readAllData: LiveData<List<SongData>>
    private val repository: SongDataRepo

    init {
        val songDao = SongDataBase.getDatabase(application).songDao()
        repository = SongDataRepo(songDao)
        readAllData = repository.readAllData
    }

    fun addSong(song:SongData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSong(song)
        }
    }
}