package com.example.agendagora.ui.screens.meusagendamentos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agendagora.ui.components.EmptyState
import com.example.agendagora.ui.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeusAgendamentosScreen(
    viewModel: MeusAgendamentosViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBack: () -> Unit
) {
    val agendamentos by viewModel.agendamentos.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Meus Agendamentos") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    androidx.compose.material3.Icon(imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (agendamentos.isEmpty()) {
            EmptyState(message = "Você não tem agendamentos.")
            return@Column
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(agendamentos) { a ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = a.servicoTitle, style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "${a.dateIso} • ${a.time} • ${a.citizenName}", style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        PrimaryButton(text = "Cancelar", onClick = { viewModel.cancelAgendamento(a.id) })
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(64.dp)) }
        }
    }
}
