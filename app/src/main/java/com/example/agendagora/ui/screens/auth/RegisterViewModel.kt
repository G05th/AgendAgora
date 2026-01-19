package com.example.agendagora.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendagora.util.Validators
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(val loading: Boolean = false, val error: String? = null, val registered: Boolean = false)

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun register(name: String, email: String, password: String, confirm: String, acceptedTerms: Boolean) {
        if (!acceptedTerms) {
            _uiState.value = RegisterUiState(error = "Aceite os termos para continuar")
            return
        }
        if (!Validators.isValidName(name)) {
            _uiState.value = RegisterUiState(error = "Informe um nome válido")
            return
        }
        if (!Validators.isValidEmail(email)) {
            _uiState.value = RegisterUiState(error = "Email inválido")
            return
        }
        if (!Validators.isValidPassword(password)) {
            _uiState.value = RegisterUiState(error = "Senha precisa ter ao menos 6 caracteres")
            return
        }
        if (password != confirm) {
            _uiState.value = RegisterUiState(error = "As senhas não coincidem")
            return
        }

        viewModelScope.launch {
            _uiState.value = RegisterUiState(loading = true)
            delay(900)
            // placeholder: call to repository to create user
            _uiState.value = RegisterUiState(registered = true)
        }
    }
}
