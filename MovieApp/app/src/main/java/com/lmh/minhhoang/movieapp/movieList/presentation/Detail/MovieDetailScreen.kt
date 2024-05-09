package com.lmh.minhhoang.movieapp.movieList.presentation

import android.net.Uri
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.di.AuthManager
import com.lmh.minhhoang.movieapp.movieList.domain.model.Comments
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies
import com.lmh.minhhoang.movieapp.movieList.presentation.Reel.VideoPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.w3c.dom.Comment

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@OptIn(UnstableApi::class)
@Composable
fun MovieDetailScreen(navController: NavController, movieId: String?) {
    var title by remember { mutableStateOf("") }
    var poster by remember { mutableStateOf("") }
    var language by remember { mutableStateOf("") }
    var info_movie by remember { mutableStateOf("") }
    var video by remember { mutableStateOf<String?>(null) }
    var comment: String by remember {
        mutableStateOf("")
    }


    var comments by remember { mutableStateOf<List<Comments>>(emptyList()) }

    val username = AuthManager.getCurrentUserEmail()
    var playedVideos by remember { mutableStateOf(HashSet<String>()) }


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
                        info_movie = document.getString("info_movie") ?: "language_movie",
                        video = document.getString("url_phim") ?: ""
                    )
                    if (movie != null) {
                        title = movie.title
                        poster = movie.poster_path
                        language = movie.language_movie
                        info_movie = movie.time_movie
                        video = movie.video
                    }
                }
            } catch (e: Exception) {
                Log.e("MovieDetailScreen", "Error fetching movie details", e)
            }
        }
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val db = Firebase.firestore
                val commentsCollection = db.collection("comments")
                val query = commentsCollection.whereEqualTo("movieID", movieId)
                val querySnapshot = query.get().await()
                val fetchedComments = mutableListOf<Comments>()
                for (document in querySnapshot.documents) {
                    val fetchedComment = document.toObject<Comments>()?.copy(
                        comments = document.getString("comments") ?: "",
                        emailUser = document.getString("userName") ?: "",
                    )
                    if (fetchedComment != null) {
                        fetchedComments.add(fetchedComment)
                    }
                    Log.d("abb", "$fetchedComment")
                }
                comments = fetchedComments
            } catch (e: Exception) {
                Log.e("MovieDetailScreen", "Error fetching comments", e)
            }
        }
    } else {
        navController.popBackStack()
        return
    }
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(horizontal = 16.dp)
        ) {
            // Video Player Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Tăng chiều cao của phần Video Player
                    .clip(RoundedCornerShape(16.dp))
            ) {
                val isPlaying = !playedVideos.contains(video)
                VideoPlayer(uri = Uri.parse(video), isPlaying = isPlaying) {
                    if (isPlaying) {
                        playedVideos.add(video!!)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Thêm khoảng cách giữa các phần

            // Movie Information Section
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White, // Màu văn bản trắng
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp)) // Thêm khoảng cách
                Text(
                    "Mô Tả",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White, // Màu văn bản trắng
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp)) // Thêm khoảng cách
                Text(
                    "$info_movie",
                    fontSize = 18.sp,
                    color = Color.White, // Màu văn bản trắng
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) // Thêm khoảng cách giữa các phần

            Text(
                text = "Bình Luận",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White, // Màu văn bản trắng
                modifier = Modifier.padding(start = 16.dp)
            )

            // Comment Section
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp) // Thêm khoảng cách ngang
            ) {
                items(comments) { comment ->
                    Text(
                        text = "${comment.emailUser}: ${comment.comments}",
                        color = Color.White, // Màu văn bản trắng
                        modifier = Modifier.padding(vertical = 8.dp) // Thêm khoảng cách dọc
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), // Đặt khoảng cách ngang
                verticalAlignment = Alignment.CenterVertically // Canh giữa dọc
            ) {
                TextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = {
                        Text(
                            "Nhập bình luận, $username",
                            color = Color.Black
                        )
                    }, // Màu văn bản trắng

                    modifier = Modifier.weight(1f) ,

                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (comment.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Bạn chưa nhập bình luận",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val db = Firebase.firestore
                            val comments = db.collection("comments")
                            val newComments = hashMapOf(
                                "userName" to username,
                                "comments" to comment,
                                "movieID" to movieId
                            )
                            comments.add(newComments)
                            comment = ""
                            Toast.makeText(
                                context,
                                "Đã bình luận thành công",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.Send,
                        contentDescription = "Submit",
                        tint = Color.White
                    ) // Icon submit
                }
            }
        }
    }
}