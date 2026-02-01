package com.example.agendagora.ui.screens.meusagendamentos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendagora.domain.model.Agendamento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MeusAgendamentosViewModel : ViewModel() {
    private val _agendamentos = MutableStateFlow<List<Agendamento>>(emptyList())
    val agendamentos: StateFlow<List<Agendamento>> = _agendamentos.asStateFlow()

    init {
        observeAgendamentos()
    }

    private fun observeAgendamentos() {
        viewModelScope.launch {
            com.example.agendagora.AppContainer.repository.getAgendamentosFlow().collect { list ->
                _agendamentos.value = list
            }
        }
    }

    fun cancelAgendamento(id: String) {
        viewModelScope.launch {
            try {
                com.example.agendagora.AppContainer.repository.cancelAgendamento(id)
            } catch (e: Exception) {

            }
        }
    }
}
