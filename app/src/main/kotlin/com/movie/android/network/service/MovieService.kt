package com.movie.android.network.service

import com.movie.android.models.entities.Movie
import com.movie.android.models.network.*
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("trending/movie/week")
    suspend fun fetchTrending(
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("region") region: String
    ): ApiResponse<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun fetchMovieId(
        @Path("movie_id") id: Int,
        @Query("language") language: String,
        @Query("region") region: String
    ): ApiResponse<Movie>

    @GET("movie/popular?language=en")
    suspend fun fetchPopular(@Query("page") page: Int): ApiResponse<PopularResponse>

    @GET("movie/top_rated?language=en")
    suspend fun fetchTopRatedPeople(@Query("page") page: Int): ApiResponse<TopRatedResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun fetchVideos(@Path("movie_id") id: Int): ApiResponse<VideoListResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun fetchCasts(@Path("movie_id") id: Int): ApiResponse<CastListResponse>
}