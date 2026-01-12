package com.example.agendagora.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false
)

class AuthViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Preencha email e senha")
            return
        }

        if (!email.contains("@")) {
            _uiState.value = _uiState.value.copy(error = "Email inválido")
            return
        }

        // Simulação de chamada de login (substituir por repositório real)
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            delay(700)
            val success = password.length >= 6 // regra simples de placeholder
            if (success) {
                _uiState.value = AuthUiState(isLoggedIn = true)
            } else {
                _uiState.value = AuthUiState(error = "Credenciais inválidas")
            }
        }
    }
}
