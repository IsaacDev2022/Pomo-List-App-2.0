package com.pomolistapp.feature_task.presentation.home.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pomolist.feature_task.presentation.home.HomeEvent
import com.pomolist.feature_task.presentation.home.HomeState
import com.pomolistapp.feature_task.domain.model.Task
import com.pomolistapp.feature_task.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    val taskListTodayTasks = mutableStateListOf<Task>()
    val taskListUpcoming = mutableStateListOf<Task>()
    val taskListLate = mutableStateListOf<Task>()
    val taskListCompleted = mutableStateListOf<Task>()
    val taskListHighPriority = mutableStateListOf<Task>()
    val taskListLowPriority = mutableStateListOf<Task>()

    var task: Task by mutableStateOf(Task())

    init {
        taskRepository.getAllTasks().onEach { tasks ->
            _state.value = state.value.copy(
                tasks = tasks
            )

            // Filtro de tarefas de hoje
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val currentDate = LocalDate.now().format(formatter)
            val listTasksToday = tasks.filter {
                it.date == currentDate
            }
            taskListTodayTasks.clear()
            taskListTodayTasks.addAll(listTasksToday)

            // Filtro de tarefas em breve
            val listUpcoming = tasks.filter {
                it.date > currentDate
            }
            taskListUpcoming.clear()
            taskListUpcoming.addAll(listUpcoming)

            // Filtro de tarefas expiradas
            val listLate = tasks.filter {
                it.date < currentDate
            }
            taskListLate.clear()
            taskListLate.addAll(listLate)

            // Filtro de tarefas terminadas
            val listCompleted = tasks.filter {
                it.completed
            }

            taskListCompleted.clear()
            taskListCompleted.addAll(listCompleted)

            // Filtro de tarefas de maior prioridade
            val listHighPriority = tasks.sortedByDescending {
                it.priority
            }

            taskListHighPriority.clear()
            taskListHighPriority.addAll(listHighPriority)

            // Filtro de tarefas de menor prioridade
            val listLowPriority = tasks.sortedBy {
                it.priority
            }

            taskListLowPriority.clear()
            taskListLowPriority.addAll(listLowPriority)

        }.launchIn(viewModelScope)
    }

    // Método criado apenas para teste
    fun getRepositories() {
        taskRepository.getAllTasks().onEach { tasks ->
            _state.value = state.value.copy(
                tasks = tasks
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnCompleted -> {
                viewModelScope.launch {
                    task = taskRepository.getTaskById(event.taskId)
                    task = task.copy(completed = event.isCompleted)
                    taskRepository.updateTask(task)
                }
            }
            is HomeEvent.DeleteTask -> {
                viewModelScope.launch {
                    taskRepository.deleteTask(event.task)
                }
            }
        }
    }
}