package com.example.agendagora.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class AuthState(
    val isLoading: Boolean = false,
    val user: FirebaseUser? = null,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    init {
        // Verifica se já está logado
        auth.currentUser?.let {
            _authState.value = AuthState(user = it, isSuccess = true)
        }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = _authState.value.copy(error = "Preencha email e senha")
            return
        }

        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                _authState.value = AuthState(
                    user = result.user,
                    isSuccess = true
                )
            } catch (e: Exception) {
                _authState.value = AuthState(
                    error = e.message ?: "Erro ao fazer login"
                )
            }
        }
    }

    fun register(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = _authState.value.copy(error = "Preencha email e senha")
            return
        }

        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                _authState.value = AuthState(
                    user = result.user,
                    isSuccess = true
                )
            } catch (e: Exception) {
                _authState.value = AuthState(
                    error = e.message ?: "Erro ao criar conta"
                )
            }
        }
    }

    fun logout() {
        auth.signOut()
        _authState.value = AuthState()
    }
}