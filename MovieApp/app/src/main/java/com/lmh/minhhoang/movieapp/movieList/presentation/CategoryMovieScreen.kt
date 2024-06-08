package com.lmh.minhhoang.movieapp.movieList.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies
import com.lmh.minhhoang.movieapp.movieList.presentation.Search.MovieItem
import com.lmh.minhhoang.movieapp.movieList.util.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@androidx.annotation.OptIn(UnstableApi::class) @OptIn(ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun CategoryMovieScreen(navController: NavController, categoryID: String?) {
    val movies = remember { mutableStateOf<List<Movies>>(emptyList()) }

    if (categoryID != null) {
        val db = Firebase.firestore
        val moviesCollection = db.collection("movies")
        val query = moviesCollection.whereEqualTo("category_movie", categoryID)
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val documents = query.get().await().documents
                val movieList = documents.mapNotNull { document ->
                    document.toObject<Movies>()?.copy(
                        title = document.getString("name_movie") ?: "",
                        poster_path = document.getString("file_movie") ?: "",
                        id = document.getString("code_phim") ?: "",
                    )
                }
                movies.value = movieList
            } catch (e: Exception) {
                Log.e("MovieDetailScreen", "Error fetching movie details", e)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Thể Loại")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },

            content = {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(vertical = 59.dp, horizontal = 4.dp),
                ) {
                    items(movies.value) { movie ->
                        MovieItem(
                            movie = movie, navController = navController, modifier = Modifier
                            .clickable {
                                navController.navigate("${Screen.CategoryMovie.rout}/${movie.id}")
                            })
                    }
                }
            }
        )
    }

}

