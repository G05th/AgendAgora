package com.example.agendagora.ui.screens.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
    val uiState by viewModel.uiState.collectAsState()

    // When the check completes, inform the NavGraph
    LaunchedEffect(uiState.checked) {
        uiState.checked?.let { onFinish(it) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "AgendAgora",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Organize o seu atendimento público",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
            CircularProgressIndicator()
        }
    }
}
