package com

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.getstream.video.android.core.StreamVideo
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.model.User
import io.getstream.video.android.model.UserType

@HiltAndroidApp
class MainApp : Application() {

    private var currentName: String? = null
    var streamClient: StreamVideo? = null

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