package com.adamkobus.android.vm.demo.ui.logs

class LogsInteractions(
    val onLogClicked: (DemoLogListItem) -> Unit,
    val onBackClicked: () -> Unit
) {
    companion object {
        val STUB = LogsInteractions(
            onLogClicked = {},
            onBackClicked = {}
        )
    }
}
