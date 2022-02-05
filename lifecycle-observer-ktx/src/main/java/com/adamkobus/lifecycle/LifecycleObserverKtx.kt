package com.adamkobus.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Use this class to register tasks that should be triggered on various [Lifecycle.Event]s.
 * [LifecycleObserverKtx] doesn't register with any [Lifecycle] by itself.
 *
 * For this class to start executing any tasks, you must register it with [Lifecycle.addObserver] first
 * or delegate events from your own [LifecycleEventObserver] via [onStateChanged] method.
 *
 * @param launchScope will be used to execute all suspend tasks
 * @param launcherDispatcher will be used to execute all suspend tasks
 */
open class LifecycleObserverKtx(
    private val launchScope: CoroutineScope,
    private val launcherDispatcher: CoroutineDispatcher = Dispatchers.Main
) : LifecycleEventObserver {

    private val tasks = mutableListOf<LifecycleTask>()
    private val lastEvent = MutableStateFlow(Lifecycle.Event.ON_DESTROY)

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        lastEvent.value = event
        tasks.filter { it.startEvent == event }.forEach { task ->
            startTask(task)
        }
        tasks.filter { it.stopEvent == event }.forEach { task ->
            stopTask(task)
        }
    }

    private fun startTask(task: LifecycleTask) {
        task.taskJob?.cancel()
        task.taskJob = runOnMain {
            task.callbacks.onStartTask()
        }
    }

    private fun stopTask(task: LifecycleTask) {
        task.taskJob?.cancel()
        task.taskJob = null
        task.callbacks.onStopTask()
    }

    fun runOnCreateDestroy(init: LifecycleOnCreateTask.Builder.() -> Unit) {
        val callbacks = LifecycleOnCreateTask.Builder().apply(init).build()
        val task = LifecycleTask(
            startEvent = Lifecycle.Event.ON_CREATE,
            stopEvent = Lifecycle.Event.ON_DESTROY,
            callbacks = callbacks
        )
        addTask(task)
    }

    fun runOnStartStop(init: LifecycleOnStartTask.Builder.() -> Unit) {
        val callbacks = LifecycleOnStartTask.Builder().apply(init).build()
        val task = LifecycleTask(
            startEvent = Lifecycle.Event.ON_START,
            stopEvent = Lifecycle.Event.ON_STOP,
            callbacks = callbacks
        )
        addTask(task)
    }

    fun runOnResumePause(init: LifecycleOnResumeTask.Builder.() -> Unit) {
        val callbacks = LifecycleOnResumeTask.Builder().apply(init).build()
        val task = LifecycleTask(
            startEvent = Lifecycle.Event.ON_RESUME,
            stopEvent = Lifecycle.Event.ON_PAUSE,
            callbacks = callbacks
        )
        addTask(task)
    }

    fun runOnCreate(task: suspend () -> Unit) {
        runOnCreateDestroy {
            onCreate = task
        }
    }

    fun runOnDestroy(task: () -> Unit) {
        runOnCreateDestroy {
            onDestroy = task
        }
    }

    fun runOnStart(task: suspend () -> Unit) {
        runOnStartStop {
            onStart = task
        }
    }

    fun runOnStop(task: () -> Unit) {
        runOnStartStop {
            onStop = task
        }
    }

    fun runOnResume(task: suspend () -> Unit) {
        runOnResumePause {
            onResume = task
        }
    }

    fun runOnPause(task: () -> Unit) {
        runOnResumePause {
            onPause = task
        }
    }

    private fun addTask(task: LifecycleTask) {
        tasks.add(task)
        if (task.startEvent.canBeStartedWithCurrentState(lastEvent.value)) {
            startTask(task)
        }
    }

    /**
     * Executes the task instantly using [launchScope] and [launcherDispatcher]
     */
    fun runOnMain(task: suspend () -> Unit): Job =
        launchScope.launch(launcherDispatcher) {
            task()
        }

    suspend fun collectLifecycleEvents(task: suspend (Lifecycle.Event) -> Unit) {
        lastEvent.collect {
            task(it)
        }
    }
}

private const val NOT_APPLICABLE = 100
private const val DESTROYED = 0
private const val CREATED = 1
private const val STARTED = 2
private const val RESUMED = 3

private val Lifecycle.Event.requiredMinState: Int
    get() = when (this) {
        Lifecycle.Event.ON_ANY,
        Lifecycle.Event.ON_DESTROY,
        Lifecycle.Event.ON_STOP,
        Lifecycle.Event.ON_PAUSE -> NOT_APPLICABLE
        Lifecycle.Event.ON_CREATE -> CREATED
        Lifecycle.Event.ON_START -> STARTED
        Lifecycle.Event.ON_RESUME -> RESUMED
    }

private val Lifecycle.Event.nextState: Int
    get() = when (this) {
        Lifecycle.Event.ON_DESTROY,
        Lifecycle.Event.ON_ANY -> DESTROYED
        Lifecycle.Event.ON_CREATE,
        Lifecycle.Event.ON_STOP -> CREATED
        Lifecycle.Event.ON_START,
        Lifecycle.Event.ON_PAUSE -> STARTED
        Lifecycle.Event.ON_RESUME -> RESUMED
    }

private fun Lifecycle.Event.canBeStartedWithCurrentState(currentEvent: Lifecycle.Event): Boolean {
    val currentState = currentEvent.nextState
    return currentState >= this.requiredMinState
}
