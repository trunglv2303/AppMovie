package com.lmh.minhhoang.movieapp.movieList.presentation.Reel

import android.net.Uri
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class) @Composable
fun VideoPlayer(uri: Uri, isPlaying: Boolean, function: () -> Unit) {
    val context = LocalContext.current

    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context, DefaultRenderersFactory(context))
            .build()
            .apply {
                val dataSourceFactory = DefaultDataSourceFactory(context)

                val mediaItem = MediaItem.fromUri(uri)
                val mediaSourceFactory = DefaultMediaSourceFactory(dataSourceFactory)
                val mediaSource = mediaSourceFactory.createMediaSource(mediaItem)

                this.setMediaSource(mediaSource)
                this.prepare()
            }
    }

    exoPlayer.playWhenReady = true
    exoPlayer.repeatMode = SimpleExoPlayer.REPEAT_MODE_ONE

    DisposableEffect(AndroidView(factory = {
        PlayerView(context).apply {
            player = exoPlayer
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        }
    })) {
        onDispose {
            exoPlayer.release()
        }
    }
}
