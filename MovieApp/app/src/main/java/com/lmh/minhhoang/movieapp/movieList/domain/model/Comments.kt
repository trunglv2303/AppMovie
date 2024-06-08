package com.lmh.minhhoang.movieapp.movieList.domain.model

data class Comments(
    val comments : String="",
    val movieID : String="",
    val emailUser : String="",
)
{
    constructor():this(
        comments ="",
        movieID ="",
        emailUser="",
    )

}