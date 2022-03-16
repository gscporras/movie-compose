package com.movie.android.ui.feature.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movie.android.R
import com.movie.android.models.entities.Movie
import com.movie.android.ui.custom.MovieDetailContent
import com.movie.android.ui.custom.MovieLoading
import com.movie.android.ui.theme.*

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    movieId: Int?,
    pressOnBack: () -> Unit = {}
) {
    val movie: Movie? by viewModel.movie.collectAsState()
    val isLoading: Boolean by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMovieDetailsById(movieId ?: 0)
    }

    when {
        isLoading -> MovieLoading()
        else -> MovieDetailContent(
            movie = movie,
            pressOnBack = pressOnBack
        )
    }
}

@Composable
fun MovieDetailsText(movie: Movie?) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 60.dp)
    ) {
        Text(
            text = "Summary",
            style = Typography.h2,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Text(
            text = movie?.overview ?: "",
            color = colorResource(id = R.color.white),
            modifier = Modifier.padding(8.dp),
            fontFamily = dmSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    }
}

/*
@Composable
fun MovieDetailsTrailers(movie: Movie?) {
    val videos: List<Video>? by viewModel.videoListFlow.collectAsState(initial = null)

    if(!videos.isNullOrEmpty()) {
        Column(Modifier.padding(top = 16.dp)) {
            val listState = rememberLazyListState()

            Text(
                text = "Trailers",
                color = Color.White,
                style = Typography.h2,
                modifier = Modifier.padding(8.dp)
            )
            LazyRow(
                state = listState,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(videos!!) { video ->
                    VideoThumbnail(video)
                }
            }
        }
    }
}

@Composable
fun VideoThumbnail(video: Video) {
    val context = LocalContext.current

    Surface(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp
    ) {

        ConstraintLayout(
            modifier = Modifier
                .size(320.dp, 180.dp)
                .clickable(
                    onClick = {
                        val playVideoIntent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(Api.getYoutubeVideoPath(video.key))
                            )
                        context.startActivity(playVideoIntent)
                    }
                )
        ) {
            val (thumbnail, icon, box, title) = createRefs()

            var palette by remember { mutableStateOf<Palette?>(null) }
            NetworkImage(
                networkUrl = Api.getYoutubeThumbnailPath(video.key),
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(thumbnail) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                bitmapPalette = BitmapPalette {
                    palette = it
                }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_youtube),
                contentDescription = null,
                modifier = Modifier
                    .height(50.dp)
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Crossfade(
                targetState = palette,
                modifier = Modifier
                    .height(25.dp)
                    .constrainAs(box) {
                        bottom.linkTo(parent.bottom)
                    }
            ) {

                Column(
                    Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black, Color.Black)
                            )
                        )) {}

                Box(
                    modifier = Modifier
                        .background(Color(it?.darkVibrantSwatch?.rgb ?: 0))
                        .alpha(0.7f)
                        .height(0.dp)
                )
            }

            Text(
                text = video.name ?: "",
                style = Typography.body2,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.85f)
                    .padding(horizontal = 8.dp)
                    .constrainAs(title) {
                        top.linkTo(box.top)
                        bottom.linkTo(box.bottom)
                    }
            )
        }
    }
}

@Composable
fun MovieDetailsCast(viewModel: MovieDetailViewModel) {
    val casts: List<Cast>? by viewModel.castListFlow.collectAsState(initial = null)

    casts?.let {
        val listState = rememberLazyListState()
        Column(Modifier.padding(top = 8.dp)) {
            Text(
                text = "Cast",
                color = colorResource(id = R.color.white),
                style = Typography.h2,
                modifier = Modifier.padding(8.dp)
            )
            LazyRow(
                state = listState,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(it) { cast ->
                    if(cast.known_for_department == "Acting") {
                        Actor(cast)
                    }
                }
            }
        }
    }
}

@Composable
fun Actor(cast: Cast) {
    Column(modifier = Modifier
        .width(120.dp)
        .padding(8.dp)
    ) {
        NetworkImage(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(CircleShape),
            networkUrl = Api.getPosterPath(cast.profile_path),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = cast.name,
            style = Typography.body1,
            fontSize = 12.sp,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            text = cast.character,
            style = Typography.body1,
            fontSize = 10.sp,
            color = colorResource(id = R.color.white_50),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}
*/
