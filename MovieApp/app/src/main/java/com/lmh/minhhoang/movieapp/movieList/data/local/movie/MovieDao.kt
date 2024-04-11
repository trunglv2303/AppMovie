package com.lmh.minhhoang.movieapp.movieList.data.local.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao  {
    @Upsert
    suspend fun upsertMovieList(movieList: List<movieEntity>)
    @Query("SELECT * FROM movieEntity WHERE id =  :id")
    suspend fun getMovieById(id:Int):movieEntity

    @Query("SELECT * FROM movieEntity WHERE category =  :category")
    suspend fun getMovieListByCategory(category:String):List<movieEntity>
}