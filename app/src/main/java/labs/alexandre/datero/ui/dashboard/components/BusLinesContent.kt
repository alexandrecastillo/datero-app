package labs.alexandre.datero.ui.dashboard.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import labs.alexandre.datero.ui.dashboard.model.BusLineUiModel
import labs.alexandre.datero.ui.dashboard.model.BusTimestampUiModel

@Composable
fun BusLinesContent(
    modifier: Modifier = Modifier,
    busLines: SnapshotStateMap<String, BusLineUiModel>,
    onBusLineClick: (busLineUiModel: BusLineUiModel) -> Unit,
    onMarkBusLineClick: (busLineUiModel: BusLineUiModel) -> Unit,
    onBusTimestampClick: (busTimestampUiModel: BusTimestampUiModel) -> Unit,
    onAddBusLineClick: () -> Unit
) {
    val busLinesList by remember { derivedStateOf { busLines } }

    when (busLinesList.isEmpty()) {
        true -> {
            EmptyBusLines(
                modifier = modifier,
                onAddBusLineClick = onAddBusLineClick
            )
        }

        false -> {
            BusLineList(
                modifier = modifier,
                busLines = busLines,
                onBusLineClick = onBusLineClick,
                onMarkBusLineClick = onMarkBusLineClick,
                onBusTimestampClick = onBusTimestampClick,
            )
        }
    }
}