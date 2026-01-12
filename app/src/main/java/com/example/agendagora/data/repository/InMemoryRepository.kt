package com.example.agendagora.data.repository

import com.example.agendagora.domain.model.Agendamento
import com.example.agendagora.domain.model.Servico
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

object InMemoryRepository {

    // Dados iniciais
    private val services = listOf(
        Servico(id = "1", title = "Emissão de Bilhete de Identidade", description = "Serviço para emissão de documento de identificação."),
        Servico(id = "2", title = "Pedido de Certidão", description = "Solicitação de certidões civis."),
        Servico(id = "3", title = "Agendamento de Consulta Social", description = "Atendimento em serviços sociais municipais.")
    )

    fun getAllServices(): List<Servico> = services

    // Agendamentos armazenados localmente
    private val _agendamentos = MutableStateFlow<List<Agendamento>>(emptyList())
    val agendamentos: StateFlow<List<Agendamento>> = _agendamentos.asStateFlow()

    fun createAgendamento(servicoId: String, dateIso: String, time: String, citizenName: String): Agendamento {
        val new = Agendamento(
            id = UUID.randomUUID().toString(),
            servicoId = servicoId,
            servicoTitle = services.firstOrNull { it.id == servicoId }?.title ?: "Serviço",
            dateIso = dateIso,
            time = time,
            citizenName = citizenName,
            status = "Confirmado"
        )
        _agendamentos.value = _agendamentos.value + new
        return new
    }

    fun getAgendamentos(): List<Agendamento> = _agendamentos.value

    fun cancelAgendamento(agendamentoId: String) {
        _agendamentos.value = _agendamentos.value.filterNot { it.id == agendamentoId }
    }

    fun clearAll() {
        _agendamentos.value = emptyList()
    }
}