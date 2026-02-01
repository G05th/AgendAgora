package com.example.agendagora.data.local.dao

import androidx.room.*
import com.example.agendagora.data.local.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(user: User)

    @Query("SELECT * FROM users WHERE uid = :uid")
    suspend fun getByUid(uid: String): User?

    @Delete
    suspend fun delete(user: User)
}