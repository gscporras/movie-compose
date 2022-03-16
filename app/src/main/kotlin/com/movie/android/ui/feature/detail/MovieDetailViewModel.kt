package com.movie.android.ui.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie.android.models.entities.Movie
import com.movie.android.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _movie = MutableStateFlow(Movie())
    val movie: StateFlow<Movie> get() = _movie

    fun fetchMovieDetailsById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.loadMovieById(
                id = id,
                start = { _isLoading.value = true },
                success = { _isLoading.value = false },
                error = { _isLoading.value = false }
            ).collect {
                _movie.value = it
            }
        }
    }
}