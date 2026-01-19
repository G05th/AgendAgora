package com.example.agendagora.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendagora.util.Validators
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val loggedIn: Boolean = false
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        // validations upfront
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState(error = "Preencha email e senha")
            return
        }
        if (!Validators.isValidEmail(email)) {
            _uiState.value = LoginUiState(error = "Email inválido")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState(loading = true)
            delay(800) // simulated network
            // placeholder: real authentication logic or repository call
            val success = password.length >= 6
            if (success) {
                _uiState.value = LoginUiState(loggedIn = true)
            } else {
                _uiState.value = LoginUiState(error = "Credenciais inválidas")
            }
        }
    }
}
