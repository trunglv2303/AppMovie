package com.lmh.minhhoang.movieapp.movieList.presentation.Reel

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.movieList.domain.model.Reel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

val verticalPadding = 12.dp
val horizontalPadding = 10.dp

@Composable
fun ReelsView() {
    Box(Modifier.background(color = Color.Black)) {
        ReelsList()

        ReelsHeader()
    }
}

@Composable
fun ReelsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Reels", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 21.sp)
    }
}

@OptIn(UnstableApi::class) @Composable
fun ReelsList() {

    var reel by remember { mutableStateOf<List<Reel>>(emptyList()) }
    val firestore = Firebase.firestore
    var playedVideos by remember { mutableStateOf(HashSet<String>()) }
    rememberCoroutineScope().launch(Dispatchers.IO) {
        try {
            val snapshot = firestore.collection("Reel").get().await()
            val fetchedReel = mutableListOf<Reel>()

            for (document in snapshot.documents) {
                val fetchedItem = document.toObject<Reel>()?.copy(
                    caption = document.getString("caption")?: "",
                    userName = document.getString("userName")?:"",
                    url = document.getString("url")?:""

                )
                if (fetchedItem != null) {
                    fetchedReel.add(fetchedItem)
                }
            }
            reel = fetchedReel
        } catch (e: Exception) {
            Log.e("Firestore", "Error getting documents: $e")
        }
    }

    LazyColumn(Modifier.fillMaxSize()) {
        items(reel) { reel ->
            Box(Modifier.fillParentMaxSize()) {
                val isPlaying = !playedVideos.contains(reel.url)
                VideoPlayer(uri = Uri.parse(reel.url), isPlaying = isPlaying) {
                    if (isPlaying) {
                        playedVideos.add(reel.url)
                    }
                }
                Column(Modifier.align(Alignment.BottomStart)) {
                    ReelFooter(reel)
                    Divider(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
fun ReelFooter(reel: Reel) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, bottom = 18.dp), verticalAlignment = Alignment.Bottom
    ) {
        FooterUserData(
            reel = reel,
            modifier = Modifier.weight(8f)
        )
    }
}

@Composable
fun FooterUserData(reel: Reel, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {

            Text(
                text = reel.userName,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        Spacer(modifier = Modifier.height(horizontalPadding))
        Text(text = reel.caption, color = Color.White)
        Spacer(modifier = Modifier.height(horizontalPadding))

    }
}