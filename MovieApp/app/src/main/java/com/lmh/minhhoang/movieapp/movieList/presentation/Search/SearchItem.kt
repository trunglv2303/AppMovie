package com.lmh.minhhoang.movieapp.movieList.presentation.Search

import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.di.AuthManager
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies
import com.lmh.minhhoang.movieapp.movieList.domain.reponsitory.AuthRespository
import com.lmh.minhhoang.movieapp.movieList.util.Screen

@kotlin.OptIn(ExperimentalFoundationApi::class)
@OptIn(UnstableApi::class) @Composable
fun MovieItem(movie: Movies, modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val userName = AuthManager.getCurrentUserEmail();
    Column(modifier = Modifier.fillMaxSize())
    {
        Card(
            Modifier
                .wrapContentSize()
//                .padding(10.dp)
                .clickable {
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
                },
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                val imageState = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.poster_path)
                        .size(Size.ORIGINAL)
                        .build()
                ).state
                imageState.painter?.let {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                            .height(260.dp)
                            .width(200.dp)
                            .clip(RoundedCornerShape(22.dp)),
                        painter = it,
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.LightGray.copy(alpha = 0.7f),
                            Color.LightGray.copy(alpha = 0.7f),
                            Color.Transparent
                        )
                    ),
                )
                .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
                .widthIn(max = 165.dp)
        )
        {
            Text(
                text = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                style = TextStyle(
                    shadow = Shadow(
                        Color(0xFFFC6603), offset = Offset(1f, 1f), 3f
                    )
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(Modifier.align(Alignment.End)) {
                Icon(imageVector = Icons.Rounded.Timer, contentDescription = "")
                Text(
                    text = movie.time_movie,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}