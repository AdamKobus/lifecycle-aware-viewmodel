package com.adamkobus.android.vm.demo.ui.logs

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import com.adamkobus.android.vm.LifecycleAwareViewModel
import com.adamkobus.android.vm.demo.model.datetime.DemoDateFormatter
import com.adamkobus.android.vm.demo.model.logs.DemoLogsStore
import com.adamkobus.android.vm.demo.nav.FromLogs
import com.adamkobus.compose.navigation.NavigationConsumer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogsScreenVM @Inject constructor(
    private val logsStore: DemoLogsStore,
    private val navigationConsumer: NavigationConsumer,
    private val dateFormatter: DemoDateFormatter
) : LifecycleAwareViewModel() {

    private val demoLogsList = mutableStateOf<List<DemoLogListItem>>(emptyList())
    private val isListLoading = mutableStateOf(true)

    val screenState = LogsScreenState(
        isListLoading = isListLoading,
        demoLogsList = demoLogsList
    )

    val interactions = LogsInteractions(
        onLogClicked = { item ->
            runOnMain {
                navigationConsumer.offer(FromLogs.ToDemoDialog(item.id))
            }
        },
        onBackClicked = {
            runOnMain {
                navigationConsumer.offer(FromLogs.Back)
            }
        }
    )

    init {
        runOnCreateDestroy {
            onCreate = {
                logsStore.addLog(Lifecycle.Event.ON_CREATE, "runOnCreateDestroy.onCreate")
            }
            onDestroy = {
                logsStore.addLog(Lifecycle.Event.ON_DESTROY, "runOnCreateDestroy.onDestroy")
            }
        }

        runOnStartStop {
            onStart = {
                logsStore.addLog(Lifecycle.Event.ON_START, "runOnStartStop.onStart")
            }
            onStop = {
                logsStore.addLog(Lifecycle.Event.ON_STOP, "runOnStartStop.onStop")
            }
        }

        runOnResumePause {
            onResume = {
                logsStore.addLog(Lifecycle.Event.ON_RESUME, "runOnResumePause.onResume")
            }
            onPause = {
                logsStore.addLog(Lifecycle.Event.ON_PAUSE, "runOnResumePause.onPause")
            }
        }

        runOnCreate {
            logsStore.addLog(Lifecycle.Event.ON_CREATE, "runOnCreate")
        }

        runOnDestroy {
            logsStore.addLog(Lifecycle.Event.ON_DESTROY, "runOnDestroy")
        }

        runOnStart {
            logsStore.addLog(Lifecycle.Event.ON_START, "runOnStart")
        }

        runOnStop {
            logsStore.addLog(Lifecycle.Event.ON_STOP, "runOnStop")
        }

        runOnResume {
            logsStore.addLog(Lifecycle.Event.ON_RESUME, "runOnResume")
        }

        runOnPause {
            logsStore.addLog(Lifecycle.Event.ON_PAUSE, "runOnPause")
        }

        runOnStart {
            logsStore.logs.collect { logs ->
                demoLogsList.value = logs.sortedByDescending { "${it.createdAtUtc}_{${it.id}" }.map {
                    it.toDemoLogListItem(dateFormatter)
                }
                isListLoading.value = false
            }
        }

        runOnCreate {
            collectLifecycleEvents {
                Log.d("LifecycleEvent", "Event collected: $it")
            }
        }
    }
}
