package com.lmh.minhhoang.movieapp.movieList.presentation

sealed class MovieListUiEvent {
    data class Paginate(val category:String): MovieListUiEvent()
    object Navigation : MovieListUiEvent()
}