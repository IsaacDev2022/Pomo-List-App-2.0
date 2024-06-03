package com.pomolistapp.feature_task.presentation.timer.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pomolist.feature_task.presentation.register_edit_task.components.TextFieldState
import com.pomolistapp.feature_task.domain.repository.TaskRepository
import com.pomolistapp.feature_task.presentation.register_edit_task.RegisterEvent
import com.pomolistapp.feature_task.presentation.register_edit_task.screens.RegisterViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id != -1) {
                viewModelScope.launch {
                    taskRepository.getTaskById(id)?.also {
                        _uiState.value = UiState.Success(
                            workTime = it.workTime.toInt(),
                            breakTime = it.breakTime.toInt(),
                            pomodoroCount = it.pomodoroCount.toInt()
                        )

                    }
                }
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(
            val workTime: Int,
            val breakTime: Int,
            val pomodoroCount: Int
        ) : UiState()
        data class Error(val message: String) : UiState()
    }
}