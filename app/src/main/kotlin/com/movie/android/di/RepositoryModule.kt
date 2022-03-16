package com.movie.android.di

import com.movie.android.network.service.MovieService
import com.movie.android.network.service.SearchService
import com.movie.android.network.service.TvService
import com.movie.android.repository.*
import com.movie.android.persistence.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideTvRepository(
        tvService: TvService,
        tvDao: TvDao
    ): TvRepository {
        return TvRepository(tvService, tvDao)
    }

    @Provides
    @ViewModelScoped
    fun provideMovieRepository(
        movieService: MovieService,
        movieDao: MovieDao
    ): MovieRepository {
        return MovieRepository(movieService, movieDao)
    }

    @Provides
    @ViewModelScoped
    fun providePopularRepository(
        movieService: MovieService,
        popularDao: PopularDao
    ): PopularRepository {
        return PopularRepository(movieService, popularDao)
    }

    @Provides
    @ViewModelScoped
    fun provideTopRatedRepository(
        movieService: MovieService,
        topRatedDao: TopRatedDao
    ): TopRatedRepository {
        return TopRatedRepository(movieService, topRatedDao)
    }

    @Provides
    @ViewModelScoped
    fun provideSearchRepository(
        searchService: SearchService,
        searchDao: SearchDao
    ): SearchRepository {
        return SearchRepository(searchService, searchDao)
    }
}