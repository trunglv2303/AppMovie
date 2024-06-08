package com.lmh.minhhoang.movieapp.movieList.presentation.Reel

import android.content.Intent
import android.media.Image
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bumptech.glide.gifdecoder.GifDecoder
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.lmh.minhhoang.movieapp.R
import com.lmh.minhhoang.movieapp.di.AuthManager
import com.lmh.minhhoang.movieapp.movieList.domain.model.Reel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

@Composable
fun ReelScreen(
    navController: NavController,
    storageReference : StorageReference,

) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFF58529), Color(0xFFDD2A7B), Color(0xFF8134AF), Color(0xFF515BD4))
    )
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val selectVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            videoUri = it
        }
    }
    val context = LocalContext.current
    var storageReference = FirebaseStorage.getInstance().getReference()
    var caption by rememberSaveable() {
        mutableStateOf("")
    }
    val currentDateTime = Date()
    val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
    val id = dateFormat.format(currentDateTime)
    val username = AuthManager.getCurrentUserEmail()
    Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(300.dp)
                    .padding(16.dp)
                    .border(2.dp, Color.Red, RoundedCornerShape(8.dp))
                    .padding(16.dp)
                    .clickable { selectVideoLauncher.launch("video/*") },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = "https://www.pngplay.com/wp-content/uploads/8/Upload-Icon-Logo-Transparent-File.png",
                        contentDescription = "Your GIF Description",
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Nhấp vào để upload video",
                        fontSize = 20.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            TextField(
                value = caption,
                onValueChange = { caption = it },
                placeholder = {
                    Text(
                        text = "Nội dung",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            color = Color(0xFFBEC2C2)
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        Toast.makeText(context,"Vui lòng đợi trong giây lát",Toast.LENGTH_SHORT).show()
                        isLoading = true
                        videoUri?.let { uri ->
                            val filename = UUID.randomUUID().toString()
                            val reference = storageReference.child("videos/$filename")
                            reference.putFile(uri)
                                .addOnSuccessListener { uploadTask ->
                                    Toast.makeText(context, "Video Upload Thành Công", Toast.LENGTH_SHORT).show()
                                    uploadTask.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                                        val reel = Reel(url = downloadUri.toString(), userName = username?:"", caption =caption,id=id)
                                        Firebase.firestore.collection("Reel").document().set(reel)
                                            .addOnSuccessListener {
                                            }
                                            .addOnFailureListener { exception ->
                                                Toast.makeText(context, "Thêm dữ liệu vào Firestore không thành công: ${exception.message}", Toast.LENGTH_SHORT).show()
                                            }
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(context, "Video Upload Không Thành Công: ${exception.message}", Toast.LENGTH_SHORT).show()
                                }
                                .addOnCompleteListener {
                                    isLoading = false
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
                )
                {
                    Text("Đăng")
                }

            }
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}