package com.example.week3assignment.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Song_List")
data class SongData(
    @PrimaryKey(autoGenerate = true)  val id: Int,
    @ColumnInfo(name = "Song")        val trackName: String,
    @ColumnInfo(name = "Song Artist") val artistName: String,
    @ColumnInfo(name = "Song Price")  val trackPrice: Double,
    @ColumnInfo(name = "Song Preview")val songPreview: String
)
