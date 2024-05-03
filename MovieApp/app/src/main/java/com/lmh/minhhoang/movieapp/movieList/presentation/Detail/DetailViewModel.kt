package com.lmh.minhhoang.movieapp.movieList.presentation.Detail

import androidx.annotation.OptIn
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.google.firebase.firestore.FirebaseFirestore
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @OptIn(UnstableApi::class) @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.get<Int>("id")
    private val firestone = FirebaseFirestore.getInstance()

    private val _detailState = MutableStateFlow<DetailState>(DetailState())
    val detailState = _detailState.asStateFlow()

    init {
        movieId?.let {
            getMovie(it.toString())
        } ?: run {
            _detailState.value = DetailState(isLoading = false)
        }
        Log.d("DetailViewModel", "Movie ID: $movieId")
    }
    fun getMovie(id: String) {
        viewModelScope.launch {
            _detailState.value = DetailState(isLoading = true)
            try {
                val document = firestone.collection("movies").document(id).get().await()
                val movie = document.toObject(Movies::class.java)
                _detailState.value = DetailState(movie = movie)
            } catch (e: Exception) {
                _detailState.value = DetailState()
            }
        }
    }
}
