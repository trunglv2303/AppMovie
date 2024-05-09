package com.lmh.minhhoang.movieapp.movieList.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies
import com.lmh.minhhoang.movieapp.movieList.presentation.Search.MovieItem
import com.lmh.minhhoang.movieapp.movieList.presentation.Search.SearchViewModel
import com.lmh.minhhoang.movieapp.movieList.util.Screen

@Composable
fun PopularMoviesScreen(navController: NavHostController) {
    val viewModel: SearchViewModel = viewModel()
    val movies by viewModel.movies.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        PopularMoviesGrid(
                movies = movies,
                navController = navController
            )
    }
}
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun PopularMoviesRow(movies: List<Movies>, navController: NavHostController) {
//    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)) {
//        items(movies) { movie ->
//            MovieItem(
//                movie = movie,
//                navController = navController,
//                modifier = Modifier.width(70.dp)
//                    .height(50.dp).clickable {
//                    navController.navigate("${Screen.Details.rout}/${movie.id}")
//                }
//            )
//        }
//    }
//}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularMoviesGrid(movies: List<Movies>, navController: NavHostController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
    ) {
        items(movies) { movie ->
            MovieItem(
                movie = movie,
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.75f)
                    .clickable {
                        navController.navigate("${Screen.Details.rout}/${movie.id}")
                    }
            )
        }
    }
}
