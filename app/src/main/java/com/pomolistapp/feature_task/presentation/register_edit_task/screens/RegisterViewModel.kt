package com.pomolistapp.feature_task.presentation.register_edit_task.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.pomolist.feature_task.presentation.register_edit_task.components.TextFieldState
import com.pomolistapp.feature_task.domain.model.Task
import com.pomolistapp.feature_task.domain.repository.TaskRepository
import com.pomolistapp.feature_task.presentation.register_edit_task.RegisterEvent
import com.pomolistapp.worker.NotificationExpireSoonTaskWorker
import com.pomolistapp.worker.NotificationNowTaskWorker
import com.pomolistapp.worker.NotificationTodayTaskWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var idTask: Int = 0

    private val _nameTask = mutableStateOf(TextFieldState())
    val nameTask: State<TextFieldState> = _nameTask

    private val _descriptionTask = mutableStateOf(TextFieldState())
    val descriptionTask: State<TextFieldState> = _descriptionTask

    private val _dateTask = mutableStateOf(TextFieldState())
    val dateTask: State<TextFieldState> = _dateTask

    private val _timeTask = mutableStateOf(TextFieldState())
    val timeTask: State<TextFieldState> = _timeTask

    private val _uiEventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    var priorityTask = mutableStateOf(0)

    private val _workTime = mutableStateOf(TextFieldState())
    val workTime: State<TextFieldState> = _workTime

    private val _breakTime = mutableStateOf(TextFieldState())
    val breakTime: State<TextFieldState> = _breakTime

    private val _pomodoroCount = mutableStateOf(TextFieldState())
    val pomodoroCount: State<TextFieldState> = _pomodoroCount


    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id != -1) {
                viewModelScope.launch {
                    taskRepository.getTaskById(id).also {
                        idTask = id
                        _nameTask.value = nameTask.value.copy(
                            text = it.name
                        )
                        _descriptionTask.value = descriptionTask.value.copy(
                            text = it.description
                        )
                        _dateTask.value = dateTask.value.copy(
                            text = it.date
                        )
                        _timeTask.value = timeTask.value.copy(
                            text = it.time
                        )
                        priorityTask.value = it.priority

                        _workTime.value = workTime.value.copy(
                            text = it.workTime
                        )
                        _breakTime.value = breakTime.value.copy(
                            text = it.breakTime
                        )
                        _pomodoroCount.value = pomodoroCount.value.copy(
                            text = it.pomodoroCount
                        )
                    }
                }
            }
        }
    }

    private fun scheduleNotification(task: Task) {
        // Notificações para horário
        val parts = task.time.split(":", " ")
        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()

        val timeSeconds = (hours * 3600) + (minutes * 60)

        val currentTimeMillis = LocalTime.now().toSecondOfDay()
        val oneHourInMillis = 3600
        val delay = timeSeconds - currentTimeMillis
        val delayExpiredTask = timeSeconds - currentTimeMillis - oneHourInMillis

        // Notificações para data
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val taskDateInSeconds = dateFormat.parse(task.date)?.time ?: return

        val calendar = Calendar.getInstance().apply {
            timeInMillis = taskDateInSeconds
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val notificationTimeInMillis = calendar.timeInMillis

        val delayTodayTask = (notificationTimeInMillis - currentTimeMillis) / 1000

        if (delay > 0) {
            val data = Data.Builder()
                .putString("taskName", task.name)
                .putString("taskDescription", task.description)
                .build()

            val workNowTaskRequest = OneTimeWorkRequestBuilder<NotificationNowTaskWorker>()
                .setInitialDelay(delay.toLong(), TimeUnit.SECONDS)
                .setInputData(data)
                .build()

            val workExpireTaskRequest = OneTimeWorkRequestBuilder<NotificationExpireSoonTaskWorker>()
                .setInitialDelay(delayExpiredTask.toLong(), TimeUnit.SECONDS)
                .setInputData(data)
                .build()

            val workTodayRequest = OneTimeWorkRequestBuilder<NotificationTodayTaskWorker>()
                .setInitialDelay(delayTodayTask, TimeUnit.SECONDS)
                .setInputData(data)
                .build()

            WorkManager.getInstance().enqueue(workNowTaskRequest)
            WorkManager.getInstance().enqueue(workExpireTaskRequest)
            WorkManager.getInstance().enqueue(workTodayRequest)
        }
    }

    fun onEvent(registerEvent: RegisterEvent) {
        when (registerEvent) {
            is RegisterEvent.EnteredName -> {
                _nameTask.value = nameTask.value.copy(
                    text = registerEvent.name
                )
            }
            is RegisterEvent.EnteredDescription -> {
                _descriptionTask.value = descriptionTask.value.copy(
                    text = registerEvent.description
                )
            }
            is RegisterEvent.EnteredPriority -> {
                priorityTask.value = registerEvent.priority.ordinal
            }

            is RegisterEvent.EnteredDate -> {
                _dateTask.value = dateTask.value.copy(
                    text = registerEvent.date
                )
            }
            is RegisterEvent.EnteredTime -> {
                _timeTask.value = timeTask.value.copy(
                    text = registerEvent.time
                )
            }
            is RegisterEvent.WorkTime -> {
                _workTime.value = workTime.value.copy(
                    text = registerEvent.workTime
                )
            }
            is RegisterEvent.BreakTime -> {
                _breakTime.value = breakTime.value.copy(
                    text = registerEvent.breakTime
                )
            }
            is RegisterEvent.PomodoroCount -> {
                _pomodoroCount.value = pomodoroCount.value.copy(
                    text = registerEvent.pomodoroCount
                )
            }
            is RegisterEvent.SaveTask -> {
                viewModelScope.launch {
                    val task = Task(
                        id = idTask,
                        name = nameTask.value.text,
                        description = descriptionTask.value.text,
                        priority = priorityTask.value,
                        date = dateTask.value.text,
                        time = timeTask.value.text,
                        workTime = workTime.value.text,
                        breakTime = breakTime.value.text,
                        pomodoroCount = pomodoroCount.value.text
                    )
                    taskRepository.insertTask(task)
                    scheduleNotification(task)
                    _uiEventFlow.emit(UiEvent.SaveTask)
                }
            }
        }
    }

    sealed class UiEvent {
        object SaveTask: UiEvent()
    }
}