package com.nat.winsome_assessment.application.data.local.domain.sharedUseCases

import com.nat.winsome_assessment.application.data.local.domain.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow

class IsMovieSavedUseCase (
    private val repository: LocalDataSource
) {

    operator fun invoke(movieId: Int): Flow<Boolean> {
        return repository.isMovieSaved(movieId = movieId)
    }
}