package com.nat.winsome_assessment.screens.mainScreen.domain.useCases

import javax.inject.Inject

class UseCases @Inject constructor(
    val getMoviesListUseCase: GetMoviesListUseCase,
    val searchOnMovieUseCase: SearchOnMovieUseCase
)
