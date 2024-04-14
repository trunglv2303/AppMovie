package com.lmh.minhhoang.movieapp.movieList.data.remote

import com.lmh.minhhoang.movieapp.movieList.data.remote.respnod.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page:Int,
        @Query("api_key") apikey:String = API_KEY
    ): MovieListDto
    companion object{
        const val BASE_URL="https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL="https://image.tmdb.org/t/p/w500"
        const val API_KEY="e9e9d8da18ae29fc430845952232787c"
    }

}