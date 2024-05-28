package com.nat.winsome_assessment.screens.mainScreen.data.repository

import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieResponse
import com.nat.winsome_assessment.screens.mainScreen.domain.models.MoviesList
import com.nat.winsome_assessment.screens.mainScreen.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMoviesRepository : MoviesRepository {
    override fun fetchMoviesList(): Flow<MovieResponse> = flow {
        emit(
            MovieResponse(
                results = listOf(
                    MoviesList(id = 1, title = "Movie 1"),
                    MoviesList(id = 2, title = "Movie 2")
                )
            )
        )
    }

    override fun searchOnMovie(query: String): Flow<MovieResponse> = flow {
        emit(
            MovieResponse(
                results = listOf(
                    MoviesList(id = 1, title = "Search Movie 1"),
                    MoviesList(id = 2, title = "Search Movie 2")
                )
            )
        )
    }
}


