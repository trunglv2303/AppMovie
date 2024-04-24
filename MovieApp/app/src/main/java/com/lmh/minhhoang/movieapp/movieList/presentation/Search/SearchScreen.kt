package com.lmh.minhhoang.movieapp.movieList.presentation.Search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel

import com.lmh.minhhoang.movieapp.movieList.presentation.componentes.MovieItem

@Composable
fun SearchScreen ()
{
    val viewModel: SearchViewModel = viewModel()
    val searchText by viewModel.searchText.collectAsState()
    val movies by viewModel.movies.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Trường tìm kiếm
        OutlinedTextField(
            value = searchText,
            onValueChange = { viewModel.onSearchTextChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(movies) { movie ->
                MovieItem(movies = movies)
            }
        }
    }
}