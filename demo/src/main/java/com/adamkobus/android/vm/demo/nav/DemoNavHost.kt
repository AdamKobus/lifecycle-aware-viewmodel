package com.adamkobus.android.vm.demo.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adamkobus.compose.navigation.ui.NavComposable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DemoNavHost(
    modifier: Modifier = Modifier
) {
    val navHostController = rememberAnimatedNavController()
    NavComposable(navController = navHostController)
    AnimatedNavHost(
        navController = navHostController,
        startDestination = DemoGraph.name,
        modifier = modifier
    ) {
        demoGraph()
    }
}
