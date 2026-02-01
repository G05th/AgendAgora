package com.example.agendagora.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicos")
data class ServicoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String
)
