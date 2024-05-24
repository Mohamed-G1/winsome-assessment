package com.nat.winsome_assessment.screens.detailsScreen.presentation

import com.nat.winsome_assessment.screens.detailsScreen.domain.models.MovieCastUiModel
import com.nat.winsome_assessment.screens.detailsScreen.domain.models.MovieDetailsUiModel

data class MovieDetailsState(
    val model: MovieDetailsUiModel?,
    val cast: List<MovieCastUiModel>?,
    val isLoading: Boolean
)

fun defaultMovieDetailsState() = MovieDetailsState(
    model = null, cast = listOf(), isLoading = false
)