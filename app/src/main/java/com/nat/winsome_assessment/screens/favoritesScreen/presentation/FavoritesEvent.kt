package com.nat.winsome_assessment.screens.favoritesScreen.presentation

import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieUiModel

sealed class FavoritesEvent {
    data class SearchOnMovie(val query: String) : FavoritesEvent()
    data class DeleteMovie(val movie: MovieUiModel) : FavoritesEvent()
}