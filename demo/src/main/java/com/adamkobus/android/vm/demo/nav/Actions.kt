package com.adamkobus.android.vm.demo.nav

import com.adamkobus.compose.navigation.data.NavAction
import com.adamkobus.compose.navigation.data.PopBackStackDestination

sealed class FromHome(navAction: NavAction) : NavAction(navAction) {

    object ToLogsList : FromHome(DemoGraph.Home to DemoGraph.LogsList)
}

sealed class FromDemoDialog(navAction: NavAction) : NavAction(navAction) {
    object Dismiss : FromDemoDialog(
        NavAction(
            DemoGraph.DemoDialog,
            PopBackStackDestination,
            navigateWithController = { it.popBackStack() }
        )
    )
}

sealed class FromLogs(navAction: NavAction) : NavAction(navAction) {
    class ToDemoDialog(logId: Int) : NavAction(DemoGraph.LogsList to DemoGraph.DemoDialog arg logId)
    object Back : NavAction(DemoGraph.LogsList, PopBackStackDestination, navigateWithController = { it.popBackStack() })
}
