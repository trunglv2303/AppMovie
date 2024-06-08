package com.lmh.minhhoang.movieapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.movieList.domain.model.User

object AuthManager {
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val firestore: FirebaseFirestore by lazy { Firebase.firestore }

    private var userPower: String? = null
    fun getCurrentUserEmail(): String? {
        return auth.currentUser?.email
    }
    fun fetchUserPower(completion: (String?) -> Unit) {
        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            val userRef = firestore.collection("User").document(currentUser.uid)
            userRef.get().addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject<User>()
                userPower = documentSnapshot.getString("power");
                completion(userPower)
            }.addOnFailureListener { exception ->
                completion(null)
            }
        } else {
            completion(null)
        }
    }

}