package com.adamkobus.lifecycle

class LifecycleOnStartTask private constructor(
    val onStart: suspend () -> Unit,
    val onStop: () -> Unit,
) : LifecycleTaskCallbacks {
    override val onStartTask = onStart
    override val onStopTask = onStop

    class Builder {
        var onStart: suspend () -> Unit = {}
        var onStop: () -> Unit = {}

        fun build() = LifecycleOnStartTask(onStart = onStart, onStop = onStop)
    }
}
