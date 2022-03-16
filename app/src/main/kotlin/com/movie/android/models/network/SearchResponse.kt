package com.movie.android.models.network

import androidx.compose.runtime.Immutable
import com.movie.android.models.NetworkResponseModel
import com.movie.android.models.entities.Search

@Immutable
data class SearchResponse(
    val page: Int,
    val results: List<Search>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel