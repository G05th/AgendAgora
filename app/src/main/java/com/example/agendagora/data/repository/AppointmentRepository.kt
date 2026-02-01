package com.example.agendagora.data.repository

import com.example.agendagora.data.local.dao.AppointmentDao
import com.example.agendagora.data.local.entity.Appointment
import kotlinx.coroutines.flow.Flow

class AppointmentRepository(private val dao: AppointmentDao) {
    fun getAllByUser(userId: String): Flow<List<Appointment>> = dao.getAllByUser(userId)

    suspend fun insert(appointment: Appointment) = dao.insert(appointment)

    suspend fun update(appointment: Appointment) = dao.update(appointment)

    suspend fun delete(appointment: Appointment) = dao.delete(appointment)

    suspend fun deleteById(id: Long) = dao.deleteById(id)
}