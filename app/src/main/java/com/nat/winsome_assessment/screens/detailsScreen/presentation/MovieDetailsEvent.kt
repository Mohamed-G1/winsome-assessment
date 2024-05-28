package com.nat.winsome_assessment.screens.detailsScreen.presentation

import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieUiModel

sealed class MovieDetailsEvent {

    data class GetMovieDetails(val movieId: Int) : MovieDetailsEvent()
    data class AddAndDeleteMovie(val movie: MovieUiModel) : MovieDetailsEvent()

}