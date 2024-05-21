package com.pomolistapp.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pomodoro(
    @PrimaryKey (autoGenerate = true) val idPomodoro: Long,
    var minutes: Long = 0,
    var seconds: Long = 0,
    var amount: Long = 0,
)
