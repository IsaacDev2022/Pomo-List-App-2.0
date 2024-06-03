package com.pomolistapp.feature_task.presentation.home.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pomolist.feature_task.presentation.home.HomeEvent
import com.pomolist.feature_task.presentation.home.components.NavigationDrawerItems
import com.pomolist.feature_task.presentation.home.components.SwipeContent
import com.pomolist.feature_task.presentation.home.components.TaskItem
import com.pomolist.feature_task.presentation.timer.components.NotificationService
import com.pomolistapp.core.navigation.Screen
import com.pomolistapp.feature_task.domain.model.Task
import com.pomolistapp.ui.theme.primaryColor
import com.pomolistapp.ui.theme.secondaryColor
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val navigationState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Notificações
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val currentDate = LocalDate.now().format(formatter)

    val notification = NotificationService(LocalContext.current)

    if (homeViewModel.task.date == currentDate) {
        notification.showNotificationTodayTask()
    }

    Surface {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .background(
                                secondaryColor,
                                shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp)
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            Modifier.wrapContentSize(),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(100.dp)
                                    .testTag("openMenuIcon"),
                                tint = Color.White,
                                imageVector = Icons.Filled.Timer,
                                contentDescription = "Open Menu"
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                modifier = Modifier
                                    .width(180.dp),
                                text = "PomodoroList",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    NavigationDrawerItems(navController, navigationState)
                }
            },
            drawerState = navigationState,
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                modifier = Modifier
                                    .padding(start = 10.dp),
                                text = "Home",
                                color = primaryColor,
                                textAlign = TextAlign.Center
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    navigationState.open()
                                }
                            }) {
                                Icon(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(start = 10.dp),
                                    tint = primaryColor,
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Open Menu"
                                )
                            }
                        }
                    )
                },
                content = { padding ->
                    HomeContent(
                        modifier = Modifier.padding(padding),
                        onDeleteTask = { homeViewModel.onEvent(HomeEvent.DeleteTask(it)) },
                        onEditTask = {
                            navController.navigate(
                                route = Screen.RegisterScreen.passId(it)
                            )
                        },
                        onPomodoroTask = {
                            navController.navigate(
                                route = Screen.TimerTaskScreen.passIdTimer(it)
                            )
                        },
                        onTasksCompleted = homeViewModel::onEvent,
                        homeViewModel = homeViewModel
                    )
                },
                floatingActionButton = {
                    HomeFab(
                        onFabClicked = {
                            navController.navigate(route = Screen.RegisterScreen.route)
                        }
                    )
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onDeleteTask: (task: Task) -> Unit,
    onEditTask: (id: Int?) -> Unit,
    onPomodoroTask: (id: Int?) -> Unit,
    onTasksCompleted: (HomeEvent) -> Unit,
    homeViewModel: HomeViewModel
) {
    var selectedToday by remember { mutableStateOf(false) }
    var selectedCompleted by remember { mutableStateOf(false) }
    var selectedLate by remember { mutableStateOf(false) }
    var selectedUpcoming by remember { mutableStateOf(false) }
    var selectedHighPriority by remember { mutableStateOf(false) }
    var selectedLowPriority by remember { mutableStateOf(false) }

    val tasksState = homeViewModel.state.value
    val tasksToday = homeViewModel.taskListTodayTasks
    val tasksUpcoming = homeViewModel.taskListUpcoming
    val tasksLate = homeViewModel.taskListLate
    val tasksCompleted = homeViewModel.taskListCompleted
    val tasksHighPriority = homeViewModel.taskListHighPriority
    val tasksLowPriority = homeViewModel.taskListLowPriority

    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.padding(15.dp, 0.dp)) {
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selectedHighPriority,
                onClick = { selectedHighPriority = !selectedHighPriority },
                label = { Text(text = "Maior Prioridade") }
            )
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selectedLowPriority,
                onClick = { selectedLowPriority = !selectedLowPriority },
                label = { Text(text = "Menor Prioridade") }
            )
        }
        Row(modifier = Modifier.padding(15.dp, 0.dp)) {
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selectedUpcoming,
                onClick = { selectedUpcoming = !selectedUpcoming },
                label = { Text(text = "Em Breve") }
            )
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selectedLate,
                onClick = { selectedLate = !selectedLate },
                label = { Text(text = "Atrasados") }
            )
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selectedToday,
                onClick = { selectedToday = !selectedToday },
                label = { Text(text = "Para Hoje") }
            )
        }
        Row(modifier = Modifier.padding(15.dp, 0.dp)) {
            FilterChip(
                modifier = Modifier.padding(5.dp, 0.dp),
                selected = selectedCompleted,
                onClick = { selectedCompleted = !selectedCompleted },
                label = { Text(text = "Terminados") }
            )
        }

        LazyColumn {
            if (selectedToday) {
                items(tasksToday) { task ->
                    TaskItem(
                        task = task,
                        onEditTask = { onEditTask(task.id) },
                        onDeleteTask = { onDeleteTask(task) },
                        onPomodoroTask = { onPomodoroTask(task.id) },
                        onCompleted = { onTasksCompleted(HomeEvent.OnCompleted(it, true)) },
                        homeViewModel = homeViewModel
                    )
                }
            }
            else if (selectedUpcoming) {
                items(tasksUpcoming) { task ->
                    TaskItem(
                        task = task,
                        onEditTask = { onEditTask(task.id) },
                        onDeleteTask = { onDeleteTask(task) },
                        onPomodoroTask = { onPomodoroTask(task.id) },
                        onCompleted = { onTasksCompleted(HomeEvent.OnCompleted(it, true)) },
                        homeViewModel = homeViewModel
                    )
                }
            }
            else if (selectedCompleted) {
                items(tasksCompleted) { task ->
                    TaskItem(
                        task = task,
                        onEditTask = { onEditTask(task.id) },
                        onDeleteTask = { onDeleteTask(task) },
                        onPomodoroTask = { onPomodoroTask(task.id) },
                        onCompleted = { onTasksCompleted(HomeEvent.OnCompleted(it, false)) },
                        homeViewModel = homeViewModel
                    )
                }
            }
            else if (selectedLate) {
                items(tasksLate) { task ->
                    TaskItem(
                        task = task,
                        onEditTask = { onEditTask(task.id) },
                        onDeleteTask = { onDeleteTask(task) },
                        onPomodoroTask = { onPomodoroTask(task.id) },
                        onCompleted = { onTasksCompleted(HomeEvent.OnCompleted(it, true)) },
                        homeViewModel = homeViewModel
                    )
                }
            }
            else if (selectedHighPriority) {
                items(tasksHighPriority) { task ->
                    TaskItem(
                        task = task,
                        onEditTask = { onEditTask(task.id) },
                        onDeleteTask = { onDeleteTask(task) },
                        onPomodoroTask = { onPomodoroTask(task.id) },
                        onCompleted = { onTasksCompleted(HomeEvent.OnCompleted(it, true)) },
                        homeViewModel = homeViewModel
                    )
                }
            }
            else if (selectedLowPriority) {
                items(tasksLowPriority) { task ->
                    TaskItem(
                        task = task,
                        onEditTask = { onEditTask(task.id) },
                        onDeleteTask = { onDeleteTask(task) },
                        onPomodoroTask = { onPomodoroTask(task.id) },
                        onCompleted = { onTasksCompleted(HomeEvent.OnCompleted(it, true)) },
                        homeViewModel = homeViewModel
                    )
                }
            }
            else {
                itemsIndexed(
                    items = tasksState.tasks,
                    key = {_, task ->
                        task.id
                    }
                ) { index, task ->
                    SwipeContent(
                        item = task,
                        onDeleteSwipeTask = { onDeleteTask(task) },
                    ) {
                        TaskItem(
                            task = task,
                            onEditTask = { onEditTask(task.id) },
                            onDeleteTask = { onDeleteTask(task) },
                            onPomodoroTask = { onPomodoroTask(task.id) },
                            onCompleted = { onTasksCompleted(HomeEvent.OnCompleted(it, true)) },
                            homeViewModel = homeViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeFab(onFabClicked: () -> Unit) {
    FloatingActionButton(
        onClick = onFabClicked,
        containerColor = secondaryColor,
        modifier = Modifier.testTag("btnAdd")
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Adicionar",
            tint = Color.White
        )
    }
}