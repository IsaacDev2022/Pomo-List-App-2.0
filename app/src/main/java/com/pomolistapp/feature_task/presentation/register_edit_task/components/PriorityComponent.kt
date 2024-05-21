package com.pomolist.feature_task.presentation.register_edit_task.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pomolistapp.ui.theme.PomoListApp20Theme
import com.pomolistapp.ui.theme.priorityColors

@Composable
fun PriorityComponent(
    defaultSortTask: Priority = Priority.LOW,
    onSelect: (Priority) -> Unit
) {
    var selectedOption by remember {
        mutableStateOf(defaultSortTask)
    }

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Priority.entries.forEach {
            PriorityItem(
                text = it.displayText,
                backgroundColor = priorityColors[it.ordinal],
                isSelected = selectedOption == it
            ) {
                onSelect(it)
                selectedOption = it
            }
        }
    }
}

@Composable
fun PriorityItem(
    text: String,
    backgroundColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isSelected) {

            val animValue = remember { Animatable(initialValue = 0f) }

            LaunchedEffect(Unit) {
                animValue.animateTo(1f, tween(300))
            }

            Box(
                modifier = Modifier
                    .width(80.dp * animValue.value)
                    .height(4.dp)
                    .background(backgroundColor, RoundedCornerShape(8.dp))
            )
        }
        Box(
            modifier = Modifier
                .width(82.dp)
                .height(75.dp)
                .background(backgroundColor, RoundedCornerShape(4.dp))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp, 24.dp)
            )
        }
        if (isSelected) {

            val animValue = remember { Animatable(initialValue = 0f) }

            LaunchedEffect(Unit) {
                animValue.animateTo(1f, tween(300))
            }

            Box(
                modifier = Modifier
                    .width(80.dp * animValue.value)
                    .height(4.dp)
                    .background(backgroundColor, RoundedCornerShape(8.dp))
            )
        }
    }
}

@Preview()
@Composable
fun PriorityComponentPreview() {
    PomoListApp20Theme{
        PriorityComponent(Priority.MEDIUM, {})
    }
}