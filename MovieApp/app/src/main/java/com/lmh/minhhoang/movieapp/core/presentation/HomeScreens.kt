package com.lmh.minhhoang.movieapp.core.presentation

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CoPresent
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.VideoCall
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.lmh.minhhoang.movieapp.movieList.presentation.PopularMoviesScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.ProfileScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Reel.ReelScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Reel.ReelsView
import com.lmh.minhhoang.movieapp.movieList.presentation.Search.SearchScreen
import com.lmh.minhhoang.movieapp.movieList.util.Screen

@androidx.annotation.OptIn(UnstableApi::class) @OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreens(navHostController:NavHostController) {
    val bottomNavController = rememberNavController()
    val storageReference = Firebase.storage.reference
    Scaffold(bottomBar = {
        BottomNavigationBar(
            bottomNavController = bottomNavController
        )
    },) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.PopularMovieList.rout
            ) {
                composable(Screen.PopularMovieList.rout) {
                    PopularMoviesScreen(navController = navHostController)
                }
                composable(Screen.Search.rout) {
                    SearchScreen(navHostController)
                }
                composable(Screen.PostReel.rout) {
                    ReelScreen(navController = navHostController, storageReference = storageReference)
                }
                composable(Screen.ListReel.rout) {
                    ReelsView(
                    )
                }
                composable(Screen.Profile.rout) {
                    ProfileScreen(navController = navHostController)
                }
            }
        }
    }

}

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController
)
{
    val item = listOf(
        BottomItem(
            title = "Popular",
            icon=   Icons.Rounded.Movie
        ),
        BottomItem(
            title = "Search",
            icon=   Icons.Rounded.Search
        ),
        BottomItem(
            title = "Post",
            icon=   Icons.Rounded.Add
        ),
        BottomItem(
            title = "Reel",
            icon=   Icons.Rounded.VideoCall
        ),
        BottomItem(
            title = "Profile",
            icon=   Icons.Rounded.CoPresent
        )

    )
    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        )
        {
            item.forEachIndexed { index, bottomItem ->
                NavigationBarItem(selected = selected.intValue==index, onClick = {
                    selected.intValue = index
                    when (selected.intValue) {
                        0 -> {
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.PopularMovieList.rout)
                        }
                        1->{
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.Search.rout)
                        }

                        2 -> {
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.PostReel.rout)
                        }
                        3->{
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.ListReel.rout)
                        }
                        4->{
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.Profile.rout)
                        }
                    }
                }, icon = {
                    Icon(
                        imageVector = bottomItem.icon,
                        contentDescription = bottomItem.title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }, label = {
                    Text(
                        text = bottomItem.title, color = MaterialTheme.colorScheme.onBackground
                    )
                })
            }
        }
    }
}
data class BottomItem( val title : String,
    val icon: ImageVector
)