package com.example.agendagora

import android.app.Application

class AgendAgoraApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContainer.init(this)
    }
}
