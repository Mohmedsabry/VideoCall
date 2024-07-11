package com.example.videocall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.videocall.connect.ConnectScreen
import com.example.videocall.connect.ConnectViewModel
import com.example.videocall.ui.theme.VideoCallTheme
import com.example.videocall.video.ConnectionState
import com.example.videocall.video.VideoScreen
import com.example.videocall.video.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.video.android.compose.theme.VideoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideoCallTheme {
                val navController = rememberNavController()
                Scaffold(Modifier.fillMaxSize()) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "connect",
                        modifier = Modifier.padding(paddingValues)
                    ) {

                        composable("connect") {
                            val connectViewModel = hiltViewModel<ConnectViewModel>()
                            LaunchedEffect(key1 = connectViewModel.state.isConnected) {
                                if (connectViewModel.state.isConnected) {
                                    navController.navigate("video") {
                                        popUpTo("connect") {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                            ConnectScreen(connectState = connectViewModel.state) {
                                connectViewModel.onEvent(it)
                            }
                        }
                        composable("video") {
                            val videoViewModel = hiltViewModel<VideoViewModel>()
                            LaunchedEffect(key1 = videoViewModel.state.connectionState) {
                                if (videoViewModel.state.connectionState == ConnectionState.Ended) {
                                    navController.navigate("connect") {
                                        println("h")
                                        popUpTo("video") {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                            VideoTheme {
                                VideoScreen(videoState = videoViewModel.state) {
                                    videoViewModel.onEvent(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
