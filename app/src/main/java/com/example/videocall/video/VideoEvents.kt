package com.example.videocall.video

sealed interface VideoEvents {
     object OnEndCall : VideoEvents
     object OnJoinCall : VideoEvents
}