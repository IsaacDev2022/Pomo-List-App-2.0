package com.pomolistapp.feature_task.presentation.register_edit_task.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pomolist.feature_task.presentation.register_edit_task.components.TextFieldState
import com.pomolistapp.feature_task.domain.model.Task
import com.pomolistapp.feature_task.domain.repository.TaskRepository
import com.pomolistapp.feature_task.presentation.register_edit_task.RegisterEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
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
    var workTime = mutableStateOf(0)
    var breakTime = mutableStateOf(0)
    var pomodoroCount = mutableStateOf(0)

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
                        workTime.value = it.workTime
                        breakTime.value = it.breakTime
                        pomodoroCount.value = it.pomodoroCount
                    }
                }
            }
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
            is RegisterEvent.SaveTask -> {
                viewModelScope.launch {
                    taskRepository.insertTask(
                        Task(
                            id = idTask,
                            name = nameTask.value.text,
                            description = descriptionTask.value.text,
                            priority = priorityTask.value,
                            date = dateTask.value.text,
                            time = timeTask.value.text,
                            workTime = workTime.value,
                            breakTime = breakTime.value,
                            pomodoroCount = pomodoroCount.value
                        )
                    )
                    _uiEventFlow.emit(UiEvent.SaveTask)
                }
            }
        }
    }

    sealed class UiEvent {
        object SaveTask: UiEvent()
    }
}