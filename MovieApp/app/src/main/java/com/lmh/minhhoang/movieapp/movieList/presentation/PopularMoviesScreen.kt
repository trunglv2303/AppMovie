package com.lmh.minhhoang.movieapp.movieList.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lmh.minhhoang.movieapp.movieList.presentation.Search.MovieItem
import com.lmh.minhhoang.movieapp.movieList.presentation.Search.SearchViewModel

/**
 * @author Android Devs Academy (Ahmed Guedmioui)
 */
@Composable
fun PopularMoviesScreen(
) {

    val viewModel: SearchViewModel = viewModel()
    val movies by viewModel.movies.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ) {
            items(movies) { movie ->
                MovieItem(movie = movie) // Pass individual movie
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

    }
}
