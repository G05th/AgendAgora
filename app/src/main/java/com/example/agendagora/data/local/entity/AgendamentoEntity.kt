package com.example.agendagora.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agendamentos")
data class AgendamentoEntity(
    @PrimaryKey val id: String,
    val servicoId: String,
    val servicoTitle: String,
    val dateIso: String,
    val time: String,
    val citizenName: String,
    val status: String
)
