package com.pomolistapp.feature_task.presentation.timer.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pomolist.feature_task.presentation.register_edit_task.components.TextFieldState
import com.pomolistapp.feature_task.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var workTime: Int = 0
    var breakTime: Int = 0
    var pomodoroCount: Int = 0

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id != -1) {
                viewModelScope.launch {
                    taskRepository.getTaskById(id)?.also {
                        workTime = it.workTime
                        breakTime = it.breakTime
                        pomodoroCount = it.pomodoroCount
                    }
                }
            }
        }
    }
}