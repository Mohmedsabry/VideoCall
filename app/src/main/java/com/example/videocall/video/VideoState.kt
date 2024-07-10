package com.example.videocall.video

import io.getstream.video.android.core.Call

data class VideoState(
    val call: Call,
    val connectionState: ConnectionState? = null,
    val error: String? = null
)

enum class ConnectionState {
    Connecting,
    Joining,
    Ended
}
