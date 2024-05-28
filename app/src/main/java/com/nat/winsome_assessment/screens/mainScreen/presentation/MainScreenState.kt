package com.nat.winsome_assessment.screens.mainScreen.presentation

import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieUiModel


data class MainScreenState(
    val model: List<MovieUiModel>?,
    val isLoading: Boolean,
)

fun defaultMainState() = MainScreenState(
    model = listOf(), isLoading = false
)
