package com.example.agendagora.ui.screens.meusagendamentos

import androidx.lifecycle.ViewModel
import com.example.agendagora.data.repository.InMemoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MeusAgendamentosViewModel : ViewModel() {
    private val _agendamentos = MutableStateFlow(InMemoryRepository.getAgendamentos())
    val agendamentos: StateFlow<List<com.example.agendagora.domain.model.Agendamento>> = _agendamentos.asStateFlow()

    init {
        // observe repository state (simple polling replacement)
        // for the prototype, we update on actions only
    }

    fun cancelAgendamento(id: String) {
        InMemoryRepository.cancelAgendamento(id)
        _agendamentos.value = InMemoryRepository.getAgendamentos()
    }
}
