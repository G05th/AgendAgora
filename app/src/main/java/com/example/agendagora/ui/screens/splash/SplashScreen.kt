package com.example.agendagora.ui.screens.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onFinish: (isLoggedIn: Boolean) -> Unit
) {
    val ui by viewModel.uiState.collectAsState()

    LaunchedEffect(ui.checked) {
        ui.checked?.let { onFinish(it) }
    }

    // Centered brand area with short trust message
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO: substitute for real logo composable or Image if available
        Text(text = "AgendAgora", style = androidx.compose.material3.MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Serviços públicos sem filas", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(24.dp))
        CircularProgressIndicator()
    }
}
