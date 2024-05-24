package com.nat.winsome_assessment.screens.detailsScreen.presentation

sealed class MovieDetailsEvent {

    data class GetMovieDetails(val movieID: Int) : MovieDetailsEvent()

}