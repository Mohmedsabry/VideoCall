package com.example.videocall.connect

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.MainApp

class ConnectViewModel(
    private val app: Application
) : AndroidViewModel(app) {
    var state by mutableStateOf(ConnectState())
        private set

    fun onEvent(event: ConnectEvents) {
        when (event) {
            is ConnectEvents.OnTyping -> {
                state = state.copy(
                    name = event.name
                )
            }

            is ConnectEvents.OnConnectClicked -> {
                connectToStream()
            }
        }
    }

    private fun connectToStream() {
        state = state.copy(error = null)
        if (state.name.isBlank()) {
            state = state.copy(
                error = "The name can't be empty"
            )
            return
        }
        (app as MainApp).initStream(state.name)
        state = state.copy(
            isConnected = true
        )
    }
}