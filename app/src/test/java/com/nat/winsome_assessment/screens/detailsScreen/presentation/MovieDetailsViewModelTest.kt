package com.nat.winsome_assessment.screens.detailsScreen.presentation

import com.nat.winsome_assessment.application.MainCoroutineRule
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.AddMovieUseCase
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.DeleteMovieUseCase
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.GetStoredMoviesUseCase
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.IsMovieSavedUseCase
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.LocalDataSourceUseCases
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.SearchMovieNameUseCase
import com.nat.winsome_assessment.application.data.local.repository.FakeLocalDataSource
import com.nat.winsome_assessment.screens.detailsScreen.data.repository.FakeMovieDetailsRepository
import com.nat.winsome_assessment.screens.detailsScreen.domain.useCases.DetailsScreenUseCases
import com.nat.winsome_assessment.screens.detailsScreen.domain.useCases.GetMovieCastUseCase
import com.nat.winsome_assessment.screens.detailsScreen.domain.useCases.GetMovieDetailsUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailsViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    private lateinit var useCases: DetailsScreenUseCases
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private lateinit var getMovieCastUseCase: GetMovieCastUseCase
    private lateinit var addMovieUseCase: AddMovieUseCase
    private lateinit var deleteMovieUseCase: DeleteMovieUseCase
    private lateinit var getStoredMoviesUseCase: GetStoredMoviesUseCase
    private lateinit var isMovieSavedUseCase: IsMovieSavedUseCase
    private lateinit var searchMovieNameUseCase: SearchMovieNameUseCase
    private lateinit var fakeLocalDataSource: FakeLocalDataSource
    private lateinit var localDataSourceUseCases: LocalDataSourceUseCases
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var fakeMovieDetailsRepository: FakeMovieDetailsRepository

    @Before
    fun setUp() {
        // initialize repos
        fakeLocalDataSource = FakeLocalDataSource()
        fakeMovieDetailsRepository = FakeMovieDetailsRepository()

        // initialize use cases
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


        getMovieDetailsUseCase = GetMovieDetailsUseCase(fakeMovieDetailsRepository)
        getMovieCastUseCase = GetMovieCastUseCase(fakeMovieDetailsRepository)
        useCases = DetailsScreenUseCases(getMovieDetailsUseCase, getMovieCastUseCase)

        // initialize view model
        viewModel = MovieDetailsViewModel(useCases, localDataSourceUseCases)
    }

    @Test
    fun `get movie details, return details`() = runTest {
        val result = useCases.getMovieDetailsUseCase.invoke(1).first()
        assertNotNull(result)
        assertEquals(1, result.id)
        assertEquals("Movie 1", result.title)
    }


    @Test
    fun `get cast movie, return movie cast list`() = runTest {
        val result = useCases.getMovieCastUseCase.invoke(1).first()
        assertNotNull(result)
        assertEquals(1, result.cast?.get(0)?.id)
        assertEquals("Actor 1", result.cast?.get(0)?.name)
        assertEquals(2, result.cast?.get(1)?.id)
        assertEquals("Actor 2", result.cast?.get(1)?.name)
    }
}