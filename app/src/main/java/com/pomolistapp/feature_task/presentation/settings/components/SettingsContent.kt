package com.pomolistapp.feature_task.presentation.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TimerOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.pomolistapp.ui.theme.primaryColor

@Composable
fun SettingsContent(modifier: Modifier = Modifier) {
    var pomodoroSettings by remember { mutableStateOf(false) }
    var intervalSettings by remember { mutableStateOf(false) }
    var notificationSettings by remember { mutableStateOf(false) }
    var informationSettings by remember { mutableStateOf(false) }

    if (pomodoroSettings) {
        SettingsComponent(
            text = "Pomodoro",
            icon = Icons.Default.Timer,
            handleExpandSettings = {
                pomodoroSettings = !pomodoroSettings
            },
            isExpanded = pomodoroSettings
        )
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(10.dp)
                    .width(100.dp),
                value = "",
                onValueChange = {},
                label = { Text("00:00") }
            )
        }
    } else {
        SettingsComponent(
            text = "Pomodoro",
            icon = Icons.Default.Timer,
            handleExpandSettings = {
                pomodoroSettings = !pomodoroSettings
            },
            isExpanded = pomodoroSettings
        )
    }

    if (intervalSettings) {
        SettingsComponent(
            text = "Intervalo",
            icon = Icons.Default.TimerOff,
            handleExpandSettings = {
                intervalSettings = !intervalSettings
            },
            isExpanded = intervalSettings
        )
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(10.dp)
                    .width(100.dp),
                value = "",
                onValueChange = {},
                label = { Text("00:00") }
            )
        }
    } else {
        SettingsComponent(
            text = "Intervalo",
            icon = Icons.Default.TimerOff,
            handleExpandSettings = {
                intervalSettings = !intervalSettings
            },
            isExpanded = intervalSettings
        )
    }

    if (notificationSettings) {
        SettingsComponent(
            text = "Notificações",
            icon = Icons.Default.Notifications,
            handleExpandSettings = {
                notificationSettings = !notificationSettings
            },
            isExpanded = notificationSettings
        )
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Ativar")
            }
        }
    } else {
        SettingsComponent(
            text = "Notificações",
            icon = Icons.Default.Notifications,
            handleExpandSettings = {
                notificationSettings = !notificationSettings
            },
            isExpanded = notificationSettings
        )
    }
    if (informationSettings) {
        SettingsComponent(
            text = "Informações",
            icon = Icons.Default.QuestionMark,
            handleExpandSettings = {
                informationSettings = !informationSettings
            },
            isExpanded = informationSettings
        )
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Ativar")
            }
        }
    } else {
        SettingsComponent(
            text = "Informações",
            icon = Icons.Default.QuestionMark,
            handleExpandSettings = {
                informationSettings = !informationSettings
            },
            isExpanded = informationSettings
        )
    }
}

@Composable
fun SettingsComponent(
    text: String,
    icon: ImageVector,
    handleExpandSettings: () -> Unit,
    isExpanded: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            Icon(
                tint = primaryColor,
                imageVector = icon,
                contentDescription = "Open Menu"
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = text, color = primaryColor)
        }
        IconButton(
            onClick = handleExpandSettings,
        ) {

            when(isExpanded) {
                false -> {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = primaryColor
                    )
                }
                else -> {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = primaryColor
                    )
                }
            }
        }
    }
}