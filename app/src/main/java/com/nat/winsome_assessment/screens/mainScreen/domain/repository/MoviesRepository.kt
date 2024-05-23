package com.nat.winsome_assessment.screens.mainScreen.domain.repository

import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun fetchMoviesList(): Flow<MovieResponse>
    fun searchOnMovie(query : String) : Flow<MovieResponse>
}