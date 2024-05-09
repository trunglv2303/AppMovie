package com.lmh.minhhoang.movieapp.movieList.presentation.Auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.lmh.minhhoang.movieapp.movieList.domain.reponsitory.AuthRespository
import com.lmh.minhhoang.movieapp.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository :AuthRespository,
): ViewModel() {
    val _signinState = Channel<SignInState>()
    val signInState = _signinState.receiveAsFlow()

    fun loginUser(email:String, password:String) = viewModelScope.launch {
         val navController: NavController
        repository.loginUser(email,password).collect{
            result->
            when(result)
            {
                is Resource.Success->{
                    _signinState.send(SignInState(isSuccess = "Sign In Success"))
                }
                is Resource.Loading->{
                    _signinState.send(SignInState(isLoading = true))
                }
                is Resource.Error->{
                    _signinState.send(SignInState(isError = "Sign In Error"))
                }

            }
        }
    }
}