package com.nat.winsome_assessment.screens.detailsScreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nat.winsome_assessment.application.data.remote.NetworkErrorHandler.Companion.handleNetworkError
import com.nat.winsome_assessment.screens.detailsScreen.domain.models.toMovieCastUiModel
import com.nat.winsome_assessment.screens.detailsScreen.domain.models.toMovieDetailsUiModel
import com.nat.winsome_assessment.screens.detailsScreen.domain.useCases.GetMovieDetailsUseCase
import com.nat.winsome_assessment.screens.detailsScreen.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    private val _state = MutableStateFlow(defaultMovieDetailsState())
    val state = _state.asStateFlow()


    fun onEvent(event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.GetMovieDetails -> {
                callMovieDetailsApi(event.movieID)
                callMovieCastApi(event.movieID)
            }
        }
    }

    private fun callMovieDetailsApi(movieID: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            useCases.getMovieDetailsUseCase.invoke(movieID = movieID).handleNetworkError(
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
            useCases.getMovieCastUseCase.invoke(movieID = movieID).handleNetworkError(
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
}