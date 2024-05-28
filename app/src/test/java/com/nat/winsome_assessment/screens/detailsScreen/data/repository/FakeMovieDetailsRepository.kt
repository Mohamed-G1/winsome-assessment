package com.nat.winsome_assessment.screens.detailsScreen.data.repository

import com.nat.winsome_assessment.screens.detailsScreen.domain.models.Cast
import com.nat.winsome_assessment.screens.detailsScreen.domain.models.MovieCastResponse
import com.nat.winsome_assessment.screens.detailsScreen.domain.models.MovieDetailsResponse
import com.nat.winsome_assessment.screens.detailsScreen.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.flow

class FakeMovieDetailsRepository : MovieDetailsRepository {
    override fun getMovieDetails(movieID: Int) = flow {
        emit(
            MovieDetailsResponse(
                title = "Movie 1",
                id = 1
            )
        )
    }

    override fun getMovieCast(movieID: Int) = flow {
        emit(
            MovieCastResponse(
                cast = listOf(
                    Cast(
                        name = "Actor 1",
                        id = 1
                    ),
                    Cast(
                        name = "Actor 2",
                        id = 2
                    )
                )
            )
        )
    }
}