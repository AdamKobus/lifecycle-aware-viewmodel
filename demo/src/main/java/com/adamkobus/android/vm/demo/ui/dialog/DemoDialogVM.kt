package com.adamkobus.android.vm.demo.ui.dialog

import androidx.compose.runtime.mutableStateOf
import com.adamkobus.android.vm.LifecycleAwareViewModel
import com.adamkobus.android.vm.ViewParam
import com.adamkobus.android.vm.collectParam
import com.adamkobus.android.vm.demo.model.logs.DemoLogsStore
import com.adamkobus.android.vm.demo.nav.FromDemoDialog
import com.adamkobus.compose.navigation.NavActionConsumer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DemoDialogVM @Inject constructor(
    private val navActionConsumer: NavActionConsumer,
    private val logsStore: DemoLogsStore
) : LifecycleAwareViewModel() {

    val logIdParam = ViewParam<Int>()

    private val logMessage = mutableStateOf<String?>(null)

    val screenState = DemoDialogState(logMessage = logMessage)

    val interactions = DemoDialogInteractions(
        onDismissClicked = {
            runOnMain {
                navActionConsumer.offer(FromDemoDialog.Dismiss)
            }
        }
    )

    init {
        collectParam(logIdParam) {
            logMessage.value = logsStore.findLogById(it)?.message
        }
    }
}
