package com.example.week3assignment.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.week3assignment.model.SongData


@Dao
interface SongDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSong(song: SongData)

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addSongArtist(songArtist: SongData)
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addSongPrice(songPrice: SongData)

    @Query("SELECT * FROM Song_List ORDER BY id ASC")
    fun readAllData(): LiveData<List<SongData>>

}