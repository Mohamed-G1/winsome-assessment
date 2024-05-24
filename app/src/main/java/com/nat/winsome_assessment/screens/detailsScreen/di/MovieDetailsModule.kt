package com.nat.winsome_assessment.screens.detailsScreen.di

import com.nat.winsome_assessment.screens.detailsScreen.data.repository.MovieDetailsRepositoryImpl
import com.nat.winsome_assessment.screens.detailsScreen.domain.repository.MovieDetailsRepository
import com.nat.winsome_assessment.screens.detailsScreen.domain.useCases.GetMovieCastUseCase
import com.nat.winsome_assessment.screens.detailsScreen.domain.useCases.GetMovieDetailsUseCase
import com.nat.winsome_assessment.screens.detailsScreen.domain.useCases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class MovieDetailsModule {

    @Provides
    @Singleton
    fun provideMoviesDetailsRepository(moviesDetailsRepositoryImpl: MovieDetailsRepositoryImpl): MovieDetailsRepository {
        return moviesDetailsRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideGetMoviesDetailsUseCase(movieDetailsRepository: MovieDetailsRepository): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(movieDetailsRepository)
    }


    @Provides
    @Singleton
    fun provideGetMoviesCastsUseCase(movieDetailsRepository: MovieDetailsRepository): GetMovieCastUseCase {
        return GetMovieCastUseCase(movieDetailsRepository)
    }
    @Provides
    @Singleton
    fun provideUseCases(
        getMovieDetailsUseCase: GetMovieDetailsUseCase,
        getMovieCastUseCase: GetMovieCastUseCase
    ): UseCases{
        return UseCases(getMovieDetailsUseCase, getMovieCastUseCase)
    }

}