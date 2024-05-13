package com.lmh.minhhoang.movieapp.movieList.presentation

import android.R
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.lmh.minhhoang.movieapp.movieList.domain.model.Category
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies
import com.lmh.minhhoang.movieapp.movieList.presentation.Search.MovieItem
import com.lmh.minhhoang.movieapp.movieList.presentation.Search.SearchViewModel
import com.lmh.minhhoang.movieapp.movieList.util.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun PopularMoviesScreen(navController: NavHostController) {
    val viewModel: SearchViewModel = viewModel()
    val scrollState = rememberScrollState()
    val movies by viewModel.movies.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(horizontal = 12.dp)
    )
    {
        Spacer(modifier = Modifier.height(8.dp))
        Text("Sắp Ra Mắt",style=MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        banner(navController = navController)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Thể Loại",style=MaterialTheme.typography.titleMedium)
        PopularMoviesRow(navController = navController)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Phim mới",style=MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        PopularMoviesGrid(movies = movies, navController = navController)
    }
}
@Composable
fun banner(navController: NavHostController) {

    val images = listOf(
       "https://filesdata.cadn.com.vn/filedatacadn/media//data_news/Image/2021/th12/ng10/295van2.jpg",
        "https://designercomvn.s3.ap-southeast-1.amazonaws.com/wp-content/uploads/2017/07/26020157/poster-phim-kinh-di.jpg",
        "https://thietkeinanktp.com/wp-content/uploads/2022/01/poster-Bong-de.jpg"
    )
    val scrollState = rememberScrollState()
    Row(modifier = Modifier.horizontalScroll(scrollState)) {
        images.forEachIndexed { index, imageUrl ->
            Surface(
                modifier = Modifier
                    .padding(horizontal = 0.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                val imageState = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .size(Size.ORIGINAL)
                        .build()
                ).state
                imageState.painter?.let {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(22.dp)),
                        painter = it,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class) @OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularMoviesRow(navController: NavHostController) {

    var category by remember { mutableStateOf<List<Category>>(emptyList()) }
    rememberCoroutineScope().launch(Dispatchers.IO) {
        try {
            val db = com.google.firebase.ktx.Firebase.firestore
            val historyCollection = db.collection("type_movie")
            val querySnapshot = historyCollection.get().await()
            val fetchedCategory = mutableListOf<Category>()
            for (document in querySnapshot.documents) {
                val fetchedComment = document.toObject<Category>()?.copy(
                    id = document.getString("id") ?: "",
                    name_type = document.getString("name_type") ?: "",
                )
                if (fetchedComment != null) {
                    fetchedCategory.add(fetchedComment)
                }
            }
            category = fetchedCategory
        } catch (e: Exception) {
            Log.e("MovieDetailScreen", "Error fetching comments", e)
        }
    }
    val scrollState = rememberScrollState()
    Row(modifier = Modifier.horizontalScroll(scrollState)) {
        category.forEachIndexed { index, category ->
            Surface(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .clickable {
                        navController.navigate("${Screen.CategoryMovie.rout}/${category.id}")
                    },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Box(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = category.name_type,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}


@Composable
fun PopularMoviesGrid(movies: List<Movies>, navController: NavHostController) {
    LazyRow(
        contentPadding = PaddingValues(start = 12.dp)
    ) {
        items(movies) { movie ->
            MovieItem(
                movie = movie,
                navController = navController,
                modifier = Modifier
                    .aspectRatio(0.75f)
                    .padding(4.dp)
                    .clickable {
                        navController.navigate("${Screen.Details.rout}/${movie.id}")
                    }
            )
        }
    }
}

