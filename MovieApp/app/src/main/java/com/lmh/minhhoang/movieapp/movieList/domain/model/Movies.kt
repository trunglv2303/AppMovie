package com.lmh.minhhoang.movieapp.movieList.domain.model

import android.health.connect.datatypes.units.Power
import androidx.room.PrimaryKey

data class Movies(
    @PrimaryKey
    val id: String,
    val age_movie: String,
    val backdrop_path: String,
    val genre_ids: String,
    val original_language: String,
    val poster_path: String,
    val title: String,
    val video: String,
    val category: String,
    val code_phim: String,
    val file_movie: String,
    val image: String,
    val info_movie: String,
    val language_movie: String,
    val name_movie: String,
    val time_movie: String,
    val url_phim: String,
    val power: String,
) {
    constructor(id: String, title: String, poster_path: String,power: String) : this(
        id = id,
        age_movie = "",
        backdrop_path = "",
        genre_ids = "",
        original_language = "",
        poster_path = poster_path,
        title = title,
        video = "",
        category = "",
        code_phim = "",
        file_movie = "",
        image = "",
        info_movie = "",
        language_movie = "",
        name_movie = "",
        time_movie = "",
        url_phim = "",
        power=power,
    )

    constructor() : this(
        id = "",
        age_movie = "",
        backdrop_path = "",
        genre_ids = "",
        original_language = "",
        poster_path = "",
        title = "",
        video = "",
        category = "",
        code_phim = "",
        file_movie = "",
        image = "",
        info_movie = "",
        language_movie = "",
        name_movie = "",
        time_movie = "",
        url_phim = "",
        power=""
    )

    fun doesMatchSearchQuery(query: String): Boolean {
        return title.contains(query, ignoreCase = true)
    }
}

