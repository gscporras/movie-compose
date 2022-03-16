package com.movie.android.network.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.movie.android.R
import com.movie.android.ui.theme.shimmerHighLight
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.palette.BitmapPalette

@Composable
@Preview
fun NetworkImage(
    @PreviewParameter(NetworkUrlPreviewProvider::class) networkUrl: Any?,
    modifier: Modifier = Modifier,
    circularReveal: CircularReveal? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
    bitmapPalette: BitmapPalette? = null,
    shimmerParams: ShimmerParams? = ShimmerParams(
        baseColor = MaterialTheme.colors.background,
        highlightColor = shimmerHighLight,
        dropOff = 0.65f
    ),
) {
    val url = networkUrl ?: return
    if (shimmerParams == null) {
        CoilImage(
            imageModel = url,
            modifier = modifier,
            contentScale = contentScale,
            circularReveal = circularReveal,
            bitmapPalette = bitmapPalette,
            failure = {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_broken_image),
                    contentDescription = null,
                    contentScale = ContentScale.Inside
                )
            },
        )
    } else {
        CoilImage(
            imageModel = url,
            modifier = modifier,
            contentScale = contentScale,
            circularReveal = circularReveal,
            bitmapPalette = bitmapPalette,
            shimmerParams = shimmerParams,
            failure = {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_broken_image),
                    contentDescription = null,
                    contentScale = ContentScale.Inside
                )
            }
        )
    }
}