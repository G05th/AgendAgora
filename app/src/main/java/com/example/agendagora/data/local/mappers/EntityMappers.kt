package com.example.agendagora.data.local.mappers

import com.example.agendagora.data.local.entity.AgendamentoEntity
import com.example.agendagora.data.local.entity.ServicoEntity
import com.example.agendagora.domain.model.Agendamento
import com.example.agendagora.domain.model.Servico

fun ServicoEntity.toDomain(): Servico = Servico(id = id, title = title, description = description)
fun Servico.toEntity(): ServicoEntity = ServicoEntity(id = id, title = title, description = description)

fun AgendamentoEntity.toDomain(): Agendamento = Agendamento(
    id = id,
    servicoId = servicoId,
    servicoTitle = servicoTitle,
    dateIso = dateIso,
    time = time,
    citizenName = citizenName,
    status = status
)

fun Agendamento.toEntity(): AgendamentoEntity = AgendamentoEntity(
    id = id,
    servicoId = servicoId,
    servicoTitle = servicoTitle,
    dateIso = dateIso,
    time = time,
    citizenName = citizenName,
    status = status
)
