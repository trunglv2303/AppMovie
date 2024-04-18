package com.lmh.minhhoang.movieapp.movieList.presentation.Auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.lmh.minhhoang.movieapp.movieList.domain.model.User
import com.lmh.minhhoang.movieapp.movieList.domain.reponsitory.AuthRespository
import com.lmh.minhhoang.movieapp.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository : AuthRespository
): ViewModel() {
    val _signupstate = Channel<SignInState>()
    val signUpState = _signupstate.receiveAsFlow()

    fun registerUser(email: String, password: String) = viewModelScope.launch {
        repository.registerUser(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val userId = Firebase.auth.currentUser?.uid
                    userId?.let { uid ->
                        val user = User(email= email, password = password)
                        Firebase.firestore.collection("User").document(uid).set(user)
                    }
                }
                is Resource.Loading -> {
                    _signupstate.send(SignInState(isLoading = true))
                }

                is Resource.Error -> {
                    _signupstate.send(SignInState(isError = "Sign Up Error"))
                }

            }
        }
    }
}