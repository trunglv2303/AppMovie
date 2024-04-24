package com.lmh.minhhoang.movieapp.movieList.presentation.Reel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.google.firebase.firestore.DocumentSnapshot
import com.google.rpc.context.AttributeContext.Resource
import com.lmh.minhhoang.movieapp.movieList.domain.reponsitory.ReelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@UnstableApi
@HiltViewModel
class ReelViewModel @Inject constructor(
    val videoPlayer:ExoPlayer,
    val videoRepository: ReelRepository
) :ViewModel() {

    private var _uiState = MutableStateFlow<VideoDetailUiState>(VideoDetailUiState.Default)
    val uiState : StateFlow<VideoDetailUiState>
        get() = _uiState

    init{
        videoPlayer.repeatMode = Player.REPEAT_MODE_ALL
        videoPlayer.playWhenReady = true
        videoPlayer.prepare()
    }
    fun handleAction(action : VideoDetailAction)
    {
        when(action)
        {
            is VideoDetailAction.LoadData ->{
                val videoID= action.id
                loadVideo(videoId = videoID)
            }
            is VideoDetailAction.ToggleVideo ->{
                toggleVideo()
            }
        }
    }
    private fun loadVideo(videoId:Int)
    {
        _uiState.value= VideoDetailUiState.Loading
        viewModelScope.launch {
            delay(100L)
            playVideo(videoResourceId = videoId)
            val video = videoRepository.getVideo()
            _uiState.value= VideoDetailUiState.Success
        }
    }
    private fun playVideo(videoResourceId: Int )
    {
        val uri = RawResourceDataSource.buildRawResourceUri(videoResourceId)
        val media = MediaItem.fromUri(uri)
        videoPlayer.setMediaItem(media)
        videoPlayer.play()

    }
    private fun toggleVideo()
    {
        if(videoPlayer.isLoading)
        {

        }
        else{
            if(videoPlayer.isPlaying)
            {
                videoPlayer.pause()
            }
            else{
                videoPlayer.play()
            }
        }
    }


}
sealed interface VideoDetailUiState
{
    object Default:VideoDetailUiState
    object Loading:VideoDetailUiState
    object Success:VideoDetailUiState
    data class Error(val msg : String):VideoDetailUiState

}
sealed class VideoDetailAction{
    data class LoadData(val id:Int):VideoDetailAction()
    object ToggleVideo : VideoDetailAction()
}