package com.lmh.minhhoang.movieapp.movieList.presentation.Search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies

@Composable
fun MovieItem(movie: Movies) {
    Box(modifier = Modifier.fillMaxSize()) {
        val defaultColor = MaterialTheme.colorScheme.secondaryContainer
        var dominantColor by remember { mutableStateOf(defaultColor) }
        Column(
            modifier = Modifier
                .wrapContentSize() // Adjust to content size
                .padding(8.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.secondaryContainer,
                            dominantColor
                        )
                    )
                )
                .clickable { }
        ) {
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
                        .height(250.dp)
                        .clip(RoundedCornerShape(22.dp)),
                    painter = it,
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                text = movie.title,
                color = Color.White,
                fontSize = 15.sp,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}