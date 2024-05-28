package com.nat.winsome_assessment.application.data.local.di

import android.content.Context
import androidx.room.Room
import com.nat.winsome_assessment.application.data.local.db.MoviesDatabase
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.AddMovieUseCase
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.DeleteMovieUseCase
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.GetStoredMoviesUseCase
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.IsMovieSavedUseCase
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.LocalDataSourceUseCases
import com.nat.winsome_assessment.application.data.local.domain.sharedUseCases.SearchMovieNameUseCase
import com.nat.winsome_assessment.application.utils.Constants.DATABASE_NAME
import com.nat.winsome_assessment.application.data.local.domain.repository.LocalDataSource
import com.nat.winsome_assessment.application.data.local.repository.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) =
        Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource {
        return localDataSourceImpl
    }


    @Provides
    @Singleton
    fun provideLocalUseCasesUseCase(
        localDataSource: LocalDataSource
    ): LocalDataSourceUseCases {
        return LocalDataSourceUseCases(
            AddMovieUseCase(localDataSource),
            DeleteMovieUseCase(localDataSource),
            GetStoredMoviesUseCase(localDataSource),
            IsMovieSavedUseCase(localDataSource),
            SearchMovieNameUseCase(localDataSource)
        )
    }
}

