package com.example.agendagora.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val uid: String,  // UID do Firebase
    val nome: String,
    val email: String,
    val telefone: String
)