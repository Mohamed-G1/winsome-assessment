package com.nat.winsome_assessment.screens.mainScreen.presentation

import com.nat.winsome_assessment.application.MainCoroutineRule
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.AddMovieUseCase
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.DeleteMovieUseCase
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.GetStoredMoviesUseCase
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.IsMovieSavedUseCase
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.LocalDataSourceUseCases
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.SearchMovieNameUseCase
import com.nat.winsome_assessment.application.data.local.repository.FakeLocalDataSource
import com.nat.winsome_assessment.screens.mainScreen.data.repository.FakeMoviesRepository
import com.nat.winsome_assessment.screens.mainScreen.domain.useCases.GetMoviesListUseCase
import com.nat.winsome_assessment.screens.mainScreen.domain.useCases.MainScreenUseCases
import com.nat.winsome_assessment.screens.mainScreen.domain.useCases.SearchOnMovieUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainScreenViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    private lateinit var useCases: MainScreenUseCases
    private lateinit var getMoviesListUseCase: GetMoviesListUseCase
    private lateinit var searchOnMovieUseCase: SearchOnMovieUseCase
    private lateinit var localDataSourceUseCases: LocalDataSourceUseCases
    private lateinit var viewModel: MainScreenViewModel
    private lateinit var addMovieUseCase: AddMovieUseCase
    private lateinit var deleteMovieUseCase: DeleteMovieUseCase
    private lateinit var getStoredMoviesUseCase: GetStoredMoviesUseCase
    private lateinit var isMovieSavedUseCase: IsMovieSavedUseCase
    private lateinit var searchMovieNameUseCase: SearchMovieNameUseCase
    private lateinit var fakeLocalDataSource: FakeLocalDataSource
    private lateinit var fakeMoviesRepository: FakeMoviesRepository

    @Before
    fun setUp() {
        fakeLocalDataSource = FakeLocalDataSource()
        fakeMoviesRepository = FakeMoviesRepository()
        addMovieUseCase = AddMovieUseCase(fakeLocalDataSource)
        deleteMovieUseCase = DeleteMovieUseCase(fakeLocalDataSource)
        getStoredMoviesUseCase = GetStoredMoviesUseCase(fakeLocalDataSource)
        isMovieSavedUseCase = IsMovieSavedUseCase(fakeLocalDataSource)
        searchMovieNameUseCase = SearchMovieNameUseCase(fakeLocalDataSource)
        localDataSourceUseCases = LocalDataSourceUseCases(
            addMovieUseCase,
            deleteMovieUseCase,
            getStoredMoviesUseCase,
            isMovieSavedUseCase,
            searchMovieNameUseCase
        )

        // initialize use cases
        getMoviesListUseCase = GetMoviesListUseCase(fakeMoviesRepository)
        searchOnMovieUseCase = SearchOnMovieUseCase(fakeMoviesRepository)
        useCases = MainScreenUseCases(
            getMoviesListUseCase,
            searchOnMovieUseCase
        )
        // initialize view model
        viewModel = MainScreenViewModel(
            useCases, localDataSourceUseCases
        )
    }

    @Test
    fun `get movies list, return movie list`() = runTest {
        val result = useCases.getMoviesListUseCase.invoke().first()
        assertNotNull(result.results?.get(0))
        assertEquals(1, result.results?.get(0)?.id)
        assertEquals("Movie 1", result.results?.get(0)?.title)
        assertEquals(2, result.results?.get(1)?.id)
        assertEquals("Movie 2", result.results?.get(1)?.title)
    }

    @Test
    fun `search on movies list with query, return movie list`() = runTest {
        val movies = useCases.searchOnMovieUseCase.invoke("Search Movie 1").first()
        assertNotNull(movies.results)
        assertEquals(2, movies.results?.size)
        assertEquals("Search Movie 1", movies.results?.get(0)?.title)
        assertEquals("Search Movie 2", movies.results?.get(1)?.title)
    }
}