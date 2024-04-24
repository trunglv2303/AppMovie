package com.lmh.minhhoang.movieapp.movieList.domain.reponsitory

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.R
import com.lmh.minhhoang.movieapp.movieList.domain.model.Reel
import com.lmh.minhhoang.movieapp.movieList.domain.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReelRepository @Inject constructor() {
    fun getVideo() {
        var reelList=ArrayList<Reel>()
        Firebase.firestore.collection("Reel").get().addOnSuccessListener {
            var templist = ArrayList<Reel>()
            reelList.clear()
            for(i in it.documents)
            {
                var reel=i.toObject<Reel>()!!
                templist.add(reel)
            }
            reelList.addAll(templist)
        }
    }
}
