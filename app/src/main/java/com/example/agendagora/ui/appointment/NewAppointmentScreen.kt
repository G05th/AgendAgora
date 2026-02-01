package com.example.agendagora.ui.appointment

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agendagora.data.local.entity.Appointment
import com.example.agendagora.viewmodel.AppointmentViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewAppointmentScreen(
    navController: NavController,
    viewModel: AppointmentViewModel = viewModel()
) {
    var selectedService by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val services = listOf(
        "Emissão de BI/Cartão de Cidadão",
        "Passaporte",
        "Carta de Condução (DETRAN)",
        "Registo Criminal",
        "Pagamento de Impostos (AGT)",
        "Consulta Médica (SNS)",
        "Registo Civil (Nascimento/Casamento)",
        "Outros Serviços"
    )

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Novo Agendamento", style = MaterialTheme.typography.headlineMedium)

            // Dropdown Tipo de Serviço
            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = { /* readonly */ }
            ) {
                OutlinedTextField(
                    value = selectedService,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de Serviço") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = false,
                    onDismissRequest = {}
                ) {
                    services.forEach { service ->
                        DropdownMenuItem(
                            text = { Text(service) },
                            onClick = { selectedService = service }
                        )
                    }
                }
            }

            // Data
            OutlinedTextField(
                value = selectedDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "",
                onValueChange = {},
                label = { Text("Data") },
                readOnly = true,
                trailingIcon = { IconButton(onClick = { showDatePicker = true }) { /* calendar icon */ } },
                modifier = Modifier.fillMaxWidth()
            )

            // Hora
            OutlinedTextField(
                value = selectedTime?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "",
                onValueChange = {},
                label = { Text("Hora") },
                readOnly = true,
                trailingIcon = { IconButton(onClick = { showTimePicker = true }) { /* clock icon */ } },
                modifier = Modifier.fillMaxWidth()
            )

            // Local
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Local (opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            // Descrição
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Button(
                onClick = {
                    if (selectedService.isBlank() || selectedDate == null || selectedTime == null) {
                        // erro (pode adicionar snackbar de erro)
                    } else {
                        val appointment = Appointment(
                            type = selectedService,
                            date = selectedDate!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            time = selectedTime!!.format(DateTimeFormatter.ofPattern("HH:mm")),
                            location = location,
                            description = description,
                            id = TODO(),
                            userId = TODO(),
                            status = TODO()
                        )
                        viewModel.addAppointment(appointment)
                        // Sucesso
                        navController.popBackStack()
                        // Snackbar mostraria aqui, mas como volta, pode mostrar na Home depois
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agendar")
            }

            TextButton(onClick = { navController.popBackStack() }) {
                Text("Cancelar")
            }
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        if (showTimePicker) {
            AlertDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(onClick = { showTimePicker = false }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showTimePicker = false }) { Text("Cancelar") }
                },
                text = {
                    TimePicker(state = timePickerState)
                }
            )
        }
    }
}