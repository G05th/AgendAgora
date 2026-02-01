package com.example.agendagora.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agendagora.domain.model.Servico
import com.example.agendagora.ui.components.EmptyState
import com.example.agendagora.ui.components.GlobalScaffold
import com.example.agendagora.ui.components.ServiceCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateToAgendamento: (serviceId: String) -> Unit,
    onNavigateToMeusAgendamentos: () -> Unit
) {
    // OBS: especifica initial para resolver "Cannot infer type parameter 'T'"
    val services by viewModel.services.collectAsState(initial = emptyList())

    var query by remember { mutableStateOf("") }
    val snackbarScope = rememberCoroutineScope()

    GlobalScaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text("AgendAgora") },
            actions = {
                IconButton(onClick = onNavigateToMeusAgendamentos) {
                    Icon(imageVector = Icons.Default.List, contentDescription = "Meus agendamentos")
                }
            }
        )
    }) { snackbarHost ->
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Pesquisar serviço") },
                placeholder = { Text("Pesquisar serviço (ex: certidão, identificação)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Surface(modifier = Modifier.fillMaxWidth(), tonalElevation = 2.dp, shape = MaterialTheme.shapes.medium) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "Como funciona", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Escolha um serviço, selecione data e hora, confirme. Receberá um comprovativo.", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            val filtered = if (query.isBlank()) services else services.filter {
                it.title.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
            }

            if (filtered.isEmpty()) {
                EmptyState(title = "Nenhum serviço encontrado", message = "Ajuste sua pesquisa ou volte mais tarde")
                return@GlobalScaffold
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize()) {
                items(filtered) { servico ->
                    ServiceCard(servico = servico, onAgendar = {
                        snackbarScope.launch {
                            snackbarHost.showSnackbar("Abrindo agendamento para ${servico.title}")
                        }
                        onNavigateToAgendamento(servico.id)
                    })
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}
