package com.example.agendagora.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agendagora.domain.model.Servico
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange

@Composable
fun ServiceCard(
    servico: Servico,
    onAgendar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = servico.title, style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = servico.description, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium, maxLines = 3)
                }
                IconButton(onClick = onAgendar) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Agendar ${servico.title}")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            PrimaryButton(text = "Agendar", onClick = onAgendar, modifier = Modifier.fillMaxWidth())
        }
    }
}
