package com.example.agendagora

import android.content.Context
import com.example.agendagora.data.local.AppDatabase
import com.example.agendagora.data.repository.RoomRepository

object AppContainer {
    lateinit var repository: RoomRepository
        private set

    fun init(context: Context) {
        val db = AppDatabase.getInstance(context)
        repository = RoomRepository(db.servicoDao(), db.agendamentoDao())
    }
}
