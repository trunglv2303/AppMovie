package com.lmh.minhhoang.movieapp.movieList.presentation.Detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
@Composable
fun MovieDetailScreen(navController: NavController) {
    val viewModel: DetailViewModel = hiltViewModel()
    val movieId = remember {
        navController.previousBackStackEntry?.arguments?.getInt("id")
    }

    LaunchedEffect(movieId) {
        movieId?.let {
            viewModel.getMovie(it.toString())
        }
    }

    val detailState by viewModel.detailState.collectAsState()

        if (detailState.movie != null) {
            val movie = detailState.movie
            Column {
                Text(text = "Title: ${movie?.title}")
            }
        } else {
            Text("Movie not found")
        }
}
