package com.lmh.minhhoang.movieapp.movieList.presentation

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import com.lmh.minhhoang.movieapp.movieList.presentation.Reel.VideoPlayerMovie
import com.lmh.minhhoang.movieapp.movieList.util.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.w3c.dom.Comment

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@kotlin.OptIn(ExperimentalMaterial3Api::class)
@OptIn(UnstableApi::class)
@Composable
fun MovieDetailScreen(navController: NavController, movieId: String?) {
    var title by remember { mutableStateOf("") }
    var poster by remember { mutableStateOf("") }
    var language by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var info_movie by remember { mutableStateOf("") }
    var video by remember { mutableStateOf<String?>(null) }
    var comment: String by remember {
        mutableStateOf("")
    }
    var isLoading by remember { mutableStateOf(false) }


    var comments by remember { mutableStateOf<List<Comments>>(emptyList()) }

    val username = AuthManager.getCurrentUserEmail()
    var playedVideos by remember { mutableStateOf(HashSet<String>()) }

    if (movieId != null) {
        val db = Firebase.firestore
        val moviesCollection = db.collection("movies")
        val query = moviesCollection.whereEqualTo("code_phim", movieId)

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
                        video = document.getString("url_phim") ?: "",
                        id = document.getString("id")?:"",
                    )
                    if (movie != null) {
                        title = movie.title
                        poster = movie.poster_path
                        language = movie.language_movie
                        info_movie = movie.time_movie
                        video = movie.video
                        id = movie.id
                    }
                }
            } catch (e: Exception) {
                Log.e("MovieDetailScreen", "Error fetching movie details", e)
            }
        }
// Fetch danh sách bình luận
        LaunchedEffect(movieId) {
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
                }
                comments = fetchedComments
            } catch (e: Exception) {
                Log.e("MovieDetailScreen", "Error fetching comments", e)
            }
        }
    } else {
        // Nếu không có id phim, quay về màn hình trước đó
        navController.popBackStack()
        return
    }
    val context = LocalContext.current
    if (video != null) {

        // Hiển thị dữ liệu
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Chi tiết phim")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate("main")
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Hiển thị video
                    item {
                        if (video != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(16f / 9)
                            ) {
                                var isPlaying by remember { mutableStateOf(!playedVideos.contains(video)) }
                                VideoPlayerMovie(uri = Uri.parse(video), isPlaying = isPlaying)
                                {
                                    isPlaying = !isPlaying
                                    if (isPlaying) {
                                        playedVideos.add(video!!)
                                    }
                                }
                            }
                        }

                    }

                    // Hiển thị thông tin phim
                    item {
                        Text(
                            text = title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            text = "Mô Tả",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            text = info_movie,
                            fontSize = 18.sp,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    // Hiển thị bình luận
                    item {
                        Text(
                            text = "Bình Luận",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            items(comments) { comment ->
                                Text(
                                    text = "${comment.emailUser}: ${comment.comments}",
                                    color = Color.White,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }

                    // Ô nhập bình luận
                    item {
                        var commentText by remember { mutableStateOf("") }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            TextField(
                                value = commentText,
                                onValueChange = { commentText = it },
                                label = {
                                    Text(
                                        text = "Nhập bình luận, $username",
                                        color = Color.Black
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                            )
                            IconButton(
                                onClick = {
                                    if (commentText.isEmpty()) {
                                        Toast.makeText(
                                            context,
                                            "Bạn chưa nhập bình luận",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        isLoading = true
                                        val db = Firebase.firestore
                                        val comments = db.collection("comments")
                                        val newComments = hashMapOf(
                                            "userName" to username,
                                            "comments" to commentText,
                                            "movieID" to movieId
                                        )
                                        comments.add(newComments)
                                            .addOnSuccessListener {
                                                // Đánh dấu rằng việc thêm bình luận đã thành công
                                                commentText = ""
                                                Toast.makeText(
                                                    context,
                                                    "Đã bình luận thành công",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                navController.navigate(Screen.Details.rout + "/${movieId}")
                                            }
                                            .addOnFailureListener { e ->
                                                // Xử lý khi thêm bình luận thất bại
                                                Toast.makeText(
                                                    context,
                                                    "Đã xảy ra lỗi khi gửi bình luận: ${e.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            .addOnCompleteListener {
                                                isLoading =
                                                    false // Đặt lại isLoading thành false khi thao tác hoàn tất
                                            }
                                    }
                                }
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Send,
                                        contentDescription = "Gửi",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}