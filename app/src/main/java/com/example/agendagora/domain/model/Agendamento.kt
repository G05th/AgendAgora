package com.example.agendagora.domain.model

data class Agendamento(
    val id: String,
    val servicoId: String,
    val servicoTitle: String,
    val dateIso: String, // YYYY-MM-DD
    val time: String, // HH:mm
    val citizenName: String,
    val status: String
)
