package com.example.agendagora.ui.screens.agendamento

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agendagora.ui.components.PrimaryButton

@Composable
fun AgendamentoScreen(
    serviceId: String,
    viewModel: AgendamentoViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // local fields
    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") } // YYYY-MM-DD (simple placeholder)
    var time by remember { mutableStateOf("") } // HH:mm

    LaunchedEffect(serviceId) {
        viewModel.loadService(serviceId)
    }

    LaunchedEffect(uiState.created) {
        if (uiState.created) onSuccess()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {
        Text(text = uiState.service?.title ?: "Agendamento", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = uiState.service?.description ?: "", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nome do cidadão") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Data (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("Hora (HH:mm)") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.error != null) {
            Text(text = uiState.error!!, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        PrimaryButton(text = if (uiState.isLoading) "Agendando..." else "Confirmar agendamento", onClick = {
            viewModel.createAgendamento(name.trim(), date.trim(), time.trim())
        }, enabled = !uiState.isLoading)

        Spacer(modifier = Modifier.height(12.dp))
        PrimaryButton(text = "Voltar", onClick = onBack)
    }
}
