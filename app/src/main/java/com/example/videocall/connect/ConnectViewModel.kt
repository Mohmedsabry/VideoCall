package com.example.videocall.connect

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.MainApp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
@HiltViewModel
class ConnectViewModel @Inject constructor(
    private val app: Application
) : ViewModel() {
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
            isConnected = true,
        )
    }
}