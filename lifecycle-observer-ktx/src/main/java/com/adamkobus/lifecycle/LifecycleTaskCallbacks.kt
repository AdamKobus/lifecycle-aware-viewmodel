package com.adamkobus.lifecycle

internal interface LifecycleTaskCallbacks {
    val onStartTask: suspend () -> Unit
    val onStopTask: () -> Unit
}
