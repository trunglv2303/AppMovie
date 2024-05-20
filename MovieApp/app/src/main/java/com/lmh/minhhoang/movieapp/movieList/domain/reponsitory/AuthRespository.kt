package com.lmh.minhhoang.movieapp.movieList.domain.reponsitory

import com.google.firebase.auth.AuthResult
import com.lmh.minhhoang.movieapp.movieList.util.Resource
import kotlinx.coroutines.flow.Flow


interface AuthRespository {
    fun loginUser(email: String ,password:String): Flow<Resource<AuthResult>>
    fun registerUser(email: String, password: String, id: String,power:String): Flow<Resource<AuthResult>>
}   