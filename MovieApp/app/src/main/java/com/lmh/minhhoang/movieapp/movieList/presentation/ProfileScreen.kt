package com.lmh.minhhoang.movieapp.movieList.presentation

import android.app.AlertDialog
import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
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
import com.lmh.minhhoang.movieapp.core.presentation.MainActivity
import com.lmh.minhhoang.movieapp.di.AuthManager
import com.lmh.minhhoang.movieapp.movieList.domain.model.CreateOrder
import com.lmh.minhhoang.movieapp.movieList.domain.model.User
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignInMainScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignInState
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignInViewModel
import com.lmh.minhhoang.movieapp.movieList.presentation.History.HistoryMovieScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Reel.MyReelScreen
import com.lmh.minhhoang.movieapp.movieList.presentation.Reel.ReelScreen
import com.lmh.minhhoang.movieapp.movieList.util.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.json.JSONObject
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener

@Composable
fun ProfileScreen(
    navController: NavHostController,
) {
    ZaloPaySDK.init(2553, Environment.SANDBOX)
    var name by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var show by remember { mutableStateOf(false) }
    var id by rememberSaveable { mutableStateOf("") }
    var power by rememberSaveable { mutableStateOf("") }

    var amount by remember { mutableStateOf("50000") }
    var token by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showToken by remember { mutableStateOf(false) }
    val createOrder = CreateOrder()
    val context = LocalContext.current
    val currentUser = Firebase.auth.currentUser
    val centerNavController = rememberNavController()
    val ref = Firebase.firestore.collection("User").document(currentUser!!.uid)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(currentUser) {
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
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
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
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                        Button(onClick = {
                            coroutineScope.launch {
                                try {
                                    val data = withContext(Dispatchers.IO) { createOrder.createOrder(amount) }
                                    val code = data.getString("return_code")
                                    val message = data.optString("message", "Unknown error")
                                    Log.d("CreateOrder", "Code: $code, Message: $message")
                                    when (code) {
                                        "1" -> {
                                            token = data.getString("zp_trans_token")
                                            showToken = true
                                        }
                                        else -> {
                                            Toast.makeText(context, "Error: $message", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    Toast.makeText(context, "Exception: ${e.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                        }) {
                            Text("Nâng cấp")
                        }

                        if (showToken) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    ZaloPaySDK.getInstance().payOrder(
                                        context as MainActivity,
                                        token,
                                        "demozpdk://app",

                                        object : PayOrderListener {
                                            override fun onPaymentSucceeded(
                                                transactionId: String,
                                                transToken: String,
                                                appTransID: String
                                            ) {
                                                isLoading = true
                                                AlertDialog.Builder(context)
                                                    .setTitle("Payment Success")
                                                    .setMessage("TransactionId: $transactionId - TransToken: $transToken")
                                                    .setPositiveButton("OK", null)
                                                    .show()
                                            }

                                            override fun onPaymentCanceled(
                                                zpTransToken: String,
                                                appTransID: String
                                            ) {
                                                AlertDialog.Builder(context)
                                                    .setTitle("User Cancel Payment")
                                                    .setMessage("zpTransToken: $zpTransToken")
                                                    .setPositiveButton("OK", null)
                                                    .show()
                                            }

                                            override fun onPaymentError(
                                                zaloPayError: ZaloPayError,
                                                zpTransToken: String,
                                                appTransID: String
                                            ) {
                                                AlertDialog.Builder(context)
                                                    .setTitle("Payment Fail")
                                                    .setMessage("ZaloPayErrorCode: ${zaloPayError.toString()} TransToken: $zpTransToken")
                                                    .setPositiveButton("OK", null)
                                                    .show()
                                            }




                                        }
                                    )
                                    val updates = hashMapOf<String, Any>(
                                        "power" to "VIP"
                                    )
                                    ref.update(updates)
                                        .addOnSuccessListener {
                                            show = false
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(
                                                context,
                                                "Lỗi $e",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black
                                ),
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(50.dp)
                            ) {
                                Text("Thanh toán")
                            }
                        }
                    }
                }
            }
            Row {
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
                        "$name",
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
                            .clickable { expanded = true },
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
                        if (power == "Normal") {
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
                            onClick = {
                                try {
                                    FirebaseAuth.getInstance().signOut()
                                    Toast.makeText(
                                        context,
                                        "Đã đăng xuất thành công",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    expanded = false
                                    navController.navigate("SignIn")
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Đăng xuất thất bại: " + e.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.e("SignOutError", "Error signing out", e)
                                }

                            }
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
                composable(Screen.SignIn.rout) {
                    SignInMainScreen(navController)
                }
            }
        }
    }
}

@Composable
fun CenterNavigationBar(centerNavController: NavHostController) {
    val item = listOf(
        BottomItem(
            title = "Lịch sử",
            icon = Icons.Rounded.History
        ),
        BottomItem(
            title = "Reel",
            icon = Icons.Rounded.VideoCall
        ),
    )
    val selected = rememberSaveable { mutableStateOf(0) }
    NavigationBar {
        Row(
            modifier = Modifier.background(Color.White)
        ) {
            item.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    selected = selected.value == index,
                    onClick = {
                        selected.value = index
                        when (selected.value) {
                            0 -> {
                                centerNavController.popBackStack()
                                centerNavController.navigate(Screen.History.rout)
                            }
                            1 -> {
                                centerNavController.popBackStack()
                                centerNavController.navigate(Screen.MyReel.rout) // Change navigation to Screen.ListReel.rout
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = bottomItem.icon,
                            contentDescription = bottomItem.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = bottomItem.title, color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }
}