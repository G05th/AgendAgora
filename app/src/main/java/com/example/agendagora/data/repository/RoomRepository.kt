package com.example.agendagora.data.repository

import com.example.agendagora.data.local.dao.AgendamentoDao
import com.example.agendagora.data.local.dao.ServicoDao
import com.example.agendagora.data.local.mappers.toDomain
import com.example.agendagora.data.local.mappers.toEntity
import com.example.agendagora.domain.model.Agendamento
import com.example.agendagora.domain.model.Servico
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class RoomRepository(
    private val servicoDao: ServicoDao,
    private val agendamentoDao: AgendamentoDao
) {

    fun getAllServicesFlow(): Flow<List<Servico>> =
        servicoDao.getAllServices().map { list -> list.map { it.toDomain() } }

    suspend fun getServiceById(id: String): Servico? {
        val entity = servicoDao.getById(id)
        return entity?.toDomain()
    }

    fun getAgendamentosFlow(): Flow<List<Agendamento>> =
        agendamentoDao.getAllAgendamentos().map { list -> list.map { it.toDomain() } }

    suspend fun createAgendamento(servicoId: String, dateIso: String, time: String, citizenName: String): Agendamento {
        val servicoTitle = servicoDao.getById(servicoId)?.title ?: "Serviço"
        val new = Agendamento(
            id = UUID.randomUUID().toString(),
            servicoId = servicoId,
            servicoTitle = servicoTitle,
            dateIso = dateIso,
            time = time,
            citizenName = citizenName,
            status = "Confirmado"
        )
        agendamentoDao.insert(new.toEntity())
        return new
    }

    suspend fun cancelAgendamento(agendamentoId: String) {
        agendamentoDao.deleteById(agendamentoId)
    }
}
