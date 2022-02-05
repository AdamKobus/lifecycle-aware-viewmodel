package com.adamkobus.android.vm.demo.ui.dialog

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

data class DemoDialogState(
    val logMessage: State<String?>
) {
    companion object {
        fun stub() = DemoDialogState(
            logMessage = mutableStateOf("Test log message")
        )
    }
}
