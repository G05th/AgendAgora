package com.example.agendagora.ui.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agendagora.ui.components.AuthHeader
import com.example.agendagora.ui.components.InputField
import com.example.agendagora.ui.components.PasswordField
import com.example.agendagora.ui.components.PrimaryButton

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    // Local form states
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(true) }

    // React to success
    LaunchedEffect(uiState.loggedIn) {
        if (uiState.loggedIn) onLoginSuccess()
    }

    Scaffold { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            AuthHeader(title = "Bem-vindo de volta", subtitle = "Agende serviços municipais rapidamente")

            Spacer(modifier = Modifier.height(20.dp))

            AnimatedVisibility(visible = uiState.error != null) {
                uiState.error?.let { err ->
                    Text(text = err, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            InputField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Down) })
            )

            PasswordField(
                value = password,
                onValueChange = { password = it },
                label = "Senha",
                showStrength = false,
                error = null
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
                Text(text = "Lembrar-me", modifier = Modifier.padding(start = 8.dp))
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = { /* Forgot password flow placeholder */ }) {
                    Text("Esqueci a senha")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                text = if (uiState.loading) "Entrando..." else "Entrar",
                onClick = {
                    focusManager.clearFocus(force = true)
                    viewModel.login(email.trim(), password)
                },
                enabled = !uiState.loading,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onNavigateToRegister,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Criar conta")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // small legal / trust text
            Text(
                text = "AgendAgora usa dados apenas para gerenciar seus agendamentos.",
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Simple snackbar-style inline feedback
        if (uiState.loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
