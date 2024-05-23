package com.nat.winsome_assessment.screens.mainScreen.presentation

sealed class MainScreenEvent {

    data class SearchOnMovie(val query: String) : MainScreenEvent()

}