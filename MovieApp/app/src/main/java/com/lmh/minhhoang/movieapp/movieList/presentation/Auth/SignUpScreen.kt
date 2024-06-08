package com.lmh.minhhoang.movieapp.movieList.presentation.Auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Brush
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
        var power by rememberSaveable() {
            mutableStateOf("Normal")
        }
    val state = viewModel.signUpState.collectAsState(initial = null)
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFF58529), Color(0xFFDD2A7B), Color(0xFF8134AF), Color(0xFF515BD4))
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ){

            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(75.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Đăng Kí",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight(500),
                    color = Color.White
                ),
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
                            text = "Mật khẩu",
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
                            text = "Nhập lại mật khẩu",
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
                                viewModel.registerUser(email, password, id, power)
                                navController.navigate("SignUp")
                                Toast.makeText(context,"Đã tạo thành công",Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .background(gradient, shape = MaterialTheme.shapes.medium)
                ) {

                    Text(
                        text = "Đăng kí",
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
                        "Tôi đã có tài khoản.",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            color = Color.White
                        )
                    )

                    Text("Đăng nhập",
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
                    LaunchedEffect(key1 = state.value?.isSuccess )
                    {
                        scope.launch {
                            if(state.value?.isSuccess?.isNotEmpty()==true)
                            {
                                val success = state.value?.isSuccess
                                Toast.makeText(context,"${success}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    LaunchedEffect(key1 = state.value?.isError )
                    {
                        scope.launch {
                            if(state.value?.isError?.isNotEmpty()==true)
                            {
                                val error = state.value?.isError
                                Toast.makeText(context,"${error}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
@Composable
fun SignUpMainScreen(navCtrl: NavHostController) {
    MaterialTheme {
        val image = painterResource(R.drawable.backgroud)

        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.size(577.dp)
        )
        SignUpScreen(navController = navCtrl)
    }
}
