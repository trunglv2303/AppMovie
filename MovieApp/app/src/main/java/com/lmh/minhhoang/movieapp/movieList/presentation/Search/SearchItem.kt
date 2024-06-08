package com.lmh.minhhoang.movieapp.movieList.presentation.Search

import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.di.AuthManager
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies
import com.lmh.minhhoang.movieapp.movieList.domain.model.User
import com.lmh.minhhoang.movieapp.movieList.util.Screen

@kotlin.OptIn(ExperimentalFoundationApi::class)
@OptIn(UnstableApi::class) @Composable
fun MovieItem(movie: Movies, modifier: Modifier, navController: NavController) {
    val context = LocalContext.current
    val userName = AuthManager.getCurrentUserEmail();
    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if(movie.power=="VIP")
                    {
                        val userPower = AuthManager.fetchUserPower { userPower ->
                            if (userPower == "VIP") {
                                navController.navigate(Screen.Details.rout + "/${movie.id}")
                                val db = Firebase.firestore
                                val history = db.collection("history")

                                history.whereEqualTo("movieID", movie.id)
                                    .whereEqualTo("userName", userName)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        if (documents.isEmpty) {
                                            val newHistory = hashMapOf(
                                                "userName" to userName,
                                                "title" to movie.title,
                                                "image" to movie.poster_path,
                                                "movieID" to movie.id
                                            )
                                            history.add(newHistory)
                                        }
                                    }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Bạn cần phải nâng cấp tài khoản để xem $userPower",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    else{
                        navController.navigate(Screen.Details.rout + "/${movie.id}")
                        val db = Firebase.firestore
                        val history = db.collection("history")

                        history.whereEqualTo("movieID", movie.id)
                            .whereEqualTo("userName", userName)
                            .get()
                            .addOnSuccessListener { documents ->
                                if (documents.isEmpty) {
                                    val newHistory = hashMapOf(
                                        "userName" to userName,
                                        "title" to movie.title,
                                        "image" to movie.poster_path,
                                        "movieID" to movie.id
                                    )
                                    history.add(newHistory)
                                }
                            }
                    }
                },
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.height(300.dp), contentAlignment = Alignment.BottomCenter) {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.poster_path)
                        .size(Size.ORIGINAL)
                        .build()
                )
                Box {
                    Image(
                        painter = painter,
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                    )
                    if(movie.power=="VIP")
                    {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = "VIP",
                            tint = Color.Yellow,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                                .size(24.dp)
                        )
                    }
                }

                    Spacer(modifier = Modifier.height(8.dp))
        }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = movie.title,
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(1f, 1f),
                            blurRadius = 3f
                        )
                    ),
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}