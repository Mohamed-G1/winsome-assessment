package com.nat.winsome_assessment.application.data.local.repository

import com.nat.winsome_assessment.application.data.local.db.entity.MoviesEntity
import com.nat.winsome_assessment.application.data.local.domain.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow

class FakeLocalDataSource : LocalDataSource {
    override suspend fun addMovie(movie: MoviesEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMovie(movieId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllStoredMovies(): Flow<List<MoviesEntity>> {
        TODO("Not yet implemented")
    }

    override fun isMovieSaved(movieId: Int): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun searchWithMovieName(query: String): Flow<List<MoviesEntity>> {
        TODO("Not yet implemented")
    }
}