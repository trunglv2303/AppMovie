package com.lmh.minhhoang.movieapp.movieList.presentation.Auth

data class SignInState(
    val isLoading : Boolean= false,
    val isSuccess : String? ="",
    val isError : String? =""
)