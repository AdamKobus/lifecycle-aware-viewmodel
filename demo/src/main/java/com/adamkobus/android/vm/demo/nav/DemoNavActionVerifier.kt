package com.adamkobus.android.vm.demo.nav

import com.adamkobus.compose.navigation.NavActionVerifier
import com.adamkobus.compose.navigation.data.INavDestination
import com.adamkobus.compose.navigation.data.NavAction
import javax.inject.Inject

class DemoNavActionVerifier @Inject constructor() : NavActionVerifier {

    override fun isNavActionAllowed(currentDestination: INavDestination, action: NavAction): Boolean {
        return action.fromDestination == currentDestination
    }
}
