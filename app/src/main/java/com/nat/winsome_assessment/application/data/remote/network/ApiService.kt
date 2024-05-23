package com.nat.winsome_assessment.application.data.remote.network

import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieResponse
import retrofit2.http.GET

interface ApiService {

    @GET("movie/popular?page=1")
    suspend fun getPopularMoviesList() : MovieResponse
}