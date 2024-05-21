@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.pomolist.feature_task.presentation.home.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pomolistapp.R
import com.pomolistapp.feature_task.domain.model.Task
import com.pomolistapp.feature_task.presentation.home.screens.HomeViewModel
import com.pomolistapp.ui.theme.doneTaskColor
import com.pomolistapp.ui.theme.primaryColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onEditTask: () -> Unit,
    onDeleteTask: () -> Unit,
    onPomodoroTask: () -> Unit,
    onCompleted: (Int) -> Unit,
    homeViewModel: HomeViewModel
) {
    var colorPriority = Color(android.graphics.Color.parseColor("#8DF181"))

    if (task.priority.equals(0)) {
        colorPriority = Color(android.graphics.Color.parseColor("#8DF181"))
    }
    if (task.priority.equals(1)) {
        colorPriority = Color(android.graphics.Color.parseColor("#ECE48B"))
    }
    if (task.priority.equals(2)) {
        colorPriority = Color(android.graphics.Color.parseColor("#f5ad89"))
    }
    if (task.priority.equals(3)) {
        colorPriority = Color(android.graphics.Color.parseColor("#f07171"))
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = { onEditTask }
            ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onClick = onEditTask,
        colors = if (task.completed) CardDefaults.cardColors(containerColor = doneTaskColor)
        else CardDefaults.cardColors(
            containerColor = colorPriority
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                IconButton(onClick = onPomodoroTask) {
                    Icon(
                        modifier = Modifier.size(size = 40.dp),
                        imageVector = Icons.Outlined.PlayCircle,
                        contentDescription = null,
                        tint = primaryColor
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "${task.name}",
                        color = primaryColor,
                        style = if (task.completed) MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.LineThrough)
                        else MaterialTheme.typography.titleMedium,
                        modifier = Modifier.testTag("txtName")
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${task.description}",
                        color = primaryColor,
                        style = if (task.completed) MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough)
                        else MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .widthIn(0.dp, 150.dp)
                            .testTag("txtDescription")
                    )
                }
            }
            Row {
                Column {
                    Row {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${task.date}",
                            color = primaryColor,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${task.time}",
                        color = primaryColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                IconButton(
                    onClick = {
                        onCompleted(task.id)
                    }
                ) {
                    if(task.completed){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check_box),
                            tint = primaryColor,
                            contentDescription = "Done",
                        )
                    }
                    else{
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check_box_outline),
                            tint = primaryColor,
                            contentDescription = "Done",
                        )
                    }
                }
            }
        }
    }
}