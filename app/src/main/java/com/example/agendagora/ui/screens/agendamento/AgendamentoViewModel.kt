package com.example.agendagora.ui.screens.agendamento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendagora.domain.model.Servico
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
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                val s = com.example.agendagora.AppContainer.repository.getServiceById(serviceId)
                _uiState.value = _uiState.value.copy(service = s, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Erro ao carregar serviço", isLoading = false)
            }
        }
    }

    fun createAgendamento(name: String, dateIso: String, time: String) {
        val service = _uiState.value.service
        if (service == null) {
            _uiState.value = _uiState.value.copy(error = "Serviço inválido")
            return
        }
        if (name.isBlank() || dateIso.isBlank() || time.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Preencha nome, data e hora")
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                com.example.agendagora.AppContainer.repository.createAgendamento(
                    servicoId = service.id,
                    dateIso = dateIso,
                    time = time,
                    citizenName = name
                )
                _uiState.value = _uiState.value.copy(isLoading = false, created = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Não foi possível criar agendamento")
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
