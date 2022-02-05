package com.adamkobus.android.vm.demo.ui.logs

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle

data class LogsScreenState(
    val demoLogsList: State<List<DemoLogListItem>>,
    val isListLoading: State<Boolean>
) {
    companion object {
        fun stub(
            demoLogsList: List<DemoLogListItem> = createPreviewLogs(),
            isListLoading: Boolean = false
        ) = LogsScreenState(
            demoLogsList = mutableStateOf(demoLogsList),
            isListLoading = mutableStateOf(isListLoading)
        )

        @Suppress("MagicNumber")
        private fun createPreviewLogs(): List<DemoLogListItem> = generateSequence(0) { it + 1 }.take(20).map { index ->
            DemoLogListItem(
                id = index,
                message = "Demo Log $index",
                event = Lifecycle.Event.values()[index % Lifecycle.Event.values().size],
                createdAt = "02 Feb 2022 12:35:12"
            )
        }.toList().reversed()
    }
}
