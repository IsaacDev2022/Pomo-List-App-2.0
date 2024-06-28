package com.pomolistapp.feature_task.presentation.settings.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pomolistapp.core.navigation.Screen
import com.pomolistapp.feature_task.presentation.settings.components.SettingsContent
import com.pomolistapp.ui.theme.primaryColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, ) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp),
                        text = "Configurações",
                        color = primaryColor,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.TimerScreen.route)
                    }) {
                        Icon(
                            modifier = Modifier
                                .size(60.dp)
                                .padding(start = 10.dp),
                            tint = primaryColor,
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column (
            modifier = Modifier.padding(padding)
        ) {
            SettingsContent(modifier = Modifier.padding(padding))
        }
    }
}