@file:Suppress("MagicNumber")

package com.adamkobus.android.vm.demo.ui.logs

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import com.adamkobus.android.vm.demo.model.datetime.DemoDateFormatter
import com.adamkobus.android.vm.demo.model.logs.DemoLog

data class DemoLogListItem(
    val id: Int,
    val event: Lifecycle.Event,
    val message: String,
    val createdAt: String
)

fun DemoLog.toDemoLogListItem(dateFormatter: DemoDateFormatter) = DemoLogListItem(
    id = id,
    event = event,
    message = message,
    createdAt = dateFormatter.formatUnixEpochTimestamp(createdAtUtc)
)

private val CreateColor = Color(0xFF0c7811)
private val StartColor = CreateColor.copy(alpha = 0.8f)
private val ResumeColor = CreateColor.copy(alpha = 0.6f)

private val DestroyColor = Color(0xFFd64c15)
private val StopColor = DestroyColor.copy(alpha = 0.8f)
private val PauseColor = DestroyColor.copy(alpha = 0.6f)

@Composable
fun Lifecycle.Event.color() = when (this) {
    Lifecycle.Event.ON_CREATE -> CreateColor
    Lifecycle.Event.ON_START -> StartColor
    Lifecycle.Event.ON_RESUME -> ResumeColor
    Lifecycle.Event.ON_PAUSE -> PauseColor
    Lifecycle.Event.ON_STOP -> StopColor
    Lifecycle.Event.ON_DESTROY -> DestroyColor
    Lifecycle.Event.ON_ANY -> MaterialTheme.colors.onBackground
}
