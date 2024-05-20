package com.lmh.minhhoang.movieapp.movieList.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CoPresent
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material.icons.rounded.VideoCall
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.R
import com.lmh.minhhoang.movieapp.core.presentation.BottomItem
import com.lmh.minhhoang.movieapp.di.AuthManager
import com.lmh.minhhoang.movieapp.movieList.domain.model.User
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignInState
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignInViewModel
import com.lmh.minhhoang.movieapp.movieList.presentation.History.HistoryMovieScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Reel.MyReelScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Reel.ReelScreen
import com.lmh.minhhoang.movieapp.movieList.util.Screen
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun ProfileScreen(
    navController: NavHostController,
) {
    var name by rememberSaveable() {
        mutableStateOf("")
    }
    var id by rememberSaveable() {
        mutableStateOf("")
    }
    val username = AuthManager.getCurrentUserEmail()
    val context = LocalContext.current
    val currentUser = Firebase.auth.currentUser
    val centerNavController = rememberNavController()
    if (currentUser != null) {
        Firebase.firestore.collection("User").document(currentUser.uid).get()
            .addOnSuccessListener { document ->
                val user: User? = document.toObject<User>()
                user?.let {
                    name = it.email ?: "Bạn chưa đăng nhập"
                    id = it.id.toString()
                }
            }
    }
    Box(modifier = Modifier.fillMaxSize())
    {
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = null,
                    modifier = Modifier.size(55.dp)
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        "$username",
                        modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "$id",
                        modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Button(
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate("SignIn")
                        Toast.makeText(context, "Đã đăng xuất thành công", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .padding(top = 15.dp, start = 10.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7C9A92)
                    ),
                ) {
                    Icon(
                        Icons.Rounded.ExitToApp,
                        contentDescription = "Exit",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            CenterNavigationBar(centerNavController = centerNavController)
            Spacer(modifier = Modifier.height(20.dp))
            NavHost(
                navController = centerNavController,
                startDestination = Screen.History.rout,
                ) {
                composable(Screen.History.rout) {
                    HistoryMovieScreen(navController)
                }
                composable(Screen.MyReel.rout) {
                    MyReelScreen(navController)
                }
            }
        }
    }
}
@Composable
fun CenterNavigationBar(
    centerNavController: NavHostController,
)
{
    val item = listOf(
        BottomItem(
            title = "Lịch sử",
            icon=   Icons.Rounded.History
        ),
        BottomItem(
            title = "Reel",
            icon=   Icons.Rounded.VideoCall
        ),

        )
    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        Row(
            modifier = Modifier
                .background(Color.White)
        )
        {
            item.forEachIndexed { index, bottomItem ->
                NavigationBarItem(selected = selected.intValue==index, onClick = {
                    selected.intValue = index
                    when (selected.intValue) {
                        0 -> {
                            centerNavController.popBackStack()
                            centerNavController.navigate(Screen.History.rout)
                        }

                        1 -> {
                            centerNavController.popBackStack()
                            centerNavController.navigate(Screen.MyReel.rout) // Change navigation to Screen.ListReel.rout
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
