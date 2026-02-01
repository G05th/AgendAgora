package com.example.agendagora.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendagora.AgendAgoraApplication
import com.example.agendagora.data.local.entity.Appointment
import com.example.agendagora.data.repository.AppointmentRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AppointmentViewModel : ViewModel() {

    private val repository = AppointmentRepository(AgendAgoraApplication.database.appointmentDao())
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    val appointments: Flow<List<Appointment>> = repository.getAllByUser(currentUserId)

    fun addAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.insert(appointment.copy(userId = currentUserId))
        }
    }

    fun updateAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.update(appointment)
        }
    }

    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.delete(appointment)
        }
    }
}