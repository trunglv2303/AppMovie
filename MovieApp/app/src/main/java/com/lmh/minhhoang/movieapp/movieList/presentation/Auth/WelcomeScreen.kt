package com.lmh.minhhoang.movieapp.movieList.presentation.Auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.lmh.minhhoang.movieapp.R

@Composable
fun WelcomeSceen(navController: NavController) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFF58529), Color(0xFFDD2A7B), Color(0xFF8134AF), Color(0xFF515BD4))
    )
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(40.dp))
                .height(378.dp)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Chào mừng bạn",
            fontSize = 26.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Vui lòng đăng nhập để trãi nghiệm dịch vụ",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(64.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navController.navigate("SignUp") },
                modifier = Modifier.padding(end = 10.dp).background(gradient, shape = MaterialTheme.shapes.medium),
                contentPadding = PaddingValues(start = 40.dp, top = 12.dp, end = 40.dp, bottom = 12.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
            ) {
                Text(
                    "Đăng kí",
                    fontSize = 20.sp
                )
            }

            Button(
                onClick = { navController.navigate("SignIn") },
                modifier = Modifier.padding(start = 10.dp).background(gradient, shape = MaterialTheme.shapes.medium),
                contentPadding = PaddingValues(
                    start = 40.dp,
                    top = 12.dp,
                    end = 40.dp,
                    bottom = 12.dp
                ),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White // Optional: Set content color (text)
                ),
            ) {
                Text(
                    "Đăng nhập",
                    fontSize = 20.sp
                )
            }
        }
    }
}
@Composable
fun WelcomeMainScreen(navCtrl: NavHostController) {
    MaterialTheme {
        val image = painterResource(R.drawable.backgroud)

        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.size(577.dp)
        )
        WelcomeSceen(navController = navCtrl)
    }
}