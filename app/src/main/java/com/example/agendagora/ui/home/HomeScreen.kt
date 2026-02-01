package com.example.agendagora.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agendagora.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bem-vindo ao AgendApp Angola", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { /* Futura tela: Agendar Serviço */ },
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

        TextButton(onClick = {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Home.route) { inclusive = true }
            }
        }) {
            Text("Sair")
        }
    }
}