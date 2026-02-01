package com.example.agendagora.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.agendagora.data.local.dao.AppointmentDao
import com.example.agendagora.data.local.dao.UserDao
import com.example.agendagora.data.local.entity.Appointment
import com.example.agendagora.data.local.entity.User

@Database(
    entities = [Appointment::class, User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appointmentDao(): AppointmentDao
    abstract fun userDao(): UserDao
}