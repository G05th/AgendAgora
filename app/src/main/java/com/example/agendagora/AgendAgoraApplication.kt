package com.example.agendagora

import android.app.Application
import androidx.room.Room
import com.example.agendagora.data.local.AppDatabase
import com.google.firebase.FirebaseApp

class AgendAgoraApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "agendagora_db"
        ).build()
    }
}