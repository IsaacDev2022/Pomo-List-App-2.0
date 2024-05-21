package com.pomolistapp.feature_task.domain.repository

import com.pomolistapp.feature_task.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAllTasks(): Flow<List<Task>>

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun getTaskById(id: Int): Task

    suspend fun deleteTask(task: Task)
}