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
import androidx.compose.foundation.layout.absoluteOffset
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.CoPresent
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material.icons.rounded.VideoCall
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
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
    var expanded by remember { mutableStateOf(false) }
    var show by remember {
        mutableStateOf(false)
    }
    var id by rememberSaveable() {
        mutableStateOf("")
    }
    var power by rememberSaveable() {
        mutableStateOf("")
    }
    val username = AuthManager.getCurrentUserEmail()
    val context = LocalContext.current
    val currentUser = Firebase.auth.currentUser
    val centerNavController = rememberNavController()
    val ref=Firebase.firestore.collection("User").document(currentUser!!.uid)
    if (currentUser != null) {
        ref.get()
            .addOnSuccessListener { document ->
                val user: User? = document.toObject<User>()
                user?.let {
                    name = it.email ?: "Bạn chưa đăng nhập"
                    id = it.id.toString()
                    power = it.power.toString()

                }
            }
    }
    Box(modifier = Modifier.fillMaxSize())
    {
        Column() {
            if (show) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    IconButton(
                        onClick = { show = false },
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Red, CircleShape)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ima),
                            contentDescription = null,
                            modifier = Modifier.size(400.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Column( horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Text(
                                text = "Nâng cấp ngay",
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight(500),
                                    color = Color.White
                                ),
                            )
                            Text(
                                "Nâng cấp để có những bộ phim hấp dẫn mới nhất nhé và giúp mình có thêm động lực để nâng cấp App hoàn thiện hơn!",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily.Serif,
                                    color = Color(0xB2FFFFFF)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                val updates = hashMapOf<String, Any>(
                                "power" to "VIP"
                                )
                                ref.update(updates)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            context,
                                            "Tài khoản đã được nâng cấp",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(
                                            context,
                                            "Lỗi ${e.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                show = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Yellow,
                                contentColor = Color.Black
                            ),
                            modifier = Modifier.width(200.dp).height(50.dp)
                        ) {
                            Text("Nâng cấp")
                        }
                    }
                }
            }
            Row(
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = null,
                        modifier = Modifier.size(55.dp)
                    )
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
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "Trạng thái:$power",
                        modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .clickable { expanded = true } ,
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Rounded.Menu,
                                contentDescription = "Menu",
                                modifier = Modifier.size(24.dp),
                                tint = Color.White
                            )
                        }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        properties = PopupProperties(focusable = true)
                    ) {
                        Divider(color = Color.White, thickness = 1.dp)
                        DropdownMenuItem(
                            text = { Text("Tiêu chuẩn cộng đồng") },
                            onClick = { Toast.makeText(context, "Đang cập nhật trạng thái", Toast.LENGTH_SHORT).show() }
                        )

                        Divider(color = Color.White, thickness = 1.dp)
                        if(power == "Normal")
                        {
                            DropdownMenuItem(
                                text = { Text("Nâng cấp tài khoản") },
                                onClick = {
                                    show = true
                                    expanded = false
                                }
                            )
                            Divider(color = Color.White, thickness = 1.dp)
                        }
                        DropdownMenuItem(
                            text = { Text("Admin Lê Minh Hoàng") },
                            onClick = { Toast.makeText(context, "SDT liên hệ 0345377500", Toast.LENGTH_SHORT).show() }
                        )
                        Divider(color = Color.White, thickness = 1.dp)
                        DropdownMenuItem(
                            text = { Text("Admin Lê Văn Trung") },
                            onClick = { Toast.makeText(context, "SDT liên hệ 0384252407", Toast.LENGTH_SHORT).show() }
                        )
                        Divider(color = Color.White, thickness = 1.dp)
                        DropdownMenuItem(
                            text = { Text("Đăng xuất") },
                            onClick = { FirebaseAuth.getInstance().signOut()
                                navController.navigate("SignIn")
                                Toast.makeText(context, "Đã đăng xuất thành công", Toast.LENGTH_SHORT).show() }
                        )
                        Divider(color = Color.White, thickness = 1.dp)
                    }
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
