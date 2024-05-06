package com.lmh.minhhoang.movieapp.movieList.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.lmh.minhhoang.movieapp.movieList.presentation.Search.MovieItem
import com.lmh.minhhoang.movieapp.movieList.presentation.Search.SearchViewModel
import com.lmh.minhhoang.movieapp.movieList.util.Screen

/**
 * @author Android Devs Academy (Ahmed Guedmioui)
 */
@Composable
fun PopularMoviesScreen(
    navController: NavHostController,
) {

    val viewModel: SearchViewModel = viewModel()
    val movies by viewModel.movies.collectAsState()
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 200.dp),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ) {
            items(movies) { movie ->
                MovieItem(
                    movie = movie,
                    navController = navController,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .clickable {
                            navController.navigate("${Screen.Details.rout}/${movie.id}")
                        }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}