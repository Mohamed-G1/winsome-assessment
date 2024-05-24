package com.nat.winsome_assessment.application.data.remote

import com.nat.winsome_assessment.screens.detailsScreen.domain.models.MovieCastResponse
import com.nat.winsome_assessment.screens.detailsScreen.domain.models.MovieDetailsResponse
import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(Endpoint.POPULAR_MOVIES)
    suspend fun getPopularMoviesList(): MovieResponse

    @GET(Endpoint.SEARCH_ON_MOVIES)
    suspend fun searchOnMovie(
        @Query(Endpoint.QUERY) query: String
    ): MovieResponse

    @GET(Endpoint.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path(Endpoint.MOVIE_ID) movieID : Int
    ) : MovieDetailsResponse


    @GET(Endpoint.MOVIE_CAST)
    suspend fun getMovieCast(
        @Path(Endpoint.MOVIE_ID) movieID : Int
    ) : MovieCastResponse
}