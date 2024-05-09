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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.lmh.minhhoang.movieapp.R
import com.lmh.minhhoang.movieapp.movieList.domain.model.Reel
import java.util.UUID

@Composable
fun ReelScreen(
    navController: NavController,
    storageReference : StorageReference,

) {
    var videoUri by remember { mutableStateOf<Uri?>(null) }

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
    val Image by remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clickable { selectVideoLauncher.launch("video/*") }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.upload),
                    contentDescription = null,
                )
            }

            TextField(
                value = caption,
                onValueChange = { caption = it },
                placeholder = {
                    Text(
                        text = "Caption reel",
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
                        navController.navigate("main")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        videoUri?.let { uri ->
                            val filename = UUID.randomUUID().toString()
                            val reference = storageReference.child("videos/$filename")

                            reference.putFile(uri)
                                .addOnSuccessListener { uploadTask ->
                                    Toast.makeText(context, "Video Upload Thành Công", Toast.LENGTH_SHORT).show()
                                    uploadTask.storage.downloadUrl.addOnSuccessListener { downloadUri ->
//                                        val reel: Reel = Reel(url = downloadUri.toString(), caption = caption)
//                                        Firebase.firestore.collection("Reel").document().set(reel)
//                                            .addOnSuccessListener {
//                                                navController.navigate("profile")
//                                            }
//                                            .addOnFailureListener { exception ->
//                                                Toast.makeText(context, "Thêm dữ liệu vào Firestore không thành công: ${exception.message}", Toast.LENGTH_SHORT).show()
//                                            }
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    // Upload failed
                                    Toast.makeText(context, "Video Upload Không Thành Công: ${exception.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                ) {
                    Text("Upload")
                }
            }
        }
    }
}