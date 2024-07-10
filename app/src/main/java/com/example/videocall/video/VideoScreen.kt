package com.example.videocall.video

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.getstream.video.android.compose.permission.rememberCallPermissionsState
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent
import io.getstream.video.android.compose.ui.components.call.controls.actions.DefaultOnCallActionHandler
import io.getstream.video.android.core.call.state.LeaveCall

@Composable
fun VideoScreen(
    modifier: Modifier = Modifier,
    videoState: VideoState,
    onEvent: (VideoEvents) -> Unit
) {
    when {
        videoState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = videoState.error,
                    color = androidx.compose.ui.graphics.Color.Red
                )
            }
        }

        videoState.connectionState == ConnectionState.Joining -> {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Joining...")
            }
        }

        else -> {
            val basePermission = listOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            )
            val bluetoothPermission = if (android.os.Build.VERSION.SDK_INT >= 31) {
                listOf(Manifest.permission.BLUETOOTH_CONNECT)
            } else {
                emptyList()
            }
            val notificationPermission = if (android.os.Build.VERSION.SDK_INT >= 33) {
                listOf(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                emptyList()
            }
            val context = LocalContext.current
            CallContent(
                call = videoState.call,
                modifier = Modifier.fillMaxSize(),
                permissions = rememberCallPermissionsState(
                    videoState.call,
                    permissions = basePermission + bluetoothPermission + notificationPermission,
                    onPermissionsResult = {
                        if (it.values.contains(false)) {
                            Toast.makeText(
                                context,
                                "please accept all permissions",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            onEvent(VideoEvents.OnJoinCall)
                        }
                    },
                ),
                onCallAction = { callAction ->
                    if (callAction == LeaveCall)
                        onEvent(VideoEvents.OnEndCall)
                    DefaultOnCallActionHandler.onCallAction(videoState.call,callAction)
                }, onBackPressed = {
                    onEvent(VideoEvents.OnEndCall)
                }
            )
        }
    }
}