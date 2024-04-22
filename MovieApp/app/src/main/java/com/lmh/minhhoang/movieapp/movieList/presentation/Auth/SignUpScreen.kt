package com.lmh.minhhoang.movieapp.movieList.presentation.Auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lmh.minhhoang.movieapp.R
import com.lmh.minhhoang.movieapp.movieList.presentation.Auth.SignUpViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
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
        var enterpass by rememberSaveable() {
            mutableStateOf("")
        }
        var id by rememberSaveable() {
            mutableStateOf("")
        }
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val state = viewModel.signUpState.collectAsState(initial = null)
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
                        .padding(top = 20.dp, start = 16.dp)
                        .height(100.dp)
                        .align(Alignment.Start)
                        .offset(x = (-20).dp)
                )

                Text(
                    text = "Sign Up",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight(500),
                        color = Color.White
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = {
                        Text(
                            text = "Email",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Serif,
                                color = Color(0xFFBEC2C2)
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White
                    )

                )
                TextField(
                    value = id,
                    onValueChange = { id = it },
                    placeholder = {
                        Text(
                            text = "ID",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Serif,
                                color = Color(0xFFBEC2C2)
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White
                    )

                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    visualTransformation = PasswordVisualTransformation(),
                    placeholder = {
                        Text(
                            text = "Password",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Serif,
                                color = Color(0xFFBEC2C2)
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White
                    )

                )
                TextField(
                    value = enterpass,
                    onValueChange = { enterpass = it },
                    visualTransformation = PasswordVisualTransformation(),
                    placeholder = {
                        Text(
                            text = "Enter the password",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Serif,
                                color = Color(0xFFBEC2C2)
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White
                    )

                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if(id.isEmpty()||password.isEmpty()||enterpass.isEmpty()||email.isEmpty())
                        {
                            Toast.makeText(context,"Vui Lòng Nhập Đầy Đủ Thông Tin",Toast.LENGTH_SHORT).show()
                        }
                        else if(password != enterpass)
                        {
                            Toast.makeText(context,"Mật khẩu nhập lại không đúng",Toast.LENGTH_SHORT).show()
                        }
                        else {
                            scope.launch {
                                viewModel.registerUser(email, password, id)
                                Toast.makeText(context,"Ok",Toast.LENGTH_SHORT).show()
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
                        text = "Sign Up",
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
                        "You have an account. ",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            color = Color.White
                        )
                    )

                    Text("Sign Up",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight(800),
                            color = Color.White
                        ),
                        modifier = Modifier.clickable {
                            navController.navigate("SignIn")
                        }
                    )

                }
            }
        }
    }
}
