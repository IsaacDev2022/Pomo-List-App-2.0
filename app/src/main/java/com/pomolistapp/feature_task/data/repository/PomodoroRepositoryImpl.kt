package com.pomolistapp.feature_task.data.repository

import com.pomolistapp.feature_task.data.local.PomodoroDAO
import com.pomolistapp.feature_task.domain.model.Pomodoro
import com.pomolistapp.feature_task.domain.repository.PomodoroRepository
import kotlinx.coroutines.flow.Flow

class PomodoroRepositoryImpl(
    private val pomodoroDAO: PomodoroDAO
): PomodoroRepository {
    override fun getAllPomodoros(): Flow<List<Pomodoro>> {
        return pomodoroDAO.getAllPomodoros()
    }

    override suspend fun insert(pomodoro: Pomodoro){
        return pomodoroDAO.insert(pomodoro)
    }

    override suspend fun update(pomodoro: Pomodoro) {
        return pomodoroDAO.update(pomodoro)
    }

    override suspend fun delete(pomodoro: Pomodoro) {
        return pomodoroDAO.delete(pomodoro)
    }
}