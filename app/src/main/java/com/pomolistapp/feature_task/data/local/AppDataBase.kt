package com.pomolistapp.feature_task.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pomolistapp.feature_task.domain.model.Converters
import com.pomolistapp.feature_task.domain.model.Pomodoro
import com.pomolistapp.feature_task.domain.model.Task

@Database(entities = [Task::class, Pomodoro::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract val taskDAO: TaskDAO
    abstract val pomodoroDAO: PomodoroDAO

    companion object {
        const val DATABASE_NAME = "pomoList_DB2"
    }
}