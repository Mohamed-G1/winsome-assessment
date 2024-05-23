package com.nat.winsome_assessment.screens.mainScreen.domain.models



data class MovieUiModel(
    val id: Int?,
    val movieName: String?,
    val moviePoster: String?,
    val movieDescription: String?,
    val rate: Double?
)

fun List<MoviesList>.toMovieUiModel(): List<MovieUiModel> {
    return this.map { movie ->
        MovieUiModel(
            id = movie.id,
            movieName = movie.title,
            moviePoster = movie.posterPath,
            movieDescription = movie.overview,
            rate = movie.voteAverage

        )
    }
}
