package com.adamkobus.android.vm.demo.nav

import android.util.Log
import com.adamkobus.compose.navigation.BuildConfig
import com.adamkobus.compose.navigation.ComposeNavigation
import com.adamkobus.compose.navigation.NavActionVerifier
import javax.inject.Inject

class ComposeNavigationConfigurator @Inject constructor(
    private val navVerifiers: Set<@JvmSuppressWildcards NavActionVerifier>,
) {

    fun configure() {
        val logLevel = if (BuildConfig.DEBUG) {
            Log.VERBOSE
        } else {
            Log.WARN
        }
        ComposeNavigation
            .addNavActionVerifiers(navVerifiers)
            .setLogLevel(logLevel)
    }
}
