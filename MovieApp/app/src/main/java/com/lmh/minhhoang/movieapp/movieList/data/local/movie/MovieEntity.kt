package com.lmh.minhhoang.movieapp.movieList.data.local.movie

import androidx.room.PrimaryKey


data class movieEntity(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    @PrimaryKey
    val id: Int,
    val category:String
)