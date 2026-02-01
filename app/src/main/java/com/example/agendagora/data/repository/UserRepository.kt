package com.example.agendagora.data.repository

import com.example.agendagora.data.local.dao.UserDao
import com.example.agendagora.data.local.entity.User

class UserRepository(private val dao: UserDao) {
    suspend fun insertOrUpdate(user: User) = dao.insertOrUpdate(user)

    suspend fun getByUid(uid: String) = dao.getByUid(uid)
}