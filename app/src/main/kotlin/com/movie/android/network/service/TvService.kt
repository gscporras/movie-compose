package com.movie.android.network.service

import com.movie.android.models.network.CastListResponse
import com.movie.android.models.network.TvResponse
import com.movie.android.models.network.VideoListResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvService {

    @GET("tv/popular?language=es")
    suspend fun fetchTv(@Query("page") page: Int): ApiResponse<TvResponse>

    @GET("tv/{tv_id}/videos")
    suspend fun fetchTvVideos(@Path("tv_id") id: Int): ApiResponse<VideoListResponse>

    @GET("tv/{tv_id}/credits")
    suspend fun fetchTvCasts(@Path("tv_id") id: Int): ApiResponse<CastListResponse>
}
