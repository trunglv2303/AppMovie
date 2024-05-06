package com.lmh.minhhoang.movieapp.movieList.presentation.Detail

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
) : ViewModel() {
    private val firestore = Firebase.firestore
    private val moviesCollection = firestore.collection("movies")

    @OptIn(UnstableApi::class) suspend fun getMovieById(movieId: String): Movies? {
        val query = moviesCollection.whereEqualTo("id", movieId).limit(1)
        return try {
            val snapshot = query.get().await()
            val document = snapshot.documents.firstOrNull()
            val title = document?.getString("title")
            Log.d("abc","$title")
            document?.toObject<Movies>()
        } catch (e: Exception) {
            // Handle error
            null
        }
    }
}
