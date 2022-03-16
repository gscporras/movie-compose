package com.movie.android.models.network

import androidx.compose.runtime.Immutable
import com.movie.android.models.NetworkResponseModel
import com.movie.android.models.entities.TopRated

@Immutable
data class TopRatedResponse(
    val page: Int,
    val results: List<TopRated>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel