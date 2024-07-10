package com.example.videocall.connect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.videocall.ui.theme.VideoCallTheme
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun ConnectScreen(
    modifier: Modifier = Modifier,
    connectState: ConnectState,
    onEvent: (ConnectEvents) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.sdp),
        verticalArrangement = Arrangement.spacedBy(16.sdp),
    ) {
        Text(
            text = "choose a Name",
            fontSize = 16.ssp,
        )
        TextField(
            value = connectState.name,
            onValueChange = {
                onEvent(ConnectEvents.OnTyping(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Enter your name")
            }
        )
        Button(
            onClick = {
                onEvent(ConnectEvents.OnConnectClicked)
            }, content = {
                Text(text = "Connect", fontSize = 14.ssp)
            },
            modifier = Modifier.align(Alignment.End)
        )
        androidx.compose.animation.AnimatedVisibility(visible = connectState.error != null) {
            Text(
                text = connectState.error.toString(),
                color = androidx.compose.ui.graphics.Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Prev() {
    VideoCallTheme {
        ConnectScreen(
            connectState = ConnectState(),
            onEvent = {}
        )
    }
}