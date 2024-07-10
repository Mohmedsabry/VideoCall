package com.example.videocall.connect

data class ConnectState(
    val isConnected: Boolean = false,
    val name:String = "",
    val error:String? = null
)
