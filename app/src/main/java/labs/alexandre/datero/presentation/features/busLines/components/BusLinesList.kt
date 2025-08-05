package labs.alexandre.datero.presentation.features.busLines.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import labs.alexandre.datero.presentation.model.BusLineUiModel
import labs.alexandre.datero.core.extension.toLongOrZero
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
fun BusLinesList(
    busLines: List<BusLineUiModel>,
    modifier: Modifier = Modifier,
    onClickBusLine: (busLineId: Long) -> Unit,
    onClickDeleteBusLine: (busLine: BusLineUiModel) -> Unit,
    onMoveBusLine: (fromId: Long, toId: Long) -> Unit,
    onDragEndBusLines: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    val lazyListState = rememberLazyListState()

    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        onMoveBusLine(from.key.toLongOrZero(), to.key.toLongOrZero())
    }

    LazyColumn(
        contentPadding = PaddingValues(bottom = 96.dp),
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = busLines,
            key = { index, item -> item.id }
        ) { index, busLine ->
            ReorderableItem(reorderableLazyListState, key = busLine.id) { isDragging ->
                BusLineEditItem(
                    isDragging = isDragging,
                    busLine = busLine,
                    onItemClick = { onClickBusLine(busLine.id) },
                    onClickDelete = { onClickDeleteBusLine(busLine) },
                    onDragStarted = { haptic.performHapticFeedback(HapticFeedbackType.LongPress) },
                    onDragStopped = {
                        haptic.performHapticFeedback(HapticFeedbackType.GestureEnd)
                        onDragEndBusLines()
                    }
                )
            }
        }
    }
}