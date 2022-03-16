package com.movie.android.models.network

import androidx.compose.runtime.Immutable
import com.movie.android.models.NetworkResponseModel
import com.movie.android.models.entities.Tv

@Immutable
data class TvResponse(
    val page: Int,
    val results: List<Tv>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel