package com.nat.winsome_assessment.application.data.remote.network

import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMoviesList(): MovieResponse

    @GET("search/movie")
    suspend fun searchOnMovie(
        @Query("query") query: String
    ): MovieResponse

}