package com.movie.android.persistence

import androidx.room.*
import com.movie.android.models.entities.Tv

@Dao
interface TvDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvList(movies: List<Tv>)

    @Update
    suspend fun updateTv(movie: Tv)

    @Query("SELECT * FROM Tv WHERE id = :id_")
    suspend fun getTv(id_: Int): Tv

    @Query("SELECT * FROM Tv WHERE page = :page_")
    suspend fun getTvList(page_: Int): List<Tv>
}