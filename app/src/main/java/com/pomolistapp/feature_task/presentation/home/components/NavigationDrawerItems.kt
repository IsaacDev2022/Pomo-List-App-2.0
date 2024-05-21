package com.pomolist.feature_task.presentation.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.pomolistapp.core.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawerItems(
    navController: NavController,
    drawerState: DrawerState
) {
    var scope = rememberCoroutineScope()

    var currentBackStackEntryAsState = navController.currentBackStackEntryAsState()

    var destination = currentBackStackEntryAsState.value?.destination

    NavigationDrawerItem(
        icon = { Icon(Icons.Filled.Home, contentDescription = "HomeScreen") },
        label = { Text(text = "Home") },
        selected = destination?.route == Screen.HomeScreen.route,
        onClick = {
            navController.navigate(Screen.HomeScreen.route, navOptions {
                this.launchSingleTop = true
                this.restoreState = true

            })
            scope.launch {
                drawerState.close()
            }

        }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )

    Spacer(modifier = Modifier.height(10.dp))

    NavigationDrawerItem(
        icon = { Icon(Icons.Filled.Timer, contentDescription = "TimerScreen") },
        label = { Text(text = "Pomodoro") },
        selected = destination?.route == Screen.TimerScreen.route,
        onClick = {
            navController.navigate(Screen.TimerScreen.route, navOptions {
                this.launchSingleTop = true
                this.restoreState = true

            })
            scope.launch {
                drawerState.close()
            }

        }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )

    Spacer(modifier = Modifier.height(10.dp))
}