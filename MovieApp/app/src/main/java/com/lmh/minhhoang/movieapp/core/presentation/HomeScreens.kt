package com.lmh.minhhoang.movieapp.core.presentation

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CoPresent
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Upcoming
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lmh.minhhoang.movieapp.R
import com.lmh.minhhoang.movieapp.movieList.presentation.MovieListUiEvent
import com.lmh.minhhoang.movieapp.movieList.presentation.MovieListViewModel
import com.lmh.minhhoang.movieapp.movieList.presentation.PopularMoviesScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.UpcomingMoviesScreen
import com.lmh.minhhoang.movieapp.movieList.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreens(navHostController:NavHostController) {
    val movieListViewModel= hiltViewModel<MovieListViewModel>()
    val movieListState = movieListViewModel.movieListState.collectAsState().value
    val bottomNavController = rememberNavController()
    Scaffold(bottomBar = {
        BottomNavigationBar(
            bottomNavController = bottomNavController, onEvent = movieListViewModel::onEvent
        )
    }, topBar = {
        TopAppBar(
            title = {
                Text(
                    text = if (movieListState.isCurrentPopularScreen)
                        stringResource(R.string.popular_movies)
                    else
                        stringResource(R.string.upcoming_movies),
                    fontSize = 20.sp
                )
            },
            modifier = Modifier.shadow(2.dp),
            colors = TopAppBarDefaults.smallTopAppBarColors(
                MaterialTheme.colorScheme.inverseOnSurface
            )
        )
    }) {
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
                    PopularMoviesScreen(movieListState = movieListState, navController = navHostController
                        , onEvent = movieListViewModel::onEvent)
                }
                composable(Screen.SearchMovieList.rout) {
//                    UpcomingMoviesScreen(movieListState = movieListState, navController = navHostController
//                        , onEvent = movieListViewModel::onEvent)
                }
                composable(Screen.UpcomingMovieList.rout) {
                    UpcomingMoviesScreen(movieListState = movieListState, navController = navHostController
                        , onEvent = movieListViewModel::onEvent)
                }
                composable(Screen.Profile.rout) {
//                    UpcomingMoviesScreen(movieListState = movieListState, navController = navHostController
//                        , onEvent = movieListViewModel::onEvent)
                }
            }
        }
    }

}

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController,
    onEvent: (MovieListUiEvent)->Unit
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
            title = "UpComming",
            icon=   Icons.Rounded.Upcoming
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
                            onEvent(MovieListUiEvent.Navigation)
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.PopularMovieList.rout)
                        }

                        1 -> {
                            onEvent(MovieListUiEvent.Navigation)
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.UpcomingMovieList.rout)
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