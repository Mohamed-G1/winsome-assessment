package com.nat.winsome_assessment.screens.mainScreen.domain.useCases

import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieResponse
import com.nat.winsome_assessment.screens.mainScreen.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query
import javax.inject.Inject

class SearchOnMovieUseCase (
    private val repository: MoviesRepository

) {
    // by adding operator to the function we are able to call the function by class name
    operator fun invoke(query: String): Flow<MovieResponse> {
        return repository.searchOnMovie(query = query)
    }
}