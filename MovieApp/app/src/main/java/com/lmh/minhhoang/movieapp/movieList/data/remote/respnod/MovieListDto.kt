package com.lmh.minhhoang.movieapp.movieList.data.remote.respnod

data class MovieListDto(
    val dates: Dates,
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)