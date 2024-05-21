package com.pomolistapp.feature_task.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pomolistapp.feature_task.domain.model.Pomodoro
import kotlinx.coroutines.flow.Flow

@Dao
interface PomodoroDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pomodoro: Pomodoro)

    @Update
    suspend fun update(pomodoro: Pomodoro)

    @Query("SELECT * FROM Pomodoro")
    fun getAllPomodoros(): Flow<List<Pomodoro>>

    @Delete
    suspend fun delete(pomodoro: Pomodoro)
}