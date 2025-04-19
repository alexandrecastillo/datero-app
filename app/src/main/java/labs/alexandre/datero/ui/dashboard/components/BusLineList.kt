package labs.alexandre.datero.ui.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch
import labs.alexandre.datero.ui.dashboard.model.BusLineUiModel
import labs.alexandre.datero.ui.dashboard.model.BusTimestampUiModel
import labs.alexandre.datero.ui.util.maxScrollFlingBehavior

@Composable
fun BusLineList(
    modifier: Modifier = Modifier,
    busLines: SnapshotStateMap<String, BusLineUiModel>,
    onBusLineClick: (busLineUiModel: BusLineUiModel) -> Unit,
    onMarkBusLineClick: (busLineUiModel: BusLineUiModel) -> Unit,
    onBusTimestampClick: (busTimestampUiModel: BusTimestampUiModel) -> Unit,
) {
    val busLinesList by remember {
        derivedStateOf {
            busLines.values.sortedBy { it.position }
        }
    }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val previousFirstId = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(busLinesList.firstOrNull()?.id) {
        val isAtTop = listState.firstVisibleItemIndex == 1
        val newFirstId = busLinesList.firstOrNull()?.id

        if (isAtTop && newFirstId != null && newFirstId != previousFirstId.value) {
            previousFirstId.value = newFirstId
            coroutineScope.launch {
                listState.animateScrollToItem(index = 0)
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        state = listState,
        flingBehavior = maxScrollFlingBehavior(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(busLinesList, key = { it.id }) { busLine ->
            BusLineItem(
                modifier = Modifier.animateItem(),
                busLine = busLine,
                onBusLineClick = { onBusLineClick.invoke(busLine) },
                onMarkBusLineClick = onMarkBusLineClick,
                onBusTimestampClick = onBusTimestampClick
            )
        }
    }
}

@Composable
fun BusLineListPlaceholder(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .shimmer()
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BusLineItemPlaceholder()
        BusLineItemPlaceholder()
        BusLineItemPlaceholder()
        BusLineItemPlaceholder()
        BusLineItemPlaceholder()
    }
}

@Preview
@Composable
fun PreviewSkeletonBusLineList() {
    BusLineListPlaceholder()
}