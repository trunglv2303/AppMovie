package com.lmh.minhhoang.movieapp.core.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.lmh.minhhoang.movieapp.core.presentation.HomeScreens
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignInMainScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignInScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignUpMainScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignUpScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.WelcomeMainScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.WelcomeSceen
import com.lmh.minhhoang.movieapp.movieList.presentation.CategoryMovieScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.MovieDetailScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.ProfileScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Reel.MyReelScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Reel.ReelScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Reel.ReelsView
import com.lmh.minhhoang.movieapp.movieList.presentation.Search.SearchScreen
import com.lmh.minhhoang.movieapp.movieList.util.Screen
import com.lmh.minhhoang.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPaySDK

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(UnstableApi::class)
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
                    NavHost(navController = navController, startDestination = Screen.Welcome.rout)
                    {
                        composable(Screen.Home.rout) {
                            HomeScreens(navController)
                        }
                        composable(Screen.SignIn.rout)
                        {
                            SignInMainScreen(navController)
                        }
                        composable(Screen.SignUp.rout)
                        {
                            SignUpMainScreen(navController)
                        }
                        composable(Screen.PostReel.rout)
                        {
                            ReelScreen(navController = navController, storageReference =storageReference )
                        }
                        composable(Screen.ListReel.rout) {
                            ReelsView(
                            )
                        }
                        composable(Screen.MyReel.rout) {
                            MyReelScreen(navController)
                        }
                        composable(Screen.Welcome.rout) {
                            WelcomeMainScreen(navController)
                        }
                        composable(Screen.Profile.rout)
                        {
                            ProfileScreen(navController)
                        }
                        composable(Screen.Search.rout)
                        {
                            SearchScreen(navController)
                        }
                        composable(Screen.Details.rout + "/{id}",
                            arguments = listOf(
                                navArgument("id"){type= NavType.StringType}
                            )
                        ) {
                            val movieId = it.arguments?.getString("id")
                            Log.d("MovieDetailScreen", "Movie ID: $movieId")

                            MovieDetailScreen(navController,movieId)
                        }
                        composable(Screen.CategoryMovie.rout + "/{id}",
                            arguments = listOf(
                                navArgument("id"){type= NavType.StringType}
                            )
                        ) {
                            val categoryID = it.arguments?.getString("id")
                            Log.d("MovieDetailScreen", "Category ID: $categoryID")
                            CategoryMovieScreen(navController,categoryID)
                        }

                    }
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
