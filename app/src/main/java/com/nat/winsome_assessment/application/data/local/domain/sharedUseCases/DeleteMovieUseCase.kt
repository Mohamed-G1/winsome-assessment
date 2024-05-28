package com.nat.winsome_assessment.application.data.local.domain.sharedUseCases

import com.nat.winsome_assessment.application.data.local.domain.repository.LocalDataSource

class DeleteMovieUseCase (
    private val repository: LocalDataSource
) {
    suspend operator fun invoke(movieId: Int) {
         repository.deleteMovie(movieId = movieId)
    }
}