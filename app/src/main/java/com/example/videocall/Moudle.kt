package com.example.videocall

import android.app.Application
import com.MainApp
import com.example.videocall.connect.ConnectViewModel
import com.example.videocall.video.VideoViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.getstream.video.android.core.StreamVideo
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

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideStreamClient(app: Application): StreamVideo {
        return (app as MainApp).streamClient!!
    }

}