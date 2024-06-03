package com.pomolistapp.feature_task.presentation.timer.screens

import android.content.Context
import android.content.Intent
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pomolist.feature_task.presentation.timer.components.NotificationService
import com.pomolistapp.core.navigation.Screen
import com.pomolistapp.ui.theme.primaryColor
import com.pomolistapp.ui.theme.secondaryColor
import kotlinx.coroutines.delay

@Composable
fun TimerTaskScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    timerViewModel: TimerViewModel = hiltViewModel()
) {
    val timerTextScale = remember { Animatable(1f) }

    val uiState by timerViewModel.uiState.collectAsState()

    when (uiState) {
        is TimerViewModel.UiState.Success -> {
            // Notificações
            val timerNotification = NotificationService(LocalContext.current)

            // Pomodoro
            val workTime = (uiState as TimerViewModel.UiState.Success).workTime * 60
            val breakTime = (uiState as TimerViewModel.UiState.Success).breakTime * 60
            val pomoCount = (uiState as TimerViewModel.UiState.Success).pomodoroCount
            var timeLeft by remember { mutableStateOf(workTime) }
            var isRunning by remember { mutableStateOf(false) }
            var isBreak by remember { mutableStateOf(false) }
            var pomodoroCount by remember { mutableStateOf(pomoCount) }
            val scope = rememberCoroutineScope()

            LaunchedEffect(isRunning, timeLeft) {
                if (isRunning && timeLeft > 0) {
                    delay(1000L)
                    timeLeft--
                } else if (isRunning && timeLeft == 0) {
                    if (!isBreak) {
                        isBreak = true
                        timeLeft = breakTime
                    } else {
                        isBreak = false
                        timeLeft = workTime
                        pomodoroCount--
                    }
                    if (pomodoroCount == 0) {
                        isRunning = false
                    }
                }
            }

            val minutes = timeLeft / 60
            val seconds = timeLeft % 60

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(secondaryColor)
            ) {
                Row() {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .padding(20.dp)
                            .height(40.dp)
                            .width(40.dp)
                            .testTag("homeIcon")
                            .clickable { navController.navigate(Screen.HomeScreen.route) }
                    )
                    Spacer(modifier = Modifier.width(220.dp))
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .padding(20.dp)
                            .height(40.dp)
                            .width(40.dp)
                            .clickable { navController.navigate(Screen.HomeScreen.route) }
                    )
                }

                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = modifier.height(100.dp))
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(250.dp)) {
                        DrawArc(timeLeft, if (isBreak) breakTime else workTime)
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = String.format("%02d:%02d", minutes, seconds),
                                fontSize = 44.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = modifier.height(20.dp))
                            Text(
                                text = "Pomodoro: ${pomodoroCount}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .scale(timerTextScale.value)
                            )
                        }
                    }

                    Spacer(modifier = modifier.height(50.dp))

                    Row() {
                        Button(
                            modifier = modifier
                                .width(80.dp)
                                .height(60.dp),
                            onClick = {
                                isRunning = false
                                isBreak = false
                                timeLeft = workTime
                                pomodoroCount = pomoCount
                            },
                            colors = ButtonDefaults.buttonColors(primaryColor)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp),
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = modifier.width(20.dp))

                        Button(
                            modifier = modifier
                                .width(100.dp)
                                .height(60.dp),
                            onClick = {
                                isRunning = !isRunning
                            },
                            colors = ButtonDefaults.buttonColors(primaryColor)
                        ) {
                            if (isRunning && timeLeft >= 0L) {
                                Icon(
                                    imageVector = Icons.Filled.Stop,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .height(40.dp)
                                        .width(40.dp)
                                )
                            }
                            if (!isRunning && timeLeft >= 0L) {
                                Icon(
                                    imageVector = Icons.Filled.PlayCircle,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .height(40.dp)
                                        .width(40.dp)
                                )
                            }
                        }

                        Spacer(modifier = modifier.width(20.dp))

                        Button(
                            modifier = modifier
                                .width(80.dp)
                                .height(60.dp),
                            onClick = {
                            },
                            colors = ButtonDefaults.buttonColors(primaryColor)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp),
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = modifier.height(40.dp))
                }
            }
        }

        else -> {}
    }
}