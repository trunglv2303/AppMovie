package com.lmh.minhhoang.movieapp.movieList.presentation.Reel

import android.content.ContentValues.TAG
import android.net.Uri
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import com.google.firebase.storage.FirebaseStorage
import com.lmh.minhhoang.movieapp.R
import com.lmh.minhhoang.movieapp.di.AuthManager
import com.lmh.minhhoang.movieapp.movieList.domain.model.Comments
import com.lmh.minhhoang.movieapp.movieList.domain.model.History
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies
import com.lmh.minhhoang.movieapp.movieList.domain.model.Reel
import com.lmh.minhhoang.movieapp.movieList.util.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(UnstableApi::class) @Composable
fun MyReelScreen(navController:NavController) {
    var reel by remember { mutableStateOf<List<Reel>>(emptyList()) }
    val email = AuthManager.getCurrentUserEmail()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var playedVideos by remember { mutableStateOf(HashSet<String>()) }
    coroutineScope.launch(Dispatchers.IO) {
        try {
            val db = Firebase.firestore
            val reelCollection = db.collection("Reel")
            val query = reelCollection.whereEqualTo("userName", email)
            val querySnapshot = query.get().await()
            val fetchedReel = mutableListOf<Reel>()
            for (document in querySnapshot.documents) {
                val fetched = document.toObject<Reel>()?.copy(
                    url = document.getString("url") ?: "",
                    caption = document.getString("caption") ?: "",
                    userName = document.getString("userName") ?: "",
                    id = document.getString("id")?:""
                )
                if (fetched != null) {
                    fetchedReel.add(fetched)
                }
                Log.e("cdcdcdccdcd", "$fetchedReel")
            }
            reel = fetchedReel
        } catch (e: Exception) {
            Log.e("MovieDetailScreen", "Error fetching comments", e)
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
    ) {
        items(reel) { item ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .aspectRatio(16f / 9f) // Aspect ratio adjustment
            ) {
                val isPlaying = !playedVideos.contains(item.url)
                VideoPlayer(
                    uri = Uri.parse(item.url),
                    isPlaying = isPlaying,
                ) {
                    if (isPlaying) {
                        playedVideos.add(item.url)
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(onClick = {
                                val db = Firebase.firestore
                                val reelCollection = db.collection("Reel")
                                val query = reelCollection.whereEqualTo("id", item.id)
                                query.get().addOnSuccessListener { documents ->
                                    for (document in documents) {
                                        document.reference.delete()
                                    }
                                }
                                    .addOnSuccessListener {
                                        Toast.makeText(context,"Đã xóa thành công",Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener { exception ->
                                    // Handle any errors
                                   Toast.makeText(context,"Thất bại $exception",Toast.LENGTH_SHORT).show()
                                }


                            })
                    )
                }
            }
        }
    }
}