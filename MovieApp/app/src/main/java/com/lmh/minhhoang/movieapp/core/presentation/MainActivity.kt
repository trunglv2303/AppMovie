package com.lmh.minhhoang.movieapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.internal.StorageReferenceUri
import com.google.firebase.storage.ktx.storage
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignInScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignUpScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.ProfileScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Reel.ReelScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Reel.VideoList
import com.lmh.minhhoang.movieapp.movieList.presentation.Search.SearchScreen
import com.lmh.minhhoang.movieapp.movieList.util.Screen
import com.lmh.minhhoang.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                SetBarColor(color = MaterialTheme.colorScheme.inverseOnSurface)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   val navController = rememberNavController()
                    val storageReference = Firebase.storage.reference
                    NavHost(navController = navController, startDestination = Screen.SignIn.rout)
                    {
                        composable(Screen.Home.rout) {
                            HomeScreens(navController)
                        }
                        composable(Screen.SignIn.rout)
                        {
                            SignInScreen(navController)
                        }
                        composable(Screen.SignUp.rout)
                        {
                            SignUpScreen(navController)
                        }
                        composable(Screen.PostReel.rout)
                        {
                            ReelScreen(navController = navController, storageReference =storageReference )
                        }
                        composable(Screen.Profile.rout)
                        {
                            ProfileScreen(navController)
                        }
                        composable(Screen.VideoDetail.rout)
                        {
                            VideoList()
                        }
                        composable(Screen.Search.rout)
                        {
                            SearchScreen()
                        }
                        composable(Screen.Details.rout+"/{movieId}",
                            arguments = listOf(
                                navArgument("movieId"){type= NavType.IntType}
                            )
                        ){backStackEntry ->
//                            DetailsScreens(backStackEntry)
                        }
                    }
//                    SignUpScreen(navController)
                }
            }
        }
    }
    @Composable
    private fun SetBarColor(color:Color)
    {
        val systemUiController= rememberSystemUiController()
        LaunchedEffect(key1 = color){
            systemUiController.setSystemBarsColor(color)
        }
    }
}
