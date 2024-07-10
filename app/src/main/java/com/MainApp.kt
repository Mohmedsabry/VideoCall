package com

import android.app.Application
import com.example.videocall.appModule
import io.getstream.video.android.core.StreamVideo
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.model.User
import io.getstream.video.android.model.UserType
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApp : Application() {

    private var currentName: String? = null
    var streamClient: StreamVideo? = null
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(appModule)
        }
    }

    fun initStream(user: String) {
        if (streamClient == null || user != currentName) {
            StreamVideo.removeClient()
            currentName = user
            streamClient = StreamVideoBuilder(
                this,
                "yvwhuwjtq3b2",
                user = User(
                    id = user,
                    name = user,
                    type = UserType.Guest
                )
            ).build()
        }
    }
}