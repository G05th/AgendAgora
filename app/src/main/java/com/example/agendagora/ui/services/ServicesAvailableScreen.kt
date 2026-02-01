package com.example.agendagora.ui.services

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agendagora.navigation.Screen

val servicesList = listOf(
    "Emissão de BI/Cartão de Cidadão",
    "Passaporte",
    "Carta de Condução",
    "Registo Criminal",
    "Pagamento de Impostos (AGT)",
    "Consulta Médica (SNS)",
    "Registo Civil (Nascimento/Casamento)",
    "Outros Serviços Públicos"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesAvailableScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Serviços Públicos Disponíveis") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(servicesList) { service ->
                Card(
                    onClick = {
                        navController.navigate(Screen.NewAppointment.createRoute(service))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = service,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Toque para agendar",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}