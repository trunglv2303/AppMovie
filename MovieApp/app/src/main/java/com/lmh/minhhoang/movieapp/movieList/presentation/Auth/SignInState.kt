package com.lmh.minhhoang.movieapp.movieList.presentation.Auth

data class SignInState(
    val isLoading : Boolean= true,
    val isSuccess : String? ="",
    val isError : String? =""

)