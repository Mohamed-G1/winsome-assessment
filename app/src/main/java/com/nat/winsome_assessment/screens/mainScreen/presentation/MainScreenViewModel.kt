package com.nat.winsome_assessment.screens.mainScreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nat.winsome_assessment.application.data.local.db.entity.MoviesEntity
import com.nat.winsome_assessment.application.data.local.db.entity.toEntity
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.LocalDataSourceUseCases
import com.nat.winsome_assessment.application.data.remote.NetworkErrorHandler.Companion.handleNetworkError
import com.nat.winsome_assessment.application.data.remote.NetworkErrorHandler.Companion.resetGeneralState
import com.nat.winsome_assessment.screens.mainScreen.domain.models.toMovieUiModel
import com.nat.winsome_assessment.screens.mainScreen.domain.useCases.MainScreenUseCases
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
class MainScreenViewModel @Inject constructor(
    private val mainScreenUseCases: MainScreenUseCases,
    private val localDataSourceUseCases: LocalDataSourceUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(defaultMainState())
    val state = _state.asStateFlow()

    init {
        resetGeneralState()
        callPopularMoviesApi()
    }


    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.SearchOnMovie -> {
                // Call the search api
                resetGeneralState()
                callSearchOnMovieApi(event.query)
            }

            is MainScreenEvent.AddAndDeleteMovie -> {
                toggleSavedMovie(event.movie.toEntity())
            }

            is MainScreenEvent.GetPopularMovies -> {
                // Call the search api
                resetGeneralState()
                callPopularMoviesApi()
            }
        }
    }

    private fun callSearchOnMovieApi(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            mainScreenUseCases.searchOnMovieUseCase.invoke(query = query)
                .handleNetworkError(onHandlingFinished = {
                    _state.update {
                        it.copy(isLoading = false)
                    }
                }).collectLatest { response ->
                    _state.update {
                        it.copy(
                            model = response.results?.toMovieUiModel(),
                            isLoading = false
                        )
                    }
                }
        }
    }

     private fun callPopularMoviesApi() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            mainScreenUseCases.getMoviesListUseCase.invoke()
                .handleNetworkError(onHandlingFinished = {
                    _state.update {
                        it.copy(isLoading = false)
                    }
                }).collectLatest { response ->
                    _state.update {
                        it.copy(
                            model = response.results?.toMovieUiModel(),
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