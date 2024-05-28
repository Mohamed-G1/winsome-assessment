package com.nat.winsome_assessment.application.data.local.repository

import com.nat.winsome_assessment.application.data.local.db.MoviesDatabase
import com.nat.winsome_assessment.application.data.local.db.entity.MoviesEntity
import com.nat.winsome_assessment.application.data.local.domain.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val db: MoviesDatabase
) : LocalDataSource {
    override suspend fun addMovie(movie: MoviesEntity) {
        db.dao().insertMovie(entity = movie)
    }

    override suspend fun deleteMovie(movieId: Int) {
        db.dao().deleteMovie(movieId = movieId)
    }

    override suspend fun getAllStoredMovies(): Flow<List<MoviesEntity>> {
        return db.dao().gelAllMovies()
    }

    override fun isMovieSaved(movieId: Int): Flow<Boolean> {
        return db.dao().isMovieSaved(movieId = movieId)
    }

    override suspend fun searchWithMovieName(query: String): Flow<List<MoviesEntity>> {
        return db.dao().searchWithMovieName(query = query)
    }

}