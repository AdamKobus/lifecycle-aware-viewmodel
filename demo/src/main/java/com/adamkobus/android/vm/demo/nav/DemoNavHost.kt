package com.adamkobus.android.vm.demo.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adamkobus.compose.navigation.ComposeNavHost
import com.adamkobus.compose.navigation.NavigationId
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DemoNavHost(
    modifier: Modifier = Modifier
) {
    val navHostController = rememberAnimatedNavController()
    ComposeNavHost(
        startGraph = DemoGraph,
        controller = navHostController,
        navigationId = NavigationId.DEFAULT,
        modifier = Modifier.fillMaxSize()
    ) {
        demoGraph()
    }
}
