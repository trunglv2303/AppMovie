package com.lmh.minhhoang.movieapp.movieList.presentation.Detail

import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies

data class DetailState(
    val isLoading :Boolean= true,
    val movie: Movies?=null

)