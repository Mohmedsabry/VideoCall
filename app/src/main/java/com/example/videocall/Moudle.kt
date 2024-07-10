package com.example.videocall

import com.MainApp
import com.example.videocall.connect.ConnectViewModel
import com.example.videocall.video.VideoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    factory {
        val app = androidContext().applicationContext as MainApp
        app.streamClient
    }

    viewModelOf(::ConnectViewModel)
    viewModelOf(::VideoViewModel)

}