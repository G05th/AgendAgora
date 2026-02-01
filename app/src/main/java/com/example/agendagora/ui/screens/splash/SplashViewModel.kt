package com.example.agendagora.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SplashUiState(val checked: Boolean? = null, val loading: Boolean = true)

class SplashViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            // placeholder: small wait to simulate work (token check, migration, etc.)
            _uiState.value = _uiState.value.copy(loading = true)
            delay(600)
            // TODO: substituir por verificação real (SharedPreferences / EncryptedSharedPreferences / DataStore)
            val isLoggedIn = false
            _uiState.value = SplashUiState(checked = isLoggedIn, loading = false)
        }
    }
}
