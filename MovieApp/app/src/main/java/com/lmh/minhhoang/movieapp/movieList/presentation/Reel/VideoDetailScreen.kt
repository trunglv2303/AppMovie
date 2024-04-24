package com.lmh.minhhoang.movieapp.movieList.presentation.Reel

import androidx.annotation.OptIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.media3.common.util.UnstableApi
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.lmh.minhhoang.movieapp.movieList.designSystem.ReelVideoPlayer
import com.lmh.minhhoang.movieapp.movieList.domain.model.Reel
import kotlinx.coroutines.tasks.await


@OptIn(UnstableApi::class)
@Composable
fun VideoList() {

}