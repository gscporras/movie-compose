package com.movie.android.models.network

import androidx.compose.runtime.Immutable
import com.movie.android.models.NetworkResponseModel
import com.movie.android.models.Video

@Immutable
data class VideoListResponse(
    val id: Int,
    val results: List<Video>
) : NetworkResponseModel