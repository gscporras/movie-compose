package com.movie.android.ui.feature.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.movie.android.models.entities.Tv
import com.movie.android.models.network.NetworkState
import com.movie.android.models.network.onLoading
import com.movie.android.network.Api
import com.movie.android.network.compose.NetworkImage
import com.movie.android.ui.feature.main.MainScreenHomeTab
import com.movie.android.ui.feature.main.viewmodel.MainViewModel
import com.movie.android.extensions.paging
import com.movie.android.ui.custom.MovieCard
import com.movie.android.ui.feature.home.viewmodel.HomeViewModel
import com.movie.android.ui.theme.white

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    lazyListState: LazyListState,
    selectPoster: (MainScreenHomeTab, Int) -> Unit,
) {
    val networkState: NetworkState by viewModel.loadingState
    val trendings by viewModel.trendings

    val trendingState = rememberLazyListState()

    LazyColumn(
        state = lazyListState
    ) {
        item {
            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = "Tendencia",
                color = white
            )
        }
        item {

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                state = trendingState
            ) {

                paging(
                    items = trendings,
                    currentIndexFlow = viewModel.trendingPageStateFlow,
                    fetch = { viewModel.fetchNextTrendingPage() }
                ) {

                    MovieCard(
                        movie = it,
                        selectPoster = selectPoster
                    )
                }
            }

            networkState.onLoading {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .size(120.dp, 180.dp)
                ) {

                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(20.dp),
                    )
                }
            }
        }
    }
}