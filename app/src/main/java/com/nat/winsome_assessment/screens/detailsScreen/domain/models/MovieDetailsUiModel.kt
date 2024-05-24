package com.nat.winsome_assessment.screens.detailsScreen.domain.models

data class MovieDetailsUiModel(
    val movieName: String?,
    val rate: Double?,
    val language: String?,
    val moviePoster: String?,
    val movieDescription: String?,
    val genre: List<Genre>?,
    val movieLength: Int?,
    val voteCount: Int?
)

fun MovieDetailsResponse.toMovieDetailsUiModel(): MovieDetailsUiModel {
    return MovieDetailsUiModel(
        movieName = this.title,
        rate = this.voteAverage,
        language = this.spokenLanguages?.firstOrNull()?.englishName,
        moviePoster = this.backdropPath,
        movieDescription = this.overview,
        genre = this.genres,
        movieLength = this.runtime,
        voteCount = this.voteCount
    )
}
