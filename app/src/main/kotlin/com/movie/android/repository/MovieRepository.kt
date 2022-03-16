package com.movie.android.repository

import androidx.annotation.WorkerThread
import com.movie.android.models.Cast
import com.movie.android.models.Video
import com.movie.android.network.Api
import com.movie.android.network.service.MovieService
import com.movie.android.persistence.MovieDao
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class MovieRepository constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao
) : Repository {

    @WorkerThread
    fun loadTrending(page: Int, success: () -> Unit, error: () -> Unit) = flow {
        val response = movieService.fetchTrending(page, Api.LANGUAGE, Api.REGION)
        response.suspendOnSuccess {
            emit(data.results)
        }.onError {
            error()
        }.onException {
            error()
        }
    }.onCompletion { success() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadVideoList(id: Int) = flow<List<Video>> {
        val movie = movieDao.getMovie(id)
        var videos = movie.videos
        if (videos.isNullOrEmpty()) {
            movieService.fetchVideos(id)
                .suspendOnSuccess {
                    videos = data.results
                    movie.videos = videos
                    movieDao.updateMovie(movie)
                    emit(videos ?: listOf())
                }
        } else {
            emit(videos ?: listOf())
        }
    }.flowOn(Dispatchers.IO)



    @WorkerThread
    fun loadCastList(id: Int) = flow<List<Cast>> {
        val movie = movieDao.getMovie(id)
        var casts = movie.casts
        if (casts.isNullOrEmpty()) {
            movieService.fetchCasts(id)
                .suspendOnSuccess {
                    casts = data.cast
                    movie.casts = casts
                    movieDao.updateMovie(movie)
                    emit(casts ?: listOf())
                }
        } else {
            emit(casts ?: listOf())
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadMovieById(id: Int, start: () -> Unit, success: () -> Unit, error: () -> Unit) = flow {
        val response = movieService.fetchMovieId(id, Api.LANGUAGE, Api.REGION)
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            error()
        }.onException {
            error()
        }
    }.onStart { start() }.onCompletion { success() }.flowOn(Dispatchers.IO)
}