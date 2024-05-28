package com.nat.winsome_assessment.application.data.local.domain.sharedUseCases

import com.nat.winsome_assessment.application.data.local.db.entity.MoviesEntity
import com.nat.winsome_assessment.application.data.local.domain.repository.LocalDataSource

class AddMovieUseCase (
    private val repository: LocalDataSource
) {
    suspend operator fun invoke(movie: MoviesEntity) {
        repository.addMovie(movie = movie)
    }
}