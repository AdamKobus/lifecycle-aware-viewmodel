package com.adamkobus.lifecycle

import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.Job

internal data class LifecycleTask(
    val startEvent: Lifecycle.Event,
    val stopEvent: Lifecycle.Event,
    val callbacks: LifecycleTaskCallbacks,
    var taskJob: Job? = null
)
