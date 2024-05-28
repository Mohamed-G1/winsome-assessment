package com.nat.winsome_assessment.screens.favoritesScreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nat.winsome_assessment.application.data.local.db.entity.MoviesEntity
import com.nat.winsome_assessment.application.data.local.db.entity.toEntity
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.LocalDataSourceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val localDataSourceUseCases: LocalDataSourceUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(defaultFavoritesState())
    val state = _state.asStateFlow()

    init {
        getStoredMovies()
    }

    fun onEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.SearchOnMovie -> {
                searchWithMovieName(query = event.query)
            }

            is FavoritesEvent.DeleteMovie -> {
                deleteSavedMovie(movie = event.movie.toEntity())

            }

            is FavoritesEvent.SearchClosed -> {
                getStoredMovies()
            }

        }

    }

    private fun getStoredMovies() {
        viewModelScope.launch {
            localDataSourceUseCases.getStoredMoviesUseCase.invoke()
                .collectLatest { movies ->
                    _state.value = _state.value.copy(storedMovies = movies)
                }
        }
    }

    private fun deleteSavedMovie(movie: MoviesEntity) {
        viewModelScope.launch {
            /**This to make sure that the delete operation will happens sync and not async
             * to avoid that the stored list will not fetched before the process is complete
             * and after 200ms refresh to list, to guarantee that the stored list will refreshed
             * after the deletion operation*/
            withContext(Dispatchers.Main) {
                localDataSourceUseCases.deleteMovieUseCase(movie.movieId ?: 0)
                delay(200)
                getStoredMovies()
            }
        }
    }

    fun isMovieSaved(movieId: Int): Flow<Boolean> {
        return localDataSourceUseCases.isMovieSavedUseCase(movieId)
    }

    private fun searchWithMovieName(query: String) {
        viewModelScope.launch {
            localDataSourceUseCases.searchMovieNameUseCase.invoke(query = query)
                .collectLatest { movies ->
                    _state.value = _state.value.copy(storedMovies = movies)

                }
        }
    }
}