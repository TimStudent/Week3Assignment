package com.example.week3assignment.utils

import com.example.week3assignment.model.DomainSong

sealed class UIState {
    object LOADING: UIState()
    data class SUCCESS(val cards: List<DomainSong>) : UIState()
    data class ERROR(val error: Exception) : UIState()
}
