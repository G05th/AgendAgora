package com.example.agendagora.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.agendagora.data.repository.InMemoryRepository
import com.example.agendagora.domain.model.Servico
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _services = MutableStateFlow<List<Servico>>(emptyList())
    val services: StateFlow<List<Servico>> = _services.asStateFlow()

    init {
        loadServices()
    }

    private fun loadServices() {
        _services.value = InMemoryRepository.getAllServices()
    }
}
