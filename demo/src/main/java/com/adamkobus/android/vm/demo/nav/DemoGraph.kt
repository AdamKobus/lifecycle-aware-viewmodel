package com.adamkobus.android.vm.demo.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.adamkobus.android.vm.demo.ui.dialog.DemoDialog
import com.adamkobus.android.vm.demo.ui.home.HomeScreen
import com.adamkobus.android.vm.demo.ui.logs.LogsScreen
import com.adamkobus.compose.navigation.data.NavGraph
import com.adamkobus.compose.navigation.ext.composableDestination
import com.adamkobus.compose.navigation.ext.composableDialog
import com.adamkobus.compose.navigation.ext.composableNavigation
import com.adamkobus.compose.navigation.ext.getInt

object DemoGraph : NavGraph {
    override val name: String = "demoGraph"

    const val PARAM_LOG_ID = "logId"

    val Home = navDestination("home")
    val LogsList = navDestination("logs")

    val DemoDialog = navDestination("demoDialog") {
        param(PARAM_LOG_ID)
    }
}

fun NavBackStackEntry.logId(): Int = getInt(DemoGraph.PARAM_LOG_ID)

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.demoGraph() {
    composableNavigation(
        graph = DemoGraph,
        startDestination = DemoGraph.Home
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
