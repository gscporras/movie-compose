package com.movie.android.models.network

import androidx.compose.runtime.Immutable
import com.movie.android.models.NetworkResponseModel
import com.movie.android.models.entities.Popular

@Immutable
data class PopularResponse(
    val page: Int,
    val results: List<Popular>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel