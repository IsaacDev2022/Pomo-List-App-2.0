package com.pomolist.feature_task.presentation.home.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.pomolistapp.ui.theme.deleteColor
import com.pomolistapp.ui.theme.primaryColor
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeContent(
    item: T,
    onDeleteSwipeTask: (T) -> Unit,
    icon: ImageVector = Icons.Default.Delete,
    iconTint: Color = White,
    animationDuration: Int = 300,
    content: @Composable (T) -> Unit,
) {
    var isActionDone by remember { mutableStateOf(false) }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            if (state == SwipeToDismissBoxValue.EndToStart) {
                isActionDone = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(isActionDone) {
        if (isActionDone) {
            delay(animationDuration.toLong())
            onDeleteSwipeTask(item)
        }
    }

    AnimatedVisibility(
        visible = !isActionDone,
        exit = fadeOut(tween(animationDuration))
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                val backgroundColor by animateColorAsState(
                targetValue = when (state.currentValue) {
                    SwipeToDismissBoxValue.StartToEnd -> primaryColor
                    SwipeToDismissBoxValue.EndToStart -> deleteColor
                    SwipeToDismissBoxValue.Settled -> deleteColor
                    },
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .background(backgroundColor, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(20.dp),
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint
                    )
                }
            },
            content = { content(item) },
        )
    }
}