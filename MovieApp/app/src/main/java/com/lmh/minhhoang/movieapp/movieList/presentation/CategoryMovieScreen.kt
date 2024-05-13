package com.lmh.minhhoang.movieapp.movieList.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies
import com.lmh.minhhoang.movieapp.movieList.util.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@androidx.annotation.OptIn(UnstableApi::class) @OptIn(ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun CategoryMovieScreen(navController: NavController, categoryID: String?) {
    val movies = remember { mutableStateOf<List<Movies>>(emptyList()) }

    if (categoryID != null) {
        val db = Firebase.firestore
        val moviesCollection = db.collection("movies")
        val query = moviesCollection.whereEqualTo("type", categoryID)
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val documents = query.get().await().documents
                val movieList = documents.mapNotNull { document ->
                    document.toObject<Movies>()?.copy(
                        title = document.getString("name_movie") ?: "",
                        poster_path = document.getString("image") ?: "",
                        id = document.getString("id") ?: "",
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
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
                ) {
                    items(movies.value) { movie ->
                        MovieItem(movie = movie, navController = navController, modifier = Modifier
                            .aspectRatio(0.75f)
                            .padding(4.dp)
                            .clickable {
                                navController.navigate("${Screen.CategoryMovie.rout}/${movie.id}")
                            })
                    }
                }
            }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieItem(movie: Movies,navController:NavController,modifier : Modifier) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .clickable {
                        navController.navigate(Screen.Details.rout + "/${movie.id}")
                        val db = Firebase.firestore
                        val history = db.collection("history")

                        history.whereEqualTo("movieID", movie.id)
                            .get()
                            .addOnSuccessListener { documents ->
                                if (documents.isEmpty) {
                                    val newHistory = hashMapOf(
                                        "title" to movie.title,
                                        "image" to movie.poster_path,
                                        "movieID" to movie.id
                                    )
                                    history.add(newHistory)
                                }
                            }
                    },
                contentAlignment = Alignment.BottomCenter
            ) {
                val imagePainter = rememberImagePainter(
                    data = movie.poster_path,
                    builder = {
                        size(Size.ORIGINAL)
                    }
                )
                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .height(260.dp)
                        .width(200.dp)
                        .clip(RoundedCornerShape(22.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.LightGray.copy(alpha = 0.7f),
                                    Color.LightGray.copy(alpha = 0.7f),
                                    Color.Transparent
                                )
                            ),
                            shape = RoundedCornerShape(22.dp)
                        )
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
                        .widthIn(max = 137.dp)
                ) {
                    Text(
                        text = movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            shadow = Shadow(
                                Color(0xFFFC6603), offset = Offset(1f, 1f), 3f
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Rounded.Timer, contentDescription = "")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "", // Thêm thông tin thời lượng phim ở đây
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            maxLines = 2
                        )
                    }
                }
            }

}
