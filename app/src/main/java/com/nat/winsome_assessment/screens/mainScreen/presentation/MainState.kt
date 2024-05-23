package com.nat.winsome_assessment.screens.mainScreen.presentation

import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieResponse

data class MainState(
    val model: MovieResponse,
    val isLoading: Boolean
)

fun defaultMainState() = MainState(
    model = MovieResponse(
        page = null,
        results = listOf(),
        totalPages = null,
        totalResults = null
    ), isLoading = false

)
