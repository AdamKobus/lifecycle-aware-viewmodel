package com.adamkobus.android.vm

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamkobus.lifecycle.LifecycleObserverKtx
import com.adamkobus.lifecycle.LifecycleOnCreateTask
import com.adamkobus.lifecycle.LifecycleOnResumeTask
import com.adamkobus.lifecycle.LifecycleOnStartTask
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Delegates all of the events received via [onStateChanged] to the embedded [LifecycleObserverKtx]
 *
 * @param lifecycleScope Will be provided to [LifecycleObserverKtx]
 * @param lifecycleDispatcher Will be provided to [LifecycleObserverKtx]
 *
 * @see [LifecycleObserverKtx]
 */
open class LifecycleAwareViewModel(
    val lifecycleScope: CoroutineScope? = null,
    lifecycleDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel(), LifecycleEventObserver {

    private val lifecycleObserver = LifecycleObserverKtx(lifecycleScope ?: viewModelScope, lifecycleDispatcher)

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) = lifecycleObserver.onStateChanged(source, event)

    fun runOnCreateDestroy(init: LifecycleOnCreateTask.Builder.() -> Unit) = lifecycleObserver.runOnCreateDestroy(init)

    fun runOnStartStop(init: LifecycleOnStartTask.Builder.() -> Unit) = lifecycleObserver.runOnStartStop(init)

    fun runOnResumePause(init: LifecycleOnResumeTask.Builder.() -> Unit) = lifecycleObserver.runOnResumePause(init)

    fun runOnCreate(task: suspend () -> Unit) = lifecycleObserver.runOnCreate(task)

    fun runOnDestroy(task: () -> Unit) = lifecycleObserver.runOnDestroy(task)

    fun runOnStart(task: suspend () -> Unit) = lifecycleObserver.runOnStart(task)

    fun runOnStop(task: () -> Unit) = lifecycleObserver.runOnStop(task)

    fun runOnResume(task: suspend () -> Unit) = lifecycleObserver.runOnResume(task)

    fun runOnPause(task: () -> Unit) = lifecycleObserver.runOnPause(task)

    fun runOnMain(task: suspend () -> Unit): Job = lifecycleObserver.runOnMain(task)

    suspend fun collectLifecycleEvents(task: suspend (Lifecycle.Event) -> Unit) = lifecycleObserver.collectLifecycleEvents(task)
}

/**
 * Will collect provided [param] when Lifecycle enters started state
 *
 * @see [ViewParam.observe]
 */
fun <T> LifecycleAwareViewModel.collectParam(param: ViewParam<T>, task: suspend (T) -> Unit) {
    runOnStart {
        param.collect {
            task(it)
        }
    }
}

/**
 * Will collect provided [param] when Lifecycle enters started state
 *
 * @see [ViewParam.collect]
 */
fun <T> LifecycleObserverKtx.collectParam(param: ViewParam<T>, task: suspend (T) -> Unit) {
    runOnStart {
        param.collect {
            task(it)
        }
    }
}
