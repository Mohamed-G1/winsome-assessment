package com.nat.winsome_assessment.application.data.local.domain.sharedUseCases



data class LocalDataSourceUseCases (
    val addMovieUseCase: AddMovieUseCase,
    val deleteMovieUseCase: DeleteMovieUseCase,
    val getStoredMoviesUseCase: GetStoredMoviesUseCase,
    val isMovieSavedUseCase: IsMovieSavedUseCase,
    val searchMovieNameUseCase: SearchMovieNameUseCase
)
