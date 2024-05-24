package com.nat.winsome_assessment.screens.detailsScreen.domain.repository

import com.nat.winsome_assessment.screens.detailsScreen.domain.models.MovieCastResponse
import com.nat.winsome_assessment.screens.detailsScreen.domain.models.MovieDetailsResponse
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    fun getMovieDetails(movieID : Int) : Flow<MovieDetailsResponse>
    fun getMovieCast(movieID : Int) : Flow<MovieCastResponse>
}