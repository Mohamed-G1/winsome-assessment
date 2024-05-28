package com.nat.winsome_assessment.application.data.local.domain.repository

import com.nat.winsome_assessment.application.data.local.db.entity.MoviesEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun addMovie(movie: MoviesEntity)
    suspend fun deleteMovie(movieId: Int)
    suspend fun getAllStoredMovies(): Flow<List<MoviesEntity>>
    fun isMovieSaved(movieId: Int): Flow<Boolean>
    suspend fun searchWithMovieName(query: String): Flow<List<MoviesEntity>>
}