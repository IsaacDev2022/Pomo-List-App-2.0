package com.pomolist.feature_task.presentation.home

import com.pomolistapp.feature_task.domain.model.Task

data class HomeState(
    val tasks: List<Task> = emptyList()
)