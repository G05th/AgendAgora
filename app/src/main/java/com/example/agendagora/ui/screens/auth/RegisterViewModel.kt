package com.example.agendagora.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val registered: Boolean = false
)

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun register(name: String, email: String, password: String, confirm: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Preencha todos os campos")
            return
        }
        if (!email.contains("@")) {
            _uiState.value = _uiState.value.copy(error = "Email inválido")
            return
        }
        if (password.length < 6) {
            _uiState.value = _uiState.value.copy(error = "Senha deve ter pelo menos 6 caracteres")
            return
        }
        if (password != confirm) {
            _uiState.value = _uiState.value.copy(error = "As senhas não coincidem")
            return
        }

        viewModelScope.launch {
            _uiState.value = RegisterUiState(isLoading = true)
            delay(700)
            // placeholder: gravar em repo/remote -> aqui consideramos sucesso
            _uiState.value = RegisterUiState(registered = true)
        }
    }
}
