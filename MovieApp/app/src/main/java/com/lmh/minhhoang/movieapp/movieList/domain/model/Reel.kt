package com.lmh.minhhoang.movieapp.movieList.domain.model

import android.net.Uri
import androidx.media3.common.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

data class Reel(
    val url: String,
    val userName: String,
    val caption: String,
) {
    constructor():this(
        url ="",
        userName ="",
        caption="",
    )
}

