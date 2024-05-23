package com.nat.winsome_assessment.screens.mainScreen.di

import com.nat.winsome_assessment.screens.mainScreen.data.repository.MoviesRepositoryImpl
import com.nat.winsome_assessment.screens.mainScreen.domain.repository.MoviesRepository
import com.nat.winsome_assessment.screens.mainScreen.domain.useCases.GetMoviesListUseCase
import com.nat.winsome_assessment.screens.mainScreen.domain.useCases.SearchOnMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class MainScreenModule {
    @Provides
    @Singleton
    fun provideMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository {
        return moviesRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideGetMoviesListUseCase(moviesRepository: MoviesRepository): GetMoviesListUseCase {
        return GetMoviesListUseCase(moviesRepository)
    }

    @Provides
    @Singleton
    fun provideSearchOnMovieUseCase(moviesRepository: MoviesRepository): SearchOnMovieUseCase {
        return SearchOnMovieUseCase(moviesRepository)
    }
}