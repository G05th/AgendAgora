package com.example.agendagora.ui.screens.meusagendamentos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agendagora.ui.components.EmptyState
import com.example.agendagora.ui.components.GlobalScaffold
import com.example.agendagora.ui.components.PrimaryButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeusAgendamentosScreen(
    viewModel: MeusAgendamentosViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBack: () -> Unit
) {
    val agendamentos by viewModel.agendamentos.collectAsState()
    val snackbarScope = rememberCoroutineScope()

    GlobalScaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text("Meus Agendamentos") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
            }
        )
    }) { snackbarHost ->
        if (agendamentos.isEmpty()) {
            EmptyState(title = "Sem agendamentos", message = "Você ainda não marcou nenhum atendimento")
            return@GlobalScaffold
        }

        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(agendamentos) { a ->
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = a.servicoTitle, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "${a.dateIso} — ${a.time}", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(onClick = {
                                viewModel.cancelAgendamento(a.id)
                                snackbarScope.launch { snackbarHost.showSnackbar("Agendamento cancelado") }
                            }) { Text("Cancelar") }

                            PrimaryButton(text = "Remarcar", onClick = {
                                // flow de remarcar pode navegar para Agendamento com pre-fill (futuro)
                                snackbarScope.launch { snackbarHost.showSnackbar("Funcionalidade de remarcar pendente") }
                            }, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}
