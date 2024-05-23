package com.nat.winsome_assessment.screens.mainScreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nat.winsome_assessment.application.data.remote.network.NetworkErrorHandler.Companion.handleNetworkError
import com.nat.winsome_assessment.application.data.remote.network.NetworkErrorHandler.Companion.resetGeneralState
import com.nat.winsome_assessment.screens.mainScreen.domain.models.toMovieUiModel
import com.nat.winsome_assessment.screens.mainScreen.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    private val _state = MutableStateFlow(defaultMainState())
    val state = _state.asStateFlow()

    init {
        resetGeneralState()
        getMovies()
    }


    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.SearchOnMovie -> {
                // Call the search api
                resetGeneralState()
                searchOnMovie(event.query)
            }
        }
    }

    private fun searchOnMovie(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            useCases.searchOnMovieUseCase.invoke(query = query)
                .handleNetworkError(onHandlingFinished = {
                    _state.update {
                        it.copy(isLoading = false)
                    }
                }).collectLatest { response ->
                    _state.update { it.copy(model = response.results?.toMovieUiModel(), isLoading = false) }
                }
        }
    }

    private fun getMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            useCases.getMoviesListUseCase.invoke().handleNetworkError(onHandlingFinished = {
                _state.update {
                    it.copy(isLoading = false)
                }
            }).collectLatest { response ->
                _state.update { it.copy(model = response.results?.toMovieUiModel(), isLoading = false) }
            }
        }
    }

}