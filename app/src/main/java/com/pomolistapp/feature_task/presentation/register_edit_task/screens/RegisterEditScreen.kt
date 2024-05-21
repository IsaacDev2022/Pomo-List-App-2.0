package com.pomolistapp.feature_task.presentation.register_edit_task.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PunchClock
import androidx.compose.material.icons.filled.TextDecrease
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pomolist.feature_task.presentation.register_edit_task.components.Priority
import com.pomolist.feature_task.presentation.register_edit_task.components.PriorityComponent
import com.pomolistapp.core.navigation.Screen
import com.pomolistapp.feature_task.presentation.register_edit_task.RegisterEvent
import com.pomolistapp.ui.theme.primaryColor
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterEditScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    var nameState = registerViewModel.nameTask.value
    var descriptionState = registerViewModel.descriptionTask.value
    var dateState = registerViewModel.dateTask.value
    var timeState = registerViewModel.timeTask.value
    var workTime = registerViewModel.workTime.value
    var breakTime = registerViewModel.breakTime.value
    var pomodoroCount = registerViewModel.pomodoroCount.value

    LaunchedEffect(key1 = true) {
        registerViewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is RegisterViewModel.UiEvent.SaveTask -> {
                    navController.navigateUp()
                }

                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp),
                        text = "Adicionar Tarefa",
                        color = primaryColor,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.HomeScreen.route)
                    }) {
                        Icon(
                            modifier = Modifier
                                .size(60.dp)
                                .padding(start = 10.dp),
                            tint = primaryColor,
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 16.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("NameTF"),
                value = nameState.text,
                onValueChange = { registerViewModel.onEvent(RegisterEvent.EnteredName(it)) },
                label = { Text("Nome") },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = Icons.Filled.Person,
                        tint = primaryColor,
                        contentDescription = "Imagem"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .testTag("DescriptionTF"),
                value = descriptionState.text,
                onValueChange = { registerViewModel.onEvent(RegisterEvent.EnteredDescription(it)) },
                label = { Text("Descrição") },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = Icons.Filled.TextDecrease,
                        tint = primaryColor,
                        contentDescription = "Imagem"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                val focusManager = LocalFocusManager.current
                // Date Picker
                val datePickerState = rememberDatePickerState()
                var showDatePicker by remember { mutableStateOf(false) }

                var selectedDate by remember { mutableStateOf(dateState.text) }

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { /*TODO*/ },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showDatePicker = false
                                    datePickerState
                                        .selectedDateMillis?.let { millis ->
                                            selectedDate = millis.toBrazilianDateFormat()
                                        }
                                    registerViewModel.onEvent(RegisterEvent.EnteredDate(selectedDate))
                                }
                            ) { Text("OK") }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    showDatePicker = false
                                }
                            ) { Text("Cancel") }
                        }
                    )
                    {
                        DatePicker(state = datePickerState)
                    }
                }

                OutlinedTextField(
                    modifier = Modifier
                        .width(160.dp)
                        .onFocusEvent {
                            if (it.isFocused) {
                                showDatePicker = true
                                focusManager.clearFocus(force = true)
                            }
                        }
                        .testTag("DateTF"),
                    value = selectedDate,
                    onValueChange = { selectedDate = it },
                    label = { Text("Data") },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.padding(end = 10.dp),
                            imageVector = Icons.Filled.DateRange,
                            tint = primaryColor,
                            contentDescription = "Imagem"
                        )
                    }
                )

                // Time Picker
                val timePickerState = rememberTimePickerState(is24Hour = true)
                var showTimePicker by remember { mutableStateOf(false) }

                var selectedTime by remember { mutableStateOf(timeState.text) }

                val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

                if (showTimePicker) {
                    TimePickerDialog(
                        title = "00:00",
                        onDismissRequest = { /*TODO*/ },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    val cal = Calendar.getInstance()
                                    cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                    cal.set(Calendar.MINUTE, timePickerState.minute)
                                    selectedTime = formatter.format(cal.time)
                                    showTimePicker = false
                                    registerViewModel.onEvent(RegisterEvent.EnteredTime(selectedTime))
                                }
                            ) { Text("OK") }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    showTimePicker = false
                                }
                            ) { Text("Cancel") }
                        }
                    ) {
                        TimePicker(state = timePickerState)
                    }
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusEvent {
                            if (it.isFocused) {
                                showTimePicker = true
                                focusManager.clearFocus(force = true)
                            }
                        }
                        .testTag("TimeTF"),
                    value = selectedTime,
                    onValueChange = { selectedTime = it },
                    label = { Text("Horário") },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.padding(end = 10.dp),
                            imageVector = Icons.Filled.Timer,
                            tint = primaryColor,
                            contentDescription = "Imagem"
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Prioridade",
                color = primaryColor,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            var taskPriority by remember { mutableStateOf(Priority.MEDIUM) }

            PriorityComponent {
                taskPriority = it
                registerViewModel.onEvent(RegisterEvent.EnteredPriority(taskPriority))
            }

            // Pomodoro
            val minutesTask = remember { mutableStateOf(workTime.toString()) }
            val breakTask = remember { mutableStateOf(workTime.toString()) }
            val pomodoroCount = remember { mutableStateOf(pomodoroCount.toString()) }

            Spacer(modifier = Modifier.height(20.dp))
            Column {
                Text(
                    text = "Pomodoro",
                    color = primaryColor,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    OutlinedTextField(
                        modifier = Modifier.width(110.dp),
                        value = minutesTask.value,
                        onValueChange = { minutesTask.value = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        label = { Text("Tempo") },
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.padding(end = 10.dp),
                                imageVector = Icons.Filled.PunchClock,
                                tint = primaryColor,
                                contentDescription = "Imagem"
                            )
                        }
                    )
                    OutlinedTextField(
                        modifier = Modifier.width(110.dp),
                        value = breakTask.value,
                        onValueChange = { breakTask.value = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        label = { Text("Intervalo") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.LockClock,
                                tint = primaryColor,
                                contentDescription = "Imagem"
                            )
                        }
                    )
                    OutlinedTextField(
                        modifier = Modifier.width(110.dp),
                        value = pomodoroCount.value,
                        onValueChange = { pomodoroCount.value = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        label = { Text("Quantidade") },
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.padding(end = 6.dp),
                                imageVector = Icons.Filled.LockClock,
                                tint = primaryColor,
                                contentDescription = "Imagem"
                            )
                        }
                    )

                    registerViewModel.workTime.value = minutesTask.value.toInt()
                    registerViewModel.breakTime.value = pomodoroCount.value.toInt()
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            BottomBar(
                onInsertTask = {
                    registerViewModel.onEvent(RegisterEvent.SaveTask)
                }
            )
        }

    }
}

@Composable
fun TimePickerDialog(
    title: String,
    onDismissRequest: () -> Unit,
    confirmButton: @Composable (() -> Unit),
    dismissButton: @Composable (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = containerColor
                ),
            color = containerColor
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onInsertTask: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .height(50.dp)
            .testTag("btnConfirmar"),
        onClick = { onInsertTask() },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(primaryColor)
    ) {
        Text(
            text = "Confirmar",
            fontSize = 15.sp
        )
    }
}

fun Long.toBrazilianDateFormat(
    pattern: String = "dd/MM/yyyy"
): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(
        pattern, Locale("pt-br")
    ).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    return formatter.format(date)
}