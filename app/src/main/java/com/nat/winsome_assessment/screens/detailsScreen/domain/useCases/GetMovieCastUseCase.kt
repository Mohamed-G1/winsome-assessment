package com.nat.winsome_assessment.screens.detailsScreen.domain.useCases

import com.nat.winsome_assessment.screens.detailsScreen.domain.models.MovieCastResponse
import com.nat.winsome_assessment.screens.detailsScreen.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieCastUseCase (
    private val repository: MovieDetailsRepository
) {
    // by adding operator to the function we are able to call the function by class name
    operator fun invoke(movieID: Int): Flow<MovieCastResponse> {
        return repository.getMovieCast(movieID = movieID)
    }
}
