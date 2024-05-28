package com.nat.winsome_assessment.application.data.local.domain.sharedUseCases

import com.nat.winsome_assessment.application.data.local.db.entity.MoviesEntity
import com.nat.winsome_assessment.application.data.local.domain.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow

class GetStoredMoviesUseCase (
    private val repository: LocalDataSource
) {
    suspend operator fun invoke(): Flow<List<MoviesEntity>> {
        return repository.getAllStoredMovies()
    }
}