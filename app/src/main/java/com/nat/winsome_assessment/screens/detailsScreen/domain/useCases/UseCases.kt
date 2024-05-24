package com.nat.winsome_assessment.screens.detailsScreen.domain.useCases

import javax.inject.Inject

data class UseCases @Inject constructor(
    val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    val getMovieCastUseCase: GetMovieCastUseCase
)
