package com.lmh.minhhoang.movieapp.movieList.presentation.Reel

import android.provider.MediaStore.Video
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.lmh.minhhoang.movieapp.R
import com.lmh.minhhoang.movieapp.movieList.domain.model.Reel

@Composable
fun ListReelScreen (){
    val reels = remember { mutableStateListOf<Reel>() }
    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+"Reel").get().addOnSuccessListener {
        val fetchedReels = it.toObjects(Reel::class.java)
        reels.addAll(fetchedReels)
    }
//    LazyVerticalGrid(
//    columns = GridCells.Fixed(2),
//    modifier = Modifier.fillMaxSize(),
//    verticalArrangement = Arrangement.Center,
//    horizontalArrangement = Arrangement.Center,
//    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
//    ) {
//        items(reels) { reel  ->
//            Box(
//                modifier = Modifier
//                    .padding(8.dp)
//                    .clickable {
//
//                    }
//            ) {
//                Video(
//                    painter = painterResource(id = R.drawable.logo),
//                    contentDescription = null,
//                    modifier = Modifier.size(165.dp)
//                )
//            }
//        }
//    }
}