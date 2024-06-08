package com.lmh.minhhoang.movieapp.movieList.presentation.Search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun SearchScreen(navController: NavHostController) {
    val viewModel: SearchViewModel = viewModel()
    val searchText by viewModel.searchText.collectAsState()
    val movies by viewModel.movies.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Search field
        OutlinedTextField(
            value = searchText,
            onValueChange = { viewModel.onSearchTextChange(it) },
            label = {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Email Icon",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Nhập phim cần tìm kiếm")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ) {
            items(movies) { movie ->
                MovieItem(movie = movie, modifier = Modifier, navController = navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
