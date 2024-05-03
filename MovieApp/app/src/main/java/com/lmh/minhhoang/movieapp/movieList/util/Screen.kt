package com.lmh.minhhoang.movieapp.movieList.util

sealed class Screen(val rout: String) {
    object Home : Screen("main")
    object PopularMovieList : Screen("popularMovie")
    object UpcomingMovieList : Screen("upcomingMovie")
    object Details : Screen("Details")
    object SignIn : Screen("SignIn")
    object SignUp : Screen("SignUp")
    object Profile : Screen("profile")
    object History : Screen("History")
    object PostReel:Screen("PostReel")
    object ListReel:Screen("ListReel")
    object  Search :Screen("Search")
}