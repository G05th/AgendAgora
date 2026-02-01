package com.example.agendagora.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,  // UID do Firebase
    val type: String,
    val date: String,    // "dd/MM/yyyy"
    val time: String,    // "HH:mm"
    val location: String = "",
    val description: String = "",
    val status: String = "Pendente"  // Pendente, Confirmado, Cancelado
)