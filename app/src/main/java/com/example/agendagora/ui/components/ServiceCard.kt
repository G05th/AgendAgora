package com.example.agendagora.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agendagora.domain.model.Servico

@Composable
fun ServiceCard(servico: Servico, onAgendar: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = servico.title, style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = servico.description, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryButton(text = "Agendar", onClick = onAgendar)
        }
    }
}
