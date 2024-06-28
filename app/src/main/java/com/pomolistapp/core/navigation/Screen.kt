package com.pomolistapp.core.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home")
    object TimerScreen: Screen("timer")
    object TimerTaskScreen: Screen("timerTaskScreen?id={id}")
    object TimerTest: Screen("timerTest")
    object SettingsScreen: Screen("settings")
    object RegisterScreen: Screen("registerScreen?id={id}")
    fun passIdTimer(id: Int?): String {
        return "timerTaskScreen?id=$id"
    }
    fun passId(id: Int?): String {
        return "registerScreen?id=$id"
    }

    fun passIdEdit(id: Int?): String {
        return "editScreen?id=$id"
    }
}