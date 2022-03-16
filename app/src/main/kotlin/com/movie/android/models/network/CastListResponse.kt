package com.movie.android.models.network

import androidx.compose.runtime.Immutable
import com.movie.android.models.Cast
import com.movie.android.models.NetworkResponseModel

@Immutable
data class CastListResponse(
    val id: Int,
    val cast: List<Cast>
): NetworkResponseModel