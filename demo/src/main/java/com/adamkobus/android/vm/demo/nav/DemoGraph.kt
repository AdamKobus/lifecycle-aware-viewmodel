package com.adamkobus.android.vm.demo.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.adamkobus.android.vm.demo.ui.dialog.DemoDialog
import com.adamkobus.android.vm.demo.ui.home.HomeScreen
import com.adamkobus.android.vm.demo.ui.logs.LogsScreen
import com.adamkobus.compose.navigation.destination.NavGraph
import com.adamkobus.compose.navigation.destination.NavStackEntry
import com.adamkobus.compose.navigation.ext.composableDestination
import com.adamkobus.compose.navigation.ext.composableDialog
import com.adamkobus.compose.navigation.ext.composableNavigation

object DemoGraph : NavGraph("demoGraph") {

    const val PARAM_LOG_ID = "logId"

    val Home = screenDestination("home")
    val LogsList = screenDestination("logs")

    val DemoDialog = dialogDestination("demoDialog") {
        param(PARAM_LOG_ID)
    }

    override fun startDestination() = Home
}

fun NavStackEntry.logId(): Int = getInt(DemoGraph.PARAM_LOG_ID)

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.demoGraph() {
    composableNavigation(
        graph = DemoGraph
    ) {
        composableDestination(DemoGraph.Home) {
            HomeScreen()
        }

        composableDestination(DemoGraph.LogsList) {
            LogsScreen()
        }

        composableDialog(DemoGraph.DemoDialog) { backStackEntry ->
            DemoDialog(backStackEntry.logId())
        }
    }
}
