package com.lmh.minhhoang.movieapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.movieList.domain.model.User

object AuthManager {
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    fun getCurrentUserEmail(): String? {
        return auth.currentUser?.email
    }
}