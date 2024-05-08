package com.lmh.minhhoang.movieapp.movieList.presentation.Comments

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.movieList.domain.model.Comments
import com.lmh.minhhoang.movieapp.movieList.domain.model.Movies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(UnstableApi::class) @Composable
fun CommentItem (MovieId:String)
{
    var email by remember {
        mutableStateOf("")
    }
    var comment: String by remember {
        mutableStateOf("")
    }
    if (MovieId != null) {
        val db = Firebase.firestore
        val moviesCollection = db.collection("comments")
        val query = moviesCollection.whereEqualTo("movieId", MovieId)

        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val document = query.get().await().documents.firstOrNull()
                if (document != null) {
                    val comments = document.toObject<Comments>()?.copy(
                        comments = document.getString("comment") ?: "",
                        emailUser = document.getString("userEmail") ?:"",
                    )
                    if (comments != null) {
                        email  = comments.emailUser
                        comment = comments.comments

                    }
                }
            } catch (e: Exception) {
                Log.e("MovieDetailScreen", "Error fetching movie details", e)
            }
        }
    }
    Column()
    {
        Text(text = "$email: $comment")
    }
}