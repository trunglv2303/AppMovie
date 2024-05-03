package com.lmh.minhhoang.movieapp.movieList.domain.model

import androidx.room.PrimaryKey

data class Movies(
    @PrimaryKey
    val id: String,
    val age_movie: Int,
    val backdrop_path: String,
    val genre_ids: String,
    val original_language: String,
    val poster_path: String,
    val title: String,
    val video: Boolean,
    val category:String
)
{
    constructor(
        id: String,
        title: String,
        poster_path: String,
    ) : this(
        id = id,
        age_movie = 0,
        backdrop_path = "",
        genre_ids = "",
        original_language = "",
        poster_path = poster_path,
        title = title,
        video = false,
        category = ""
    )

    fun doesMatchSearchQuery(query: String): Boolean {
        return title.contains(query, ignoreCase = true)
    }


}
