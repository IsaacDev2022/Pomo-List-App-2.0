package com.pomolistapp.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pomolistapp.feature_task.presentation.home.screens.HomeScreen
import com.pomolistapp.feature_task.presentation.register_edit_task.screens.RegisterEditScreen
import com.pomolistapp.feature_task.presentation.timer.screens.TimerScreen
import com.pomolistapp.feature_task.presentation.timer.screens.TimerTaskScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.TimerScreen.route
        ) {
            TimerScreen(navController)
        }
        composable(
            route = Screen.TimerTaskScreen.route,
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry  ->
            TimerTaskScreen(navController)
        }
        composable(
            route = Screen.RegisterScreen.route,
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry  ->
            RegisterEditScreen(navController)
        }
    }
}