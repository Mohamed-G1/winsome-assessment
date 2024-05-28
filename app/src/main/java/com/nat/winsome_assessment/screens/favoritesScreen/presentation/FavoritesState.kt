package com.nat.winsome_assessment.screens.favoritesScreen.presentation

import com.nat.winsome_assessment.application.data.local.db.entity.MoviesEntity

data class FavoritesState (
    val storedMovies : List<MoviesEntity>
)

fun defaultFavoritesState() = FavoritesState(
    storedMovies = listOf()
)