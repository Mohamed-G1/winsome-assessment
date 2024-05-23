package com.nat.winsome_assessment.screens.mainScreen.presentation

import androidx.lifecycle.ViewModel
import com.nat.winsome_assessment.application.data.remote.network.NetworkErrorHandler.Companion.handleNetworkError
import com.nat.winsome_assessment.screens.mainScreen.domain.useCases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    private val _state = MutableStateFlow(defaultMainState())
    val state = _state.asStateFlow()

    private fun getMovies() {
        useCases.getMoviesListUseCase.invoke().onEach { response ->
            _state.update { it.copy(model = response, isLoading = true) }

        }.handleNetworkError(onHandlingFinished = {
            _state.update {
                it.copy(isLoading = false)
            }
        })
    }

}