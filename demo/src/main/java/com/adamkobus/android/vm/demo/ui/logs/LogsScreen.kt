package com.adamkobus.android.vm.demo.ui.logs

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adamkobus.android.vm.demo.R
import com.adamkobus.android.vm.demo.ui.Paddings
import com.adamkobus.android.vm.demo.ui.common.BackgroundComponent
import com.adamkobus.android.vm.demo.ui.common.LifecycleAwareComponent
import com.adamkobus.android.vm.demo.ui.common.PreviewComponent

@Composable
fun LogsScreen() {
    val vm: LogsScreenVM = hiltViewModel()
    LifecycleAwareComponent(vm)
    BackgroundComponent {
        LogsScreenContent(screenState = vm.screenState, interactions = vm.interactions)
    }
}

@Composable
private fun LogsScreenContent(screenState: LogsScreenState, interactions: LogsInteractions) {
    Column(modifier = Modifier.fillMaxSize()) {
        LogsTopBar(onBackClicked = interactions.onBackClicked)
        Crossfade(targetState = screenState.isListLoading.value) { renderedIsListLoading ->
            when (renderedIsListLoading) {
                true -> LoadingContent()
                false -> ListContent(screenState, interactions)
            }
        }
    }
}

@Composable
fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ListContent(screenState: LogsScreenState, interactions: LogsInteractions) {
    val logs = screenState.demoLogsList.value
    val listState = rememberLazyListState()
    val isListScrollSticky = rememberSaveable { mutableStateOf(true) }
    val scrollNeeded = isListScrollSticky.value

    LazyColumn(
        contentPadding = PaddingValues(Paddings.Screen),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(Paddings.ListElementSpacing),
        reverseLayout = true
    ) {
        itemsIndexed(logs, key = { _, item -> item.id }) { index, item ->
            LogItemContent(item, index != 0, interactions.onLogClicked)
        }
    }
    isListScrollSticky.value = listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0

    LaunchedEffect(key1 = logs) {
        if (scrollNeeded) {
            listState.animateScrollToItem(0, 0)
            isListScrollSticky.value = true
        }
    }
}

@Composable
private fun LogItemContent(item: DemoLogListItem, isNotLastElement: Boolean, onLogClicked: (DemoLogListItem) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLogClicked(item) }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = item.event.name,
                style = MaterialTheme.typography.caption,
                color = item.event.color()
            )
            Text(
                text = item.createdAt,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.caption
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = item.message)

        if (isNotLastElement) {
            Spacer(modifier = Modifier.height(Paddings.ListElementSpacing))
            Divider(color = MaterialTheme.colors.secondaryVariant, thickness = 1.dp)
        }
    }
}

@Composable
private fun LogsTopBar(onBackClicked: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.logs_title)) },
        navigationIcon = { BackButton(onBackClicked = onBackClicked) }
    )
}

@Composable
fun BackButton(onBackClicked: () -> Unit) {
    IconButton(onClick = onBackClicked) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.accessibility_back))
    }
}

@Preview
@Composable
private fun LightListPreview() {
    PreviewComponent {
        LogsScreenContent(screenState = LogsScreenState.stub(), interactions = LogsInteractions.STUB)
    }
}

@Preview
@Composable
private fun LightLoadingPreview() {
    PreviewComponent {
        LogsScreenContent(screenState = LogsScreenState.stub(isListLoading = true), interactions = LogsInteractions.STUB)
    }
}
