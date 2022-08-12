package com.adamkobus.android.vm.demo.app

import android.app.Application
import com.adamkobus.android.vm.demo.nav.ComposeNavigationConfigurator
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class DemoApplication : Application() {

    @Inject
    lateinit var composeNavigationConfigStep: ComposeNavigationConfigurator

    override fun onCreate() {
        super.onCreate()
        composeNavigationConfigStep.configure()
    }
}
