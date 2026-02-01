package com.example.agendagora.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.agendagora.data.local.entity.AgendamentoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendamentoDao {
    @Query("SELECT * FROM agendamentos ORDER BY dateIso, time")
    fun getAllAgendamentos(): Flow<List<AgendamentoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(agendamento: AgendamentoEntity)

    @Query("DELETE FROM agendamentos WHERE id = :id")
    suspend fun deleteById(id: String)
}
