package com.lmh.minhhoang.movieapp.movieList.presentation.Reel

import androidx.compose.runtime.Composable
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView

@Composable
@OptIn(UnstableApi::class)
fun VideoPlayerMovie(uri: Uri, isPlaying: Boolean, function: () -> Unit) {
    val context = LocalContext.current

    val exoPlayer = SimpleExoPlayer.Builder(context, DefaultRenderersFactory(context))
        .build()

    DisposableEffect(Unit) {
        val dataSourceFactory = DefaultDataSourceFactory(context)

        val mediaItem = androidx.media3.common.MediaItem.fromUri(uri)
        val mediaSourceFactory = DefaultMediaSourceFactory(dataSourceFactory)
        val mediaSource = mediaSourceFactory.createMediaSource(mediaItem)

        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()

        onDispose {
            exoPlayer.release()
        }
    }

    val isVideoPlaying by remember { mutableStateOf(isPlaying) }
    LaunchedEffect(isVideoPlaying) {
        if (isVideoPlaying) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(factory = {
        PlayerView(context).apply {
            player = exoPlayer
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        }
    })
}