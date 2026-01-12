package com.example.agendagora.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agendagora.domain.model.Servico
import com.example.agendagora.ui.components.EmptyState
import com.example.agendagora.ui.components.ServiceCard
import com.example.agendagora.ui.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateToAgendamento: (serviceId: String) -> Unit,
    onNavigateToMeusAgendamentos: () -> Unit
) {
    val services by viewModel.services.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("AgendAgora") },
            actions = {
                IconButton(onClick = onNavigateToMeusAgendamentos) {
                    Icon(imageVector = androidx.compose.material.icons.Icons.Default.List, contentDescription = "Meus agendamentos")
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (services.isEmpty()) {
            EmptyState(message = "Nenhum serviço disponível")
            return@Column
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(services) { servico ->
                ServiceCard(servico = servico, onAgendar = { onNavigateToAgendamento(servico.id) })
            }
            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}
