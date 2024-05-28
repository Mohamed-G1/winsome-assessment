package com.nat.winsome_assessment.application.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nat.winsome_assessment.application.utils.Constants.MOVIES_ENTITY_NAME
import com.nat.winsome_assessment.screens.detailsScreen.domain.models.MovieDetailsUiModel
import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieUiModel


@Entity(tableName = MOVIES_ENTITY_NAME)
data class MoviesEntity(
    val movieId: Int?,
    val movieName: String?,
    val moviePoster: String?,
    val movieDescription: String?,
    val rate: Double?,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


/**
 * Those two functions help to convert the movie model from/to entity and normal model*/
fun MoviesEntity.toUiModel(): MovieUiModel {
    return MovieUiModel(
        id = this.movieId,
        movieName = this.movieName,
        moviePoster = this.moviePoster,
        movieDescription = this.movieDescription,
        rate = this.rate
    )
}


fun MovieUiModel.toEntity(): MoviesEntity {
    return MoviesEntity(
        movieId = this.id,
        movieName = this.movieName,
        moviePoster = this.moviePoster,
        movieDescription = this.movieDescription,
        rate = this.rate

    )
}

/**
 * This to avoid create two entities in DB, because the MovieDetailsUiModel and the MovieUiModel are different
 * so the solution is to convert the MovieDetailsUiModel to the MovieUiModel with the necessary parameters that we need
 * */
fun MovieDetailsUiModel.toUiModel(): MovieUiModel {
    return MovieUiModel(
        id = this.movieId,
        movieName = this.movieName,
        moviePoster = this.moviePoster,
        movieDescription = this.movieDescription,
        rate = this.rate
    )
}