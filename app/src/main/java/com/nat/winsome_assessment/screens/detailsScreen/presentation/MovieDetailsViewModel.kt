package com.nat.winsome_assessment.screens.detailsScreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nat.winsome_assessment.application.data.local.db.entity.MoviesEntity
import com.nat.winsome_assessment.application.data.local.db.entity.toEntity
import com.nat.winsome_assessment.application.data.remote.NetworkErrorHandler.Companion.handleNetworkError
import com.nat.winsome_assessment.screens.detailsScreen.domain.models.toMovieCastUiModel
import com.nat.winsome_assessment.screens.detailsScreen.domain.models.toMovieDetailsUiModel
import com.nat.winsome_assessment.screens.detailsScreen.domain.useCases.DetailsScreenUseCases
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.LocalDataSourceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val detailsScreenUseCases: DetailsScreenUseCases,
    private val localDataSourceUseCases: LocalDataSourceUseCases

) : ViewModel() {
    private val _state = MutableStateFlow(defaultMovieDetailsState())
    val state = _state.asStateFlow()


    fun onEvent(event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.GetMovieDetails -> {
                callMovieDetailsApi(event.movieId)
                callMovieCastApi(event.movieId)
            }
            is MovieDetailsEvent.AddAndDeleteMovie ->{
                toggleSavedMovie(movie = event.movie.toEntity())
            }
        }
    }

    private fun callMovieDetailsApi(movieID: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            detailsScreenUseCases.getMovieDetailsUseCase.invoke(movieID = movieID).handleNetworkError(
                onHandlingFinished = {
                    _state.update { it.copy(isLoading = false) }

                }
            ).collectLatest { response ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        model = response.toMovieDetailsUiModel()
                    )
                }
            }
        }
    }

    private fun callMovieCastApi(movieID: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            detailsScreenUseCases.getMovieCastUseCase.invoke(movieID = movieID).handleNetworkError(
                onHandlingFinished = {
                    _state.update { it.copy(isLoading = false) }
                }
            ).collectLatest { response ->
                _state.update {
                    it.copy(
                        cast = response.cast?.toMovieCastUiModel(),
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun toggleSavedMovie(movie: MoviesEntity) {
        viewModelScope.launch {
            val isSaved = localDataSourceUseCases.isMovieSavedUseCase(movie.movieId ?: 0).first()
            if (isSaved) {
                localDataSourceUseCases.deleteMovieUseCase(movie.movieId ?: 0)
            } else {
                localDataSourceUseCases.addMovieUseCase(movie)
            }
        }
    }

    fun isMovieSaved(movieId: Int): Flow<Boolean> {
        return localDataSourceUseCases.isMovieSavedUseCase(movieId)
    }
}