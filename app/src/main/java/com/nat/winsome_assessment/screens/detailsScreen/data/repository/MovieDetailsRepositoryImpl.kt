package com.nat.winsome_assessment.screens.detailsScreen.data.repository

import com.nat.winsome_assessment.application.data.remote.ApiService
import com.nat.winsome_assessment.screens.detailsScreen.domain.models.MovieCastResponse
import com.nat.winsome_assessment.screens.detailsScreen.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieDetailsRepository {
    override fun getMovieDetails(movieID: Int) = flow {
        emit(apiService.getMovieDetails(movieID = movieID))
    }

    override fun getMovieCast(movieID: Int) = flow {
        emit(apiService.getMovieCast(movieID = movieID))
    }
}