package com.lmh.minhhoang.movieapp.movieList.domain.model

data class Comments(
    var comments : String,
    var movieID : String,
    var emailUser : String,
)
{
    constructor():this(
        comments ="",
        movieID ="",
        emailUser="",
    )

}