package com.lmh.minhhoang.movieapp.movieList.presentation.Search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies

@Composable
fun MovieItem(movies: List<Movies>) {
    movies.forEach { movie ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Title: ${movie.title}")
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Category: ${movie.category}")
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Age: ${movie.age_movie}")
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
