package com.movie.android.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.google.accompanist.coil.rememberCoilPainter
import com.movie.android.models.entities.Movie
import com.movie.android.network.Api
import com.movie.android.ui.feature.detail.MovieDetailsText
import com.movie.android.ui.theme.black
import com.movie.android.ui.theme.black_80
import com.movie.android.ui.theme.dmSansFamily
import com.movie.android.ui.theme.white
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
@Preview
fun MovieDetailContent(
    movie: Movie? = Movie(),
    pressOnBack: () -> Unit = {}
) {

    val stateToolbar = rememberCollapsingToolbarScaffoldState()
    val progress = stateToolbar.toolbarState.progress
    val density = LocalDensity.current.density
    val widthToolbar = remember { mutableStateOf(0f) }
    val heightToolbar = remember { mutableStateOf(0f) }

    CollapsingToolbarScaffold(
        modifier = Modifier
            .fillMaxSize(),
        state = stateToolbar,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            val textSize = (18 + (30 - 18) * stateToolbar.toolbarState.progress).sp



            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(black)
                    .pin()
                    .parallax()
                    .padding(bottom = 24.dp)
                    .graphicsLayer {
                        alpha = progress
                    }
                    .onGloballyPositioned {
                        widthToolbar.value = it.size.width / density
                        heightToolbar.value = it.size.height / density
                    },
            ) {
                val (cover, toolbar, picture, title, releaseDate) = createRefs()

                val painter =
                    rememberImagePainter(data = Api.getPosterPath(movie?.poster_path))

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                        .constrainAs(cover) {
                            top.linkTo(parent.top)
                        },
                    painter = rememberCoilPainter(request = Api.getBackdropPath(movie?.backdrop_path)),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

                Card(
                    modifier = Modifier
                        .size(80.dp, 120.dp)
                        .constrainAs(picture) {
                            top.linkTo(cover.bottom)
                            bottom.linkTo(cover.bottom)
                            start.linkTo(cover.start, 24.dp)
                        },
                    onClick = { }
                ) {
                    Image(
                        painter = painter,
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }

                Text(
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(cover.bottom, 16.dp)
                            start.linkTo(picture.end, 24.dp)
                            end.linkTo(parent.end, 24.dp)
                            width = Dimension.fillToConstraints
                        },
                    text = movie?.title ?: "",
                    color = white,
                    style = MaterialTheme.typography.h3
                )

                Text(
                    modifier = Modifier
                        .constrainAs(releaseDate) {
                            top.linkTo(title.bottom, 8.dp)
                            start.linkTo(title.start)
                        },
                    text = movie?.release_date ?: "",
                    color = Color.Gray,
                    style = MaterialTheme.typography.body1
                )
            }

            TopAppBar(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = (1 - progress)
                    },
                title = {
                    Text(
                        text = movie?.title ?: "",
                        fontFamily = dmSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = white,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    ) },
                backgroundColor = black,
                navigationIcon = {
                    IconButton(
                        onClick = { pressOnBack() }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = white
                        )
                    }
                },
                elevation = 0.dp
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .verticalScroll(rememberScrollState())
        ) {
            MovieDetailsText(movie)
            MovieDetailsText(movie)
            //MovieDetailsTrailers(movie)
            //MovieDetailsCast(movie)
        }
    }
}