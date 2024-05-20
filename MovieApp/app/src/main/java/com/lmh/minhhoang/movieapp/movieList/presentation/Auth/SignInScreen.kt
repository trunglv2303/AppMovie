package com.lmh.minhhoang.movieapp.movieList.presentation.Auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lmh.minhhoang.movieapp.R
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignInViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = hiltViewModel()
) {

    // we can copy and paste and do changes for signup screen
    Surface(
        color = Color(0xFF253334),
        modifier = Modifier.fillMaxSize()
    ) {

        var email by rememberSaveable() {
            mutableStateOf("")
        }
        var password by rememberSaveable() {
            mutableStateOf("")
        }
        var power by rememberSaveable() {
            mutableStateOf("")
        }
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val state = viewModel.signInState.collectAsState(initial = null)
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {

                // Logo
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 54.dp, start = 16.dp)
                        .height(100.dp)
                        .align(Alignment.Start)
                        .offset(x = (-20).dp)
                )

                Text(
                    text = "Đăng nhập",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight(500),
                        color = Color.White
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )

                Text(
                    "Chào mừng",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color(0xB2FFFFFF)
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 24.dp)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(
                            text = "Vui lòng nhập Email",
                            color = Color.Black
                        )
                    },

                    leadingIcon = {

                        IconButton(onClick = {
                        }) {

                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = "E-Mail İcon"
                            )

                        }
                    },
                    keyboardOptions = KeyboardOptions(

                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),

                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text(
                            text = "Vui lòng nhập mật khẩu",
                            color = Color.Black
                        )
                    },

                    leadingIcon = {

                        IconButton(onClick = {


                        }) {

                            Icon(
                                imageVector = Icons.Filled.Password,
                                contentDescription = "E-Mail İcon"
                            )

                        }
                    },

                    keyboardOptions = KeyboardOptions(

                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),

                    )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        scope.launch {
                            if(email.isEmpty()||password.isEmpty())
                            {
                                Toast.makeText(context,"Bạn chưa nhập xong dữ liệu!",Toast.LENGTH_SHORT).show()
                            }
                            else{
                                val login = viewModel.loginUser(email, password )
                                if(login.isCompleted)
                                {
                                    navController.navigate("main")
                                }
                            }

                        }
                    },
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7C9A92)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {

                    Text(
                        text = "Đăng nhập",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight(500),
                            color = Color.White
                        )
                    )
                }


                Row(
                    modifier = Modifier.padding(top = 12.dp, bottom = 52.dp)
                ) {
                    Text(
                        "Tôi chưa có tài khoản. ",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            color = Color.White
                        )
                    )

                    Text("Đăng kí",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight(800),
                            color = Color.White
                        ),
                        modifier = Modifier.clickable {
                            navController.navigate("SignUp")
                        }
                    )
                }
                if (state.value?.isLoading == true) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
                LaunchedEffect(key1 = state.value?.isSuccess )
                {
                    scope.launch {
                        if(state.value?.isSuccess?.isNotEmpty()==true)
                        {
                            val success = state.value?.isSuccess
                            Toast.makeText(context,"${success}",Toast.LENGTH_SHORT).show()
                            navController.navigate("main")
                        }
                    }
                }
                LaunchedEffect(key1 = state.value?.isError )
                {
                    scope.launch {
                        if(state.value?.isError?.isNotEmpty()==true)
                        {
                            val error = state.value?.isError
                            Toast.makeText(context,"${error}",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }

    }
}
