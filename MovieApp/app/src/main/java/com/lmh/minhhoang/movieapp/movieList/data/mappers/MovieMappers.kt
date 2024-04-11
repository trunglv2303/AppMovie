package com.lmh.minhhoang.movieapp.movieList.data.mappers

import com.lmh.minhhoang.movieapp.movieList.data.local.movie.movieEntity
import com.lmh.minhhoang.movieapp.movieList.data.remote.respnod.MovieDto
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movie

fun MovieDto.toMovieEntity(
    category: String
):movieEntity{
    return movieEntity(
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        original_language = original_language ?: "",
        overview = overview ?: "",
        poster_path = poster_path ?: "",
        release_date = release_date ?: "",
        title = title ?: "",
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        vote_count = vote_count ?: 0,
        id = id ?: -1,
        original_title = original_title ?: "",
        video = video ?: false,

        category = category,

        genre_ids = try {
            genre_ids?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        }
    )
}
fun movieEntity.toMovie(
    category: String
):Movie{
    return Movie(
        backdrop_path = backdrop_path,
        original_language = original_language,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_average = vote_average,
        popularity = popularity,
        vote_count = vote_count,
        video = video,
        id = id,
        adult = adult,
        original_title = original_title,
        category = category,
        genre_ids = try{
            genre_ids.split(",").map { it.toInt() }
        }
        catch(e:Exception)
        {
            listOf(-1,-2)
        }
    )
}