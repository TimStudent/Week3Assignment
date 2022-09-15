package com.example.week3assignment.model

data class DomainSong(
    val trackName: String,
    val artistName: String,
    val trackPrice: Double
)

fun List<SongData?>?.mapToDomainSong(): List<DomainSong> {
    return this?.map { song ->
        DomainSong(
            trackName = song?.trackName ?: "Invalid Song Name",
            artistName = song?.artistName ?: "Invalid Artist Name",
            trackPrice = song?.trackPrice ?: 0.0
        )
    } ?: emptyList()
}
