package com.lmh.minhhoang.movieapp.movieList.domain.model

data class Category(
    var id : String,
    var name_type : String,
)
{
    constructor():this(
        id ="",
        name_type ="",
    )

}