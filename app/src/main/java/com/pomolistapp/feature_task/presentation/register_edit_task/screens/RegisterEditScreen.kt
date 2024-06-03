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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.pomolist.feature_task.presentation.register_edit_task.components.TextFieldState
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

    var nameStateError by remember { mutableStateOf("") }
    var descriptionStateError by remember { mutableStateOf("") }
    var dateStateError by remember { mutableStateOf("") }
    var timeStateError by remember { mutableStateOf("") }
    var workTimeStateError by remember { mutableStateOf("") }
    var breakTimeStateError by remember { mutableStateOf("") }
    var pomodoroCountStateError by remember { mutableStateOf("") }

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
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("NameTF"),
                value = nameState.text,
                onValueChange = { registerViewModel.onEvent(RegisterEvent.EnteredName(it)) },
                label = { Text("Nome") },
                isError = nameStateError.isNotEmpty(),
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
            if (nameStateError.isNotEmpty()) {
                Text(
                    text = nameStateError,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .testTag("DescriptionTF"),
                value = descriptionState.text,
                onValueChange = { registerViewModel.onEvent(RegisterEvent.EnteredDescription(it)) },
                label = { Text("Descrição") },
                isError = descriptionStateError.isNotEmpty(),
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
            if (descriptionStateError.isNotEmpty()) {
                Text(
                    text = descriptionStateError,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            val focusManager = LocalFocusManager.current

            // Date Picker
            val datePickerState = rememberDatePickerState()
            var showDatePicker by remember { mutableStateOf(false) }

            var selectedDate by remember { mutableStateOf(dateState.text) }

            // Time Picker
            val timePickerState = rememberTimePickerState(is24Hour = true)
            var showTimePicker by remember { mutableStateOf(false) }

            var selectedTime by remember { mutableStateOf(timeState.text) }

            val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                // Date Picker TextField
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

                Column {
                    OutlinedTextField(
                        modifier = Modifier
                            .width(170.dp)
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
                        isError = dateStateError.isNotEmpty(),
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.padding(end = 10.dp),
                                imageVector = Icons.Filled.DateRange,
                                tint = primaryColor,
                                contentDescription = "Imagem"
                            )
                        }
                    )
                    if (dateStateError.isNotEmpty()) {
                        Text(
                            text = dateStateError,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }

                // Time Picker TextField
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

                Column {
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
                        isError = timeStateError.isNotEmpty(),
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.padding(end = 10.dp),
                                imageVector = Icons.Filled.Timer,
                                tint = primaryColor,
                                contentDescription = "Imagem"
                            )
                        }
                    )
                    if (timeStateError.isNotEmpty()) {
                        Text(
                            text = timeStateError,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Prioridade",
                color = primaryColor,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(15.dp))

            var taskPriority by remember { mutableStateOf(Priority.MEDIUM) }

            PriorityComponent {
                taskPriority = it
                registerViewModel.onEvent(RegisterEvent.EnteredPriority(taskPriority))
            }

            // Pomodoro
            val minutesTask = remember { mutableStateOf(workTime) }
            val breakTask = remember { mutableStateOf(breakTime) }
            val pomodoroCount = remember { mutableStateOf(pomodoroCount) }

            Spacer(modifier = Modifier.height(15.dp))
            Column {
                Text(
                    text = "Pomodoro",
                    color = primaryColor,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Column {
                        OutlinedTextField(
                            modifier = Modifier.width(160.dp),
                            value = minutesTask.value,
                            onValueChange = { minutesTask.value = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            label = { Text("Tempo") },
                            isError = workTimeStateError.isNotEmpty(),
                            leadingIcon = {
                                Icon(
                                    modifier = Modifier.padding(end = 10.dp),
                                    imageVector = Icons.Filled.PunchClock,
                                    tint = primaryColor,
                                    contentDescription = "Imagem"
                                )
                            }
                        )
                        if (workTimeStateError.isNotEmpty()) {
                            Text(
                                text = workTimeStateError,
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Column {
                        OutlinedTextField(
                            modifier = Modifier.width(160.dp),
                            value = breakTask.value,
                            onValueChange = { breakTask.value = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            label = { Text("Intervalo") },
                            isError = breakTimeStateError.isNotEmpty(),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.LockClock,
                                    tint = primaryColor,
                                    contentDescription = "Imagem"
                                )
                            }
                        )
                        if (breakTimeStateError.isNotEmpty()) {
                            Text(
                                text = breakTimeStateError,
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                OutlinedTextField(
                    modifier = Modifier.width(180.dp),
                    value = pomodoroCount.value,
                    onValueChange = { pomodoroCount.value = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    label = { Text("Quantidade") },
                    isError = pomodoroCountStateError.isNotEmpty(),
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.padding(end = 6.dp),
                            imageVector = Icons.Filled.LockClock,
                            tint = primaryColor,
                            contentDescription = "Imagem"
                        )
                    }
                )
                if (pomodoroCountStateError.isNotEmpty()) {
                    Text(
                        text = pomodoroCountStateError,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }

            registerViewModel.workTime.value = minutesTask.value
            registerViewModel.breakTime.value = breakTask.value
            registerViewModel.pomodoroCount.value = pomodoroCount.value

            Spacer(modifier = Modifier.height(5.dp))

//            BottomBar(
//                onInsertTask = {
//                    registerViewModel.onEvent(RegisterEvent.SaveTask)
//                },
//                name = nameState,
//                nameError = nameStateError
//            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(50.dp)
                    .testTag("btnConfirmar"),
                onClick = {
                    var isValid = true
                    if (nameState.text.isEmpty()) {
                        nameStateError = "Preencha o campo nome"
                        isValid = false
                    } else {
                        nameStateError = ""
                    }
                    if (descriptionState.text.isEmpty()) {
                        descriptionStateError = "Preencha o campo descrição"
                        isValid = false
                    } else {
                        descriptionStateError = ""
                    }
                    if (selectedDate.isEmpty()) {
                        dateStateError = "Preencha o campo data"
                        isValid = false
                    } else {
                        descriptionStateError = ""
                    }
                    if (selectedTime.isEmpty()) {
                        timeStateError = "Preencha o campo horário"
                        isValid = false
                    } else {
                        descriptionStateError = ""
                    }
                    if (minutesTask.value.isEmpty()) {
                        workTimeStateError = "Preencha o campo pomodoro"
                        isValid = false
                    } else {
                        workTimeStateError = ""
                    }
                    if (breakTask.value.isEmpty()) {
                        breakTimeStateError = "Preencha o campo intervalo"
                        isValid = false
                    } else {
                        breakTimeStateError = ""
                    }
                    if (pomodoroCount.value.isEmpty()) {
                        pomodoroCountStateError = "Preencha o campo quantidade"
                        isValid = false
                    } else {
                        pomodoroCountStateError = ""
                    }

                    if (isValid) {
                        registerViewModel.onEvent(RegisterEvent.SaveTask)
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(primaryColor)
            ) {
                Text(
                    text = "Confirmar",
                    fontSize = 15.sp
                )
            }
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
    onInsertTask: () -> Unit,
    name: TextFieldState,
    nameError: String
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .height(50.dp)
            .testTag("btnConfirmar"),
        onClick = {
            var isValid = true
            if (name.text.isEmpty()) {

            }
            onInsertTask()
                  },
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