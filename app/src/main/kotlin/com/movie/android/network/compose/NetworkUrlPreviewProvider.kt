package com.movie.android.network.compose

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.movie.android.R

class NetworkUrlPreviewProvider : PreviewParameterProvider<Int> {
    override val count: Int
        get() = super.count
    override val values: Sequence<Int>
        get() = sequenceOf(R.drawable.ic_youtube)
}