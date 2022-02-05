package com.adamkobus.android.vm.demo.ui.home

data class HomeScreenInteractions(
    val onLaunchLogsClicked: () -> Unit
) {
    companion object {
        val STUB = HomeScreenInteractions(
            onLaunchLogsClicked = {}
        )
    }
}
