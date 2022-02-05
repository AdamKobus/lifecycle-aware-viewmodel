package com.adamkobus.android.vm.demo.ui.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adamkobus.android.vm.demo.R
import com.adamkobus.android.vm.demo.ui.Paddings
import com.adamkobus.android.vm.demo.ui.common.LifecycleAwareComponent
import com.adamkobus.android.vm.demo.ui.common.PreviewComponent

@Composable
fun DemoDialog(logId: Int) {
    val vm: DemoDialogVM = hiltViewModel()
    vm.logIdParam.bind(logId)
    LifecycleAwareComponent(vm)
    DemoDialogContent(vm.screenState, vm.interactions)
}

@Composable
private fun DemoDialogContent(screenState: DemoDialogState, interactions: DemoDialogInteractions) {
    Card(modifier = Modifier.widthIn(0.dp, 380.dp)) {
        Column(
            modifier = Modifier
                .padding(Paddings.Screen)
                .fillMaxWidth()
        ) {
            Text(
                text = screenState.logMessage.value ?: "",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(id = R.string.demo_dialog_content),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = interactions.onDismissClicked
            ) {
                Text(text = stringResource(id = R.string.demo_dialog_dismiss))
            }
        }
    }
}

@Preview
@Composable
private fun DemoDialogPreview() {
    PreviewComponent {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            DemoDialogContent(screenState = DemoDialogState.stub(), interactions = DemoDialogInteractions.STUB)
        }
    }
}
