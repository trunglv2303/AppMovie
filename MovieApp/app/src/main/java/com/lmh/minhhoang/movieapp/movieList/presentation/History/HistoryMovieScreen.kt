package com.lmh.minhhoang.movieapp.movieList.presentation.History

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lmh.minhhoang.movieapp.R

@Composable
fun HistoryMovieScreen() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
    ) {
        items(count = 10) { itemIndex ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {

                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(165.dp)
                )
                Text(
                    text = "$itemIndex",
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(Alignment.BottomStart)
                )
            }
        }
    }
}