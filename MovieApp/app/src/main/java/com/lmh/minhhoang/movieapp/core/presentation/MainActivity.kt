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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignInScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignUpScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.MovieListViewModel
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
//                    NavHost(navController = navController, startDestination = Screen.Home.rout)
//                    {
//                        composable(Screen.Home.rout){
//                            HomeScreens(navController)
//                        }
//                        composable(Screen.Details.rout+"/{movieId}",
//                            arguments = listOf(
//                                navArgument("movieId"){type= NavType.IntType}
//                            )
//                        ){backStackEntry ->
////                            DetailsScreens(backStackEntry)
//                        }
//                    }
                    SignInScreen(navController)
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
