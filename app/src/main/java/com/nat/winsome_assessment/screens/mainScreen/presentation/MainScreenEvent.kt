package com.nat.winsome_assessment.screens.mainScreen.presentation

import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieUiModel

sealed class MainScreenEvent {
    data class SearchOnMovie(val query: String) : MainScreenEvent()
    data class AddAndDeleteMovie(val movie: MovieUiModel) : MainScreenEvent()
    data object GetPopularMovies : MainScreenEvent()
}