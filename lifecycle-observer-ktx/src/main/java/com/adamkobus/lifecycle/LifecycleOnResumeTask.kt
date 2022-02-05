package com.adamkobus.lifecycle

class LifecycleOnResumeTask private constructor(
    val onResume: suspend () -> Unit,
    val onPause: () -> Unit,
) : LifecycleTaskCallbacks {
    override val onStartTask = onResume
    override val onStopTask = onPause

    class Builder {
        var onResume: suspend () -> Unit = {}
        var onPause: () -> Unit = {}

        fun build() = LifecycleOnResumeTask(onResume = onResume, onPause = onPause)
    }
}
