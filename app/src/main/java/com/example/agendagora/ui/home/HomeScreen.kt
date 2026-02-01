package com.example.agendagora.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agendagora.navigation.Screen
import com.example.agendagora.viewmodel.AuthViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    val authState = viewModel.authState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bem-vindo ao AgendApp Angola",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Exibe o email do usuário logado
        authState.user?.email?.let { email ->
            Text(
                text = "Logado como: $email",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        Button(
            onClick = { /* Futura tela: Agendar Novo Serviço */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agendar Novo Serviço")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Futura tela: Meus Agendamentos */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Meus Agendamentos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Futura tela: Serviços Disponíveis */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Serviços Públicos Disponíveis")
        }

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedButton(
            onClick = {
                viewModel.logout()
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sair (Logout)")
        }
    }
}