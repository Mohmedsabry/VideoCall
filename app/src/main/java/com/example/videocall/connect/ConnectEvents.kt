package com.example.videocall.connect

sealed interface ConnectEvents {
    data class OnTyping(val name: String) : ConnectEvents
     object OnConnectClicked : ConnectEvents
}