package com.lmh.minhhoang.movieapp.movieList.domain.reponsitory

import com.lmh.minhhoang.movieapp.movieList.domain.model.Movie
import com.lmh.minhhoang.movieapp.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListReponsitory {
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>
    suspend fun getMovie(id:Int):Flow<Resource<Movie>>
}