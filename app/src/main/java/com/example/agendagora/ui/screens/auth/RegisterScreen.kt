package com.example.agendagora.ui.screens.auth

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
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var acceptTerms by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.registered) {
        if (uiState.registered) onRegisterSuccess()
    }

    Scaffold { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            AuthHeader(title = "Criar conta", subtitle = "Tenha acesso fácil aos serviços municipais")
            Spacer(modifier = Modifier.height(20.dp))

            InputField(
                value = name,
                onValueChange = { name = it },
                label = "Nome completo",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Down) })
            )

            InputField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                )
            )

            PasswordField(
                value = password,
                onValueChange = { password = it },
                label = "Senha",
                showStrength = true
            )

            PasswordField(
                value = confirm,
                onValueChange = { confirm = it },
                label = "Confirmar senha",
                showStrength = false
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = acceptTerms, onCheckedChange = { acceptTerms = it })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Li e aceito os termos de uso", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                text = if (uiState.loading) "Criando conta..." else "Criar conta",
                onClick = {
                    focusManager.clearFocus(true)
                    viewModel.register(name.trim(), email.trim(), password, confirm, acceptTerms)
                },
                enabled = !uiState.loading && acceptTerms,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("Voltar")
            }

            Spacer(modifier = Modifier.height(12.dp))
            uiState.error?.let { err ->
                Text(text = err, color = MaterialTheme.colorScheme.error)
            }
        }

        if (uiState.loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}
