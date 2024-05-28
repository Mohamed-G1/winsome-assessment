package com.nat.winsome_assessment.screens.detailsScreen.di

import com.nat.winsome_assessment.screens.detailsScreen.data.repository.MovieDetailsRepositoryImpl
import com.nat.winsome_assessment.screens.detailsScreen.domain.repository.MovieDetailsRepository
import com.nat.winsome_assessment.screens.detailsScreen.domain.useCases.GetMovieCastUseCase
import com.nat.winsome_assessment.screens.detailsScreen.domain.useCases.GetMovieDetailsUseCase
import com.nat.winsome_assessment.screens.detailsScreen.domain.useCases.DetailsScreenUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MovieDetailsModule {

    @Provides
    @Singleton
    fun provideMoviesDetailsRepository(moviesDetailsRepositoryImpl: MovieDetailsRepositoryImpl): MovieDetailsRepository {
        return moviesDetailsRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideUseCases(
        movieDetailsRepository: MovieDetailsRepository
    ): DetailsScreenUseCases {
        return DetailsScreenUseCases(
            GetMovieDetailsUseCase(movieDetailsRepository),
            GetMovieCastUseCase(movieDetailsRepository)
        )
    }
}