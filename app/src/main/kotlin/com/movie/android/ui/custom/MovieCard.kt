package com.movie.android.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.movie.android.models.entities.Movie
import com.movie.android.network.Api
import com.movie.android.ui.feature.main.MainScreenHomeTab
import com.movie.android.ui.theme.black_5
import com.movie.android.ui.theme.black_50
import com.movie.android.ui.theme.white
import com.movie.android.ui.theme.white_50

/*
@Composable
@Preview
fun MovieCard(
    modifier: Modifier = Modifier,
    movieId: Int = 0,
    moviePosterPath: String = "",
    selectPoster: (MainScreenHomeTab, Int) -> Unit = {_,_ -> },
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                onClick = {
                    selectPoster(MainScreenHomeTab.TV, movieId)
                }
            )
    ) {

        val density = LocalDensity.current.density
        val width = remember { mutableStateOf(0f) }
        val height = remember { mutableStateOf(0f) }

        var palette by remember { mutableStateOf<Palette?>(null) }

        Crossfade(
            targetState = palette,
            modifier = Modifier
                .size(width.value.dp, height.value.dp)
        ) {

            Box(
                modifier = Modifier
                    .background(Color(it?.darkVibrantSwatch?.rgb ?: 0))
                    .alpha(0.7f)
                    .fillMaxSize()
            )
        }

        NetworkImage(
            networkUrl = Api.getPosterPath(moviePosterPath),
            modifier = Modifier
                .aspectRatio(0.6f)
                .onGloballyPositioned {
                    width.value = it.size.width / density
                    height.value = it.size.height / density
                },
            bitmapPalette = BitmapPalette {
                palette = it
            },
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .size(width.value.dp, height.value.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Black, Color.Transparent, Color.Black)
                    )
                )
        ){}
    }
}*/

@Composable
@Preview
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie = Movie(),
    selectPoster: (MainScreenHomeTab, Int) -> Unit = {_,_ -> }
) {
    val painter =
        rememberImagePainter(data = Api.getPosterPath(movie.poster_path))

    ConstraintLayout {

        val (card, title, option) = createRefs()

        Card(
            modifier = Modifier
                .size(120.dp, 180.dp)
                .constrainAs(card) {
                    top.linkTo(parent.top)
                },
            onClick = { selectPoster(MainScreenHomeTab.TV, movie.id ?: 0) },
            shape = RoundedCornerShape(8.dp),
            elevation = 0.dp
        ) {
            ConstraintLayout() {

                val (image, score) = createRefs()

                Image(
                    modifier = Modifier
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        },
                    painter = painter,
                    contentDescription = "Forest Image",
                    contentScale = ContentScale.Crop
                )

                Card(
                    modifier = Modifier
                        .constrainAs(score) {
                            top.linkTo(parent.top, 4.dp)
                            end.linkTo(parent.end, 4.dp)
                        },
                    backgroundColor = black_50,
                    shape = RoundedCornerShape(8.dp),
                    elevation = 0.dp
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp),
                        text = movie.vote_average.toString(),
                        color = white,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }

        Text(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(card.bottom, 8.dp)
                    start.linkTo(card.start)
                    end.linkTo(option.start)
                    width = Dimension.fillToConstraints
                },
            text = movie.title ?: "",
            color = white,
            style = MaterialTheme.typography.body2
        )

        IconButton(
            modifier = Modifier
                .constrainAs(option) {
                    top.linkTo(title.top)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(0.dp),
            onClick = {  }
        ) {
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = null
            )
        }
    }
}