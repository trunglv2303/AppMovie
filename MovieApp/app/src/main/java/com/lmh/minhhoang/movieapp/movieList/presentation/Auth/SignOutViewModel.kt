package com.lmh.minhhoang.movieapp.movieList.presentation.Auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _signupstate.send(SignInState(isSuccess = "Sign In Success"))
                }

                is Resource.Loading -> {
                    _signupstate.send(SignInState(isLoading = true))
                }

                is Resource.Error -> {
                    _signupstate.send(SignInState(isError = "Sign In Error"))
                }

            }
        }
    }
}