package com.example.agendagora.ui.screens.agendamento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendagora.data.repository.InMemoryRepository
import com.example.agendagora.domain.model.Servico
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AgendamentoUiState(
    val service: Servico? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val created: Boolean = false
)

class AgendamentoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AgendamentoUiState())
    val uiState: StateFlow<AgendamentoUiState> = _uiState.asStateFlow()

    fun loadService(serviceId: String) {
        val s = InMemoryRepository.getAllServices().firstOrNull { it.id == serviceId }
        _uiState.value = _uiState.value.copy(service = s)
    }

    fun createAgendamento(name: String, dateIso: String, time: String) {
        if (name.isBlank() || dateIso.isBlank() || time.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Preencha nome, data e hora")
            return
        }
        val servicoId = _uiState.value.service?.id ?: run {
            _uiState.value = _uiState.value.copy(error = "Serviço inválido")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            delay(700) // simula I/O
            InMemoryRepository.createAgendamento(servicoId = servicoId, dateIso = dateIso, time = time, citizenName = name)
            _uiState.value = _uiState.value.copy(isLoading = false, created = true)
        }
    }
}
