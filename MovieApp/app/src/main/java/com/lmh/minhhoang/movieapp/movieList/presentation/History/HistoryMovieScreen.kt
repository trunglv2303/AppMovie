package com.lmh.minhhoang.movieapp.movieList.presentation.History

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.R
import com.lmh.minhhoang.movieapp.di.AuthManager
import com.lmh.minhhoang.movieapp.movieList.domain.model.Comments
import com.lmh.minhhoang.movieapp.movieList.domain.model.History
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies
import com.lmh.minhhoang.movieapp.movieList.util.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(UnstableApi::class)
@Composable
fun HistoryMovieScreen(navController:NavController) {
    var history by remember { mutableStateOf<List<History>>(emptyList()) }
    val email = AuthManager.getCurrentUserEmail()
    rememberCoroutineScope().launch(Dispatchers.IO) {
        try {
            val db = com.google.firebase.ktx.Firebase.firestore
            val historyCollection = db.collection("history")
            val query = historyCollection.whereEqualTo("userName", email)
            val querySnapshot = query.get().await()
            val fetchedHistory = mutableListOf<History>()
            for (document in querySnapshot.documents) {
                val fetchedComment = document.toObject<History>()?.copy(
                    title = document.getString("title") ?: "",
                    image = document.getString("image") ?: "",
                    movieID = document.getString("movieID")?:"",
                    email = document.getString("userName")?:"",
                )
                if (fetchedComment != null) {
                    fetchedHistory.add(fetchedComment)
                }
            }
            history = fetchedHistory
        } catch (e: Exception) {
            androidx.media3.common.util.Log.e("MovieDetailScreen", "Error fetching comments", e)
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
    ) {
        items(history) { historyItem ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                val imageState = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(historyItem.image)
                        .size(Size.ORIGINAL)
                        .build()
                ).state
                imageState.painter?.let { painter ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                            .height(250.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                navController.navigate(Screen.Details.rout + "/${historyItem.movieID}")
                            }
                            .background(Color.Black)
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painter,
                            contentDescription = historyItem.title,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}
