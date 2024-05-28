package com.nat.winsome_assessment.application.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nat.winsome_assessment.application.data.local.db.entity.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(entity: MoviesEntity)

    @Query("DELETE FROM MOVIES_ENTITY WHERE movieId =:movieId")
    suspend fun deleteMovie(movieId: Int)

    @Query("SELECT * FROM MOVIES_ENTITY")
    fun gelAllMovies(): Flow<List<MoviesEntity>>

    /**
     * Using SELECT 1 is efficient because it doesn't need to fetch actual data from the table
     * Instead, it just checks if any row exists that matches the condition, which can be faster than retrieving all columns.*/
    @Query("SELECT EXISTS(SELECT 1 FROM MOVIES_ENTITY WHERE movieId =:movieId)")
    fun isMovieSaved(movieId: Int): Flow<Boolean>


    /**
     * The LIKE operator is used in SQL queries to search for a specified pattern in a column
     * this '%' is used with LIKE to matches any string that contains the search parameter
     * and the || operator used to concatenate the '%' with the search parameter
     * */
    @Query("SELECT * FROM MOVIES_ENTITY WHERE movieName LIKE '%' || :query || '%'")
    fun searchWithMovieName(query: String): Flow<List<MoviesEntity>>
}