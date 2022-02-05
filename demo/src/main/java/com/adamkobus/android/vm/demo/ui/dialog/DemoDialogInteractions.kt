package com.adamkobus.android.vm.demo.ui.dialog

data class DemoDialogInteractions(
    val onDismissClicked: () -> Unit
) {
    companion object {
        val STUB = DemoDialogInteractions(
            onDismissClicked = {}
        )
    }
}
