package com.nat.winsome_assessment.screens.mainScreen.domain.useCases

import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieResponse
import com.nat.winsome_assessment.screens.mainScreen.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesListUseCase(
     private val moviesRepository: MoviesRepository
 ){
    // by adding operator to the function we are able to call the function by class name
    operator fun invoke() : Flow<MovieResponse>{
        return moviesRepository.fetchMoviesList()
    }
}
