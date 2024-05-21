package com.pomolistapp.feature_task.data.repository

import com.pomolistapp.feature_task.data.local.TaskDAO
import com.pomolistapp.feature_task.domain.model.Task
import com.pomolistapp.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val taskDAO: TaskDAO
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDAO.getAllTasks()
    }

    override suspend fun insertTask(task: Task) {
        return taskDAO.insertTask(task)
    }

    override suspend fun updateTask(task: Task) {
        return taskDAO.updateTask(task)
    }

    override suspend fun getTaskById(id: Int): Task {
        return taskDAO.getTaskById(id)
    }

    override suspend fun deleteTask(task: Task) {
        return taskDAO.deleteTask(task)
    }
}