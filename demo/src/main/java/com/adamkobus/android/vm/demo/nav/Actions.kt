package com.adamkobus.android.vm.demo.nav

import com.adamkobus.compose.navigation.action.NavAction
import com.adamkobus.compose.navigation.action.NavActionWrapper

sealed class FromHome(navAction: NavAction) : NavActionWrapper(navAction) {

    object ToLogsList : FromHome(DemoGraph.Home goTo DemoGraph.LogsList)
}

sealed class FromDemoDialog(navAction: NavAction) : NavActionWrapper(navAction) {
    object Dismiss : FromDemoDialog(DemoGraph.DemoDialog.pop())
}

sealed class FromLogs(navAction: NavAction) : NavActionWrapper(navAction) {
    class ToDemoDialog(logId: Int) : FromLogs(DemoGraph.LogsList goTo DemoGraph.DemoDialog arg logId)
    object Back : FromLogs(DemoGraph.LogsList.pop())
}
