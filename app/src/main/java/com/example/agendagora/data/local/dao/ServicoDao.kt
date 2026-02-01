package com.example.agendagora.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.agendagora.data.local.entity.ServicoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicoDao {
    @Query("SELECT * FROM servicos ORDER BY title")
    fun getAllServices(): Flow<List<ServicoEntity>>

    @Query("SELECT * FROM servicos WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): ServicoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(services: List<ServicoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(service: ServicoEntity)
}
