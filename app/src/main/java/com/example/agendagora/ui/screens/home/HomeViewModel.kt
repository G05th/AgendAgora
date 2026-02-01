package com.example.agendagora.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendagora.domain.model.Servico
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _services = MutableStateFlow<List<Servico>>(emptyList())
    val services: StateFlow<List<Servico>> = _services.asStateFlow()

    init {
        observeServices()
    }

    private fun observeServices() {
        viewModelScope.launch {
            com.example.agendagora.AppContainer.repository.getAllServicesFlow().collect { list ->
                _services.value = list
            }
        }
    }
}
