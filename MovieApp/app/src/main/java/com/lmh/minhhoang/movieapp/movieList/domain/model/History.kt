package com.lmh.minhhoang.movieapp.movieList.domain.model

data class History (
    var image : String,
    var movieID : String,
    var title : String,
    var email:String,
)
{
    constructor():this(
        image ="",
        movieID ="",
        title="",
        email=""
    )

}