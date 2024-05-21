package com.pomolistapp.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val priority: Int = 0,
    val date: String = "",
    val time: String = "",
    var workTime: Int = 0,
    var breakTime: Int = 0,
    var pomodoroCount: Int = 0,
    var completed: Boolean = false
)