package com.movie.android.ui.feature.home.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.movie.android.models.entities.Movie
import com.movie.android.models.entities.Popular
import com.movie.android.models.entities.Tv
import com.movie.android.models.network.NetworkState
import com.movie.android.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val imageLoader: ImageLoader,
    private val movieRepository: MovieRepository
): ViewModel() {

    private val _loadingState: MutableState<NetworkState> = mutableStateOf(NetworkState.IDLE)
    val loadingState: State<NetworkState> get() = _loadingState

    val trendings: State<MutableList<Movie>> = mutableStateOf(mutableListOf())
    val trendingPageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)

    private val newTrendingFlow = trendingPageStateFlow.flatMapLatest {
        _loadingState.value = NetworkState.LOADING
        movieRepository.loadTrending(
            page = it,
            success = { _loadingState.value = NetworkState.SUCCESS },
            error = { _loadingState.value = NetworkState.ERROR }
        )
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            newTrendingFlow.collectLatest {
                trendings.value.addAll(it)
            }
        }
    }

    fun fetchNextTrendingPage() {
        if (loadingState.value != NetworkState.LOADING) {
            trendingPageStateFlow.value++
        }
    }
}