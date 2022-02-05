package com.adamkobus.android.vm.demo.ui.home

import androidx.lifecycle.viewModelScope
import com.adamkobus.android.vm.LifecycleAwareViewModel
import com.adamkobus.android.vm.demo.di.DispatcherMain
import com.adamkobus.android.vm.demo.nav.FromHome
import com.adamkobus.compose.navigation.NavActionConsumer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val navActionConsumer: NavActionConsumer,
    @DispatcherMain private val mainDispatcher: CoroutineDispatcher
) : LifecycleAwareViewModel() {

    val interactions = HomeScreenInteractions(
        onLaunchLogsClicked = {
            viewModelScope.launch(mainDispatcher) {
                navActionConsumer.offer(FromHome.ToLogsList)
            }
        }
    )
}
