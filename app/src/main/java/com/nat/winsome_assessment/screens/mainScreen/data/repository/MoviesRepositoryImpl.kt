package com.nat.winsome_assessment.screens.mainScreen.data.repository

import com.nat.winsome_assessment.application.data.remote.network.ApiService
import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieResponse
import com.nat.winsome_assessment.screens.mainScreen.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MoviesRepository {
    override fun fetchMoviesList(): Flow<MovieResponse> = flow {
        emit(apiService.getPopularMoviesList())
    }

    override fun searchOnMovie(query: String) = flow {
        emit(apiService.searchOnMovie(query = query))
    }
}