package com.pomolistapp.feature_task.presentation.register_edit_task

import com.pomolist.feature_task.presentation.register_edit_task.components.Priority

sealed class RegisterEvent {
    data class EnteredName(val name: String) : RegisterEvent()
    data class EnteredDescription(val description: String) : RegisterEvent()
    data class EnteredPriority(val priority: Priority) : RegisterEvent()
    data class EnteredDate(val date: String) : RegisterEvent()
    data class EnteredTime(val time: String) : RegisterEvent()
    object SaveTask : RegisterEvent()
}