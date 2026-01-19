package com.example.agendagora.ui.screens.agendamento

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agendagora.ui.components.GlobalScaffold
import com.example.agendagora.ui.components.PrimaryButton
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendamentoScreen(
    serviceId: String,
    viewModel: AgendamentoViewModel = viewModel(),
    onSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarScope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    // load service
    LaunchedEffect(serviceId) { viewModel.loadService(serviceId) }
    LaunchedEffect(uiState.created) { if (uiState.created) onSuccess() }

    // Date picker
    val calendar = Calendar.getInstance()
    val datePicker = DatePickerDialog(context, { _: DatePicker, y: Int, m: Int, d: Int ->
        date = String.format("%04d-%02d-%02d", y, m + 1, d)
    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

    // Time picker
    val timePicker = TimePickerDialog(context, { _: TimePicker, h: Int, min: Int ->
        time = String.format("%02d:%02d", h, min)
    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)

    GlobalScaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(uiState.service?.title ?: "Agendamento") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
            }
        )
    }) { snackbarHost ->
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = uiState.service?.description ?: "", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nome completo") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = date, onValueChange = { }, readOnly = true, label = { Text("Data") }, modifier = Modifier.weight(1f), trailingIcon = {
                    TextButton(onClick = { datePicker.show() }) { Text("Selecionar") }
                })
                OutlinedTextField(value = time, onValueChange = { }, readOnly = true, label = { Text("Hora") }, modifier = Modifier.weight(1f), trailingIcon = {
                    TextButton(onClick = { timePicker.show() }) { Text("Selecionar") }
                })
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.error != null) {
                Text(text = uiState.error!!, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
            }

            val enabled = name.isNotBlank() && date.isNotBlank() && time.isNotBlank() && !uiState.isLoading

            PrimaryButton(text = if (uiState.isLoading) "Agendando..." else "Confirmar agendamento", onClick = {
                viewModel.createAgendamento(name, date, time)
                snackbarScope.launch { snackbarHost.showSnackbar("Tentando criar agendamento...") }
            }, enabled = enabled)

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Cancelar")
            }
        }
    }
}
