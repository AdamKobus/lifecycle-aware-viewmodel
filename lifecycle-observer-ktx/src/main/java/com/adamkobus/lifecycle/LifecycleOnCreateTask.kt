package com.adamkobus.lifecycle

class LifecycleOnCreateTask private constructor(
    val onCreate: suspend () -> Unit,
    val onDestroy: () -> Unit,
) : LifecycleTaskCallbacks {
    override val onStartTask = onCreate
    override val onStopTask = onDestroy

    class Builder {
        var onCreate: suspend () -> Unit = {}
        var onDestroy: () -> Unit = {}

        fun build() = LifecycleOnCreateTask(onCreate = onCreate, onDestroy = onDestroy)
    }
}
