package com.example.agendagora.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Appointment(
    val type: String,
    val date: String,
    val time: String,
    val location: String = "",
    val description: String = ""
)

class AppointmentViewModel : ViewModel() {
    private val _appointments = mutableStateListOf<Appointment>()
    val appointments: List<Appointment> = _appointments

    fun addAppointment(appointment: Appointment) {
        _appointments.add(appointment)
    }
}