package com.adamkobus.android.vm.demo.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.adamkobus.android.vm.demo.R
import com.adamkobus.android.vm.demo.ui.Paddings
import com.adamkobus.android.vm.demo.ui.common.BackgroundComponent
import com.adamkobus.android.vm.demo.ui.common.LifecycleAwareComponent
import com.adamkobus.android.vm.demo.ui.common.PreviewComponent

@Composable
fun HomeScreen() {
    val vm: HomeScreenVM = hiltViewModel()
    LifecycleAwareComponent(observer = vm)
    BackgroundComponent {
        HomeScreenContent(interactions = vm.interactions)
    }
}

@Composable
private fun HomeScreenContent(interactions: HomeScreenInteractions) {
    Column(modifier = Modifier.fillMaxSize()) {
        HomeTopBar()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.Screen)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            OpenLogsButton(interactions.onLaunchLogsClicked)
        }
    }
}

@Composable
private fun OpenLogsButton(onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text(text = stringResource(id = R.string.home_open_logs_button))
    }
}

@Composable
private fun HomeTopBar() {
    TopAppBar(title = { Text(text = stringResource(id = R.string.home_title)) })
}

@Preview
@Composable
private fun HomeScreenPreview() {
    PreviewComponent {
        HomeScreenContent(interactions = HomeScreenInteractions.STUB)
    }
}
