package com.lmh.minhhoang.movieapp.movieList.presentation

import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies
import com.lmh.minhhoang.movieapp.movieList.domain.model.User
import com.lmh.minhhoang.movieapp.movieList.util.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.SimpleTimeZone

@OptIn(UnstableApi::class)
@Composable
fun MovieDetailScreen(navController: NavController, movieId: String?) {
    var title by remember { mutableStateOf("") }
    var poster by remember { mutableStateOf("") }
    var language by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var video by remember { mutableStateOf<String?>(null) } // Video URL được giữ trong mutable state

    // Check if movieId is null
    if (movieId != null) {
        val db = Firebase.firestore
        val moviesCollection = db.collection("movies")
        val query = moviesCollection.whereEqualTo("id", movieId)

        // Fetch data asynchronously using coroutines (recommended)
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val document = query.get().await().documents.firstOrNull()
                if (document != null) {
                    val movie = document.toObject<Movies>()?.copy(
                        title = document.getString("name_movie") ?: "",
                        poster_path = document.getString("image") ?: "",
                        language_movie = document.getString("language_movie") ?: "language_movie",
                        time_movie = document.getString("time_movie") ?: "language_movie",
                        video = document.getString("url_phim") ?: ""
                    )
                    if (movie != null) {
                        title = movie.title
                        poster = movie.poster_path
                        language = movie.language_movie
                        time = movie.time_movie
                        video = movie.video
                    }
                }
            } catch (e: Exception) {
                // Handle exceptions here
                Log.e("MovieDetailScreen", "Error fetching movie details", e)
            }
        }
    } else {
        // Handle null movieId
        // You can choose to navigate back or show an error message
        navController.popBackStack()
        return
    }

    // Thêm null-check cho video URL trước khi tạo ExoPlayer
    val context = LocalContext.current
    if (video != null) {
        val player = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(video!!))
        }
        val playerView = PlayerView(context)
        val playWhenReady by rememberSaveable {
            mutableStateOf(true)
        }

        playerView.player = player

        LaunchedEffect(player) {
            player.prepare()
            player.playWhenReady = playWhenReady
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    factory = {
                        playerView
                    })
                Text(text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp))
                Row()
                {
                    Text("")
                }
            }
        }
    }
}
