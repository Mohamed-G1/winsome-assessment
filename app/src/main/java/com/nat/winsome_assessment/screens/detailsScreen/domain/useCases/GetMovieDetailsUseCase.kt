package com.nat.winsome_assessment.screens.detailsScreen.domain.useCases

import com.nat.winsome_assessment.screens.detailsScreen.domain.models.MovieDetailsResponse
import com.nat.winsome_assessment.screens.detailsScreen.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor (
    private val repository: MovieDetailsRepository
) {
    // by adding operator to the function we are able to call the function by class name
    operator fun invoke(movieID: Int): Flow<MovieDetailsResponse> {
        return repository.getMovieDetails(movieID = movieID)
    }
}