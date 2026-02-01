package com.example.agendagora

import android.app.Application
import com.google.firebase.FirebaseApp

class AgendAgoraApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}