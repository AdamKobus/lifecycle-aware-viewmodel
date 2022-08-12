package com.adamkobus.android.vm.demo.ui.home

import androidx.lifecycle.viewModelScope
import com.adamkobus.android.vm.LifecycleAwareViewModel
import com.adamkobus.android.vm.demo.nav.FromHome
import com.adamkobus.compose.navigation.NavigationConsumer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val navigationConsumer: NavigationConsumer,
) : LifecycleAwareViewModel() {

    val interactions = HomeScreenInteractions(
        onLaunchLogsClicked = {
            viewModelScope.launch() {
                navigationConsumer.offer(FromHome.ToLogsList)
            }
        }
    )
}
