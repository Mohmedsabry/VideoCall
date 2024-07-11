package com.example.videocall.video

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.video.android.core.StreamVideo
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val videoClient: StreamVideo
) : ViewModel() {
    var state by mutableStateOf(VideoState(videoClient.call("default", "room")))
        private set


    fun onEvent(event: VideoEvents) {
        when (event) {
            VideoEvents.OnEndCall -> {
                state.call.leave()
                videoClient.logOut()
                state = state.copy(
                    connectionState = ConnectionState.Ended,
                    error = null
                )
            }

            VideoEvents.OnJoinCall -> {
                joinCall()
            }
        }
    }

    private fun joinCall() {
        if (state.connectionState == ConnectionState.Connecting) return
        viewModelScope.launch {
            state = state.copy(
                connectionState = ConnectionState.Joining
            )
            val shouldCreateCall = videoClient.queryCalls(
                filters = emptyMap()
            ).getOrNull()
                ?.calls?.isEmpty() == true
            state.call.join(shouldCreateCall).onSuccess {
                state = state.copy(
                    connectionState = ConnectionState.Connecting,
                    error = null
                )
            }.onError {
                state = state.copy(
                    connectionState = null,
                    error = it.message
                )
            }
        }
    }
}