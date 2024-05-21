package com.pomolistapp.feature_task.presentation.timer.screens

import android.text.format.DateUtils
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pomolistapp.core.navigation.Screen
import com.pomolistapp.ui.theme.primaryColor
import com.pomolistapp.ui.theme.secondaryColor
import com.pomolistapp.ui.theme.tertiaryColor
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun TimerScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val timerTextScale = remember { Animatable(1f) }

    val workTime = 2 * 60 // 25 minutes in seconds
    val breakTime = 1 * 60 // 5 minutes in seconds
    var timeLeft by remember { mutableStateOf(workTime) }
    var isRunning by remember { mutableStateOf(false) }
    var isBreak by remember { mutableStateOf(false) }
    var pomodoroCount by remember { mutableStateOf(4) } // Total Pomodoros to complete
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

    // Notificações
//    val timerNotification = NotificationService(LocalContext.current)

    // Service
//    var context: Context = LocalContext.current
//    var foregroundService: Intent? = null
//
//    foregroundService = Intent(context, TimerService::class.java)

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
                        pomodoroCount = 4
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
//                        timerNotification.showNotification()
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

@Composable
fun DrawArc(timeLeft: Int, totalTime: Int) {
    Canvas(modifier = Modifier.size(260.dp)) {
        val sweepAngle = (timeLeft / totalTime.toFloat()) * 360
        drawArc(
            color = Color.White,
            startAngle = 360f,
            sweepAngle = 360f,
            useCenter = false,
            size = Size(size.width.toFloat(), size.height.toFloat()),
            style = Stroke(10.dp.toPx(), cap = StrokeCap.Round)
        )
        drawArc(
            color = if (totalTime == 1 * 60) secondaryColor else primaryColor,
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            size = Size(size.width.toFloat(), size.height.toFloat()),
            style = Stroke(10.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

fun Long.formatDuration(): String = DateUtils.formatElapsedTime(this)