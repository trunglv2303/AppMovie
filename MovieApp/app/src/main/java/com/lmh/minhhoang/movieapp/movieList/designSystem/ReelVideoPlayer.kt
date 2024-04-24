package com.lmh.minhhoang.movieapp.movieList.designSystem

import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi

@UnstableApi
@Composable
fun ReelVideoPlayer(
    video: String?
) {
    AndroidView(factory = {
                          context ->
                          VideoView(context).apply {
                              setVideoPath(video)
                              val myController = MediaController(context)
                              myController.setAnchorView(this)
                              setMediaController(myController)

                              setOnPreparedListener{
                                  start()
                              }
                          }
    }, modifier = Modifier)
}