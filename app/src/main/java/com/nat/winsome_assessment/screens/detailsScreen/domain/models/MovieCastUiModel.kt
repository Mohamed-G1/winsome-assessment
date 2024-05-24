package com.nat.winsome_assessment.screens.detailsScreen.domain.models

data class MovieCastUiModel(
    val personName: String?,
    val personImage: String?
)

fun List<Cast>.toMovieCastUiModel(): List<MovieCastUiModel> {
    return this.map { cast ->
        MovieCastUiModel(
            personName = cast.name, personImage = cast.profilePath
        )
    }
}
