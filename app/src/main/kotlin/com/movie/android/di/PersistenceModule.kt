package com.movie.android.di

import android.content.Context
import androidx.room.Room
import com.movie.android.persistence.*
import com.movie.android.utils.APP_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideTvDao(appDatabase: AppDatabase): TvDao {
        return appDatabase.tvDao()
    }

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun providePopularDao(appDatabase: AppDatabase): PopularDao {
        return appDatabase.popularDao()
    }

    @Provides
    @Singleton
    fun provideTopRatedDao(appDatabase: AppDatabase): TopRatedDao {
        return appDatabase.topRatedDao()
    }

    @Provides
    @Singleton
    fun provideSearchDao(appDatabase: AppDatabase): SearchDao {
        return appDatabase.searchDao()
    }
}