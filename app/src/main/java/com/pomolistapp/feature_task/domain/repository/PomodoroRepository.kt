package com.pomolistapp.feature_task.domain.repository

import com.pomolistapp.feature_task.domain.model.Pomodoro
import kotlinx.coroutines.flow.Flow

interface PomodoroRepository {

    fun getAllPomodoros(): Flow<List<Pomodoro>>

    suspend fun insert(pomodoro: Pomodoro)

    suspend fun update(pomodoro: Pomodoro)

    suspend fun delete(pomodoro: Pomodoro)
}