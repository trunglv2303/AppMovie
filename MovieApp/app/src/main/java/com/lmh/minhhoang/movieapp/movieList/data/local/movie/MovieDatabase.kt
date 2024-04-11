package com.lmh.minhhoang.movieapp.movieList.data.local.movie

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [movieEntity::class],
    version = 1
)
abstract class MovieDatabase:RoomDatabase() {
    abstract val movieDao: MovieDao

}